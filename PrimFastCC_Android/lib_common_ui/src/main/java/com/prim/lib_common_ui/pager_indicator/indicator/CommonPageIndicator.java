package com.prim.lib_common_ui.pager_indicator.indicator;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.prim.lib_common_ui.R;
import com.prim.lib_common_ui.base.view.BaseView;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

/**
 * @author prim
 * @version 1.0.0
 * @desc 封装通用Tab Page指示器
 * @time 2019-09-04 - 18:52
 * @contact https://jakeprim.cn
 * @name PrimFastCC_Android
 */
public class CommonPageIndicator extends RelativeLayout implements View.OnClickListener {
    private MagicIndicator magicIndicator;

    private FrameLayout left_view, right_view;

    private View mView;

    private boolean isAdjustMode;//// 自适应模式，适用于数目固定的、少量的title

    private boolean mSmoothScroll;// 是否平滑滚动，适用于 !mAdjustMode && !mFollowTouch

    private boolean mFollowTouch;//mFollowTouch // 是否手指跟随滚动

    private boolean mIndicatorOnTop;//指示器是否在title上层，默认为下层

    private int mRightPadding = 5;

    private int mLeftPadding = 5;

    private boolean mSkimOver;  // 跨多页切换时，中间页是否显示 "掠过" 效果

    private CommonNavigator commonNavigator;

    private OnPageIndicatorListener listener;

    private @LayoutRes
    int leftId, rightId;

    private View leftChild, rightChild;

    private ViewPager viewPager;

    private IPagerTitleView pagerTitleView;

    public CommonPageIndicator(@NonNull Context context) {
        this(context, null);
    }

    public CommonPageIndicator(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonPageIndicator(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        isAdjustMode = false;
        mSmoothScroll = true;
        mFollowTouch = false;
        mIndicatorOnTop = false;
        mSkimOver = false;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //加载页面布局
        mView = LayoutInflater.from(getContext()).inflate(R.layout.common_page_indicator, this, false);
        magicIndicator = mView.findViewById(R.id.indicator_view);
        left_view = mView.findViewById(R.id.left_view);
        right_view = mView.findViewById(R.id.right_view);
        left_view.setOnClickListener(this);
        right_view.setOnClickListener(this);
    }

    public Build with(Context context) {
        return new Build(this, context);
    }

    public void config(Build build, ViewPager viewPager) {
        if (viewPager == null) {
            throw new IllegalArgumentException("CommonPageIndicator need bind ViewPager");
        }
        this.viewPager = viewPager;
        if (build.commonNavigator == null) {
            this.commonNavigator = new CommonNavigator(getContext());
        } else {
            this.commonNavigator = build.commonNavigator;
        }
        commonNavigator.setAdjustMode(isAdjustMode);
        commonNavigator.setSmoothScroll(mSmoothScroll);
        commonNavigator.setFollowTouch(mFollowTouch);
        commonNavigator.setIndicatorOnTop(mIndicatorOnTop);
        commonNavigator.setRightPadding(mRightPadding);
        commonNavigator.setLeftPadding(mLeftPadding);
        commonNavigator.setSkimOver(mSkimOver);

        if (this.leftId == 0) {
            this.leftId = build.leftId;
        }
        if (this.rightId == 0) {
            this.rightId = build.rightId;
        }

        this.leftChild = build.leftChild;
        this.rightChild = build.rightChild;

        if (leftId != 0) {
            LayoutInflater.from(getContext()).inflate(leftId, left_view, false);
        } else if (leftChild != null) {
            left_view.addView(leftChild);
        }

        if (rightId != 0) {
            LayoutInflater.from(getContext()).inflate(rightId, right_view, false);
        } else if (rightChild != null) {
            right_view.addView(rightChild);
        }

        if (build.pagerTitleView == null) {
            this.pagerTitleView = new SimplePagerTitleView(getContext());
        } else {
            this.pagerTitleView = build.pagerTitleView;
        }

        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return build.count;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, int index) {
                IPagerTitleView pagerTitleView = CommonPageIndicator.this.pagerTitleView;
                if (listener != null) {
                    listener.onPageIndex(context, index, (SimplePagerTitleView) pagerTitleView);
                }
                return pagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                return build.pagerIndicator;
            }

            @Override
            public float getTitleWeight(Context context, int index) {
                return super.getTitleWeight(context, index);
            }
        });
        Log.e(TAG, "commonNavigator: " + magicIndicator.getParent());

        if (magicIndicator.getParent() != null) {
            ViewGroup viewGroup = (ViewGroup) magicIndicator.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(magicIndicator);
            }
        }
        Log.e(TAG, "commonNavigator: " + magicIndicator.getParent());
        //设置Navigator
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, viewPager);
    }

    private static final String TAG = "CommonPageIndicator";

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.left_view) {
            if (listener != null) listener.onLeft();
        } else if (id == R.id.right_view) {
            if (listener != null) listener.onRight();
        }
    }

    public static class Build {
        private CommonPageIndicator pageIndicator;

        private @LayoutRes
        int leftId, rightId;

        private View leftChild, rightChild;

        private IPagerTitleView pagerTitleView;

        private IPagerIndicator pagerIndicator;

        private int count;

        private CommonNavigator commonNavigator;

        private float titleWidget = 1;

        public Build(CommonPageIndicator pageIndicator, Context context) {
            this.pageIndicator = pageIndicator;
        }

        public Build setAdjustMode(boolean adjustMode) {
            pageIndicator.isAdjustMode = adjustMode;
            return this;
        }

        public Build setSmoothScroll(boolean mSmoothScroll) {
            pageIndicator.mSmoothScroll = mSmoothScroll;
            return this;
        }

        public Build setFollowTouch(boolean mFollowTouch) {
            pageIndicator.mFollowTouch = mFollowTouch;
            return this;
        }

        public Build setIndicatorOnTop(boolean mIndicatorOnTop) {
            pageIndicator.mIndicatorOnTop = mIndicatorOnTop;
            return this;
        }

        public Build setRightPadding(int mRightPadding) {
            pageIndicator.mRightPadding = mRightPadding;
            return this;
        }

        public Build setLeftPadding(int mLeftPadding) {
            pageIndicator.mLeftPadding = mLeftPadding;
            return this;
        }

        public Build setSkimOver(boolean mSkimOver) {
            pageIndicator.mSkimOver = mSkimOver;
            return this;
        }

        public Build addOnPageIndicatorListener(OnPageIndicatorListener listener) {
            pageIndicator.listener = listener;
            return this;
        }

        public Build setLeftChild(@LayoutRes int leftId) {
            this.leftId = leftId;
            return this;
        }

        public Build setRightChild(@LayoutRes int rightId) {
            this.rightId = rightId;
            return this;
        }

        public Build setLeftChild(View leftChild) {
            this.leftChild = leftChild;
            return this;
        }

        public Build setRightChild(View rightChild) {
            this.rightChild = rightChild;
            return this;
        }

        public Build setCommonNavigator(CommonNavigator navigator) {
            this.commonNavigator = navigator;
            return this;
        }

        public Build setPagerTitleView(IPagerTitleView pagerTitleView) {
            this.pagerTitleView = pagerTitleView;
            return this;
        }

        public Build setPagerIndicator(IPagerIndicator pagerIndicator) {
            this.pagerIndicator = pagerIndicator;
            return this;
        }

        public Build setCount(int count) {
            this.count = count;
            return this;
        }

        public Build setTitleWidget(float titleWidget) {
            this.titleWidget = titleWidget;
            return this;
        }

        public void config(ViewPager viewPager) {
            pageIndicator.config(this, viewPager);
        }
    }
}
