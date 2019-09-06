package com.prim.lib_common_ui.base.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2019-09-04 - 19:04
 * @contact https://jakeprim.cn
 * @name PrimFastCC_Android
 */
public class BaseView extends FrameLayout {
    public BaseView(@NonNull Context context) {
        super(context);
    }

    public BaseView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


}
