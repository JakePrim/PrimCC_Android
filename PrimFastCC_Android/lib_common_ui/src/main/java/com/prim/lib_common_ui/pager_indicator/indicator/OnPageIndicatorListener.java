package com.prim.lib_common_ui.pager_indicator.indicator;

import android.content.Context;
import android.widget.TextView;

import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2019-09-05 - 17:26
 * @contact https://jakeprim.cn
 * @name PrimFastCC_Android
 */
public interface OnPageIndicatorListener {
    void onLeft();

    void onRight();

    void onPageIndex(Context context, int index, SimplePagerTitleView textView);
}
