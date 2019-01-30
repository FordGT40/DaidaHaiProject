package com.wisdom.project.weight;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @author HanXueFeng
 * @ProjectName project： DaidaHaiProject
 * @class package：com.wisdom.project.weight
 * @class describe：
 * @time 2019/1/30 9:32
 * @change
 */
public class ViewPagerWithoutSlideJava extends ViewPager {
    public ViewPagerWithoutSlideJava(@NonNull Context context) {
        super(context);
    }

    public ViewPagerWithoutSlideJava(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }

}
