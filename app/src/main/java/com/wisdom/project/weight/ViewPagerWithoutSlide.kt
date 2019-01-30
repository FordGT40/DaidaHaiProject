package com.wisdom.project.weight

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

/**
 * @ProjectName project： DaidaHaiProject
 * @class package：com.wisdom.project.weight
 * @class describe：禁止滑动的slideView
 * @author HanXueFeng
 * @time 2019/1/30 9:02
 * @change
 */
class ViewPagerWithoutSlide(context: Context) : ViewPager(context) {
    //是否可以进行滑动
    private var isSlide = false

    fun setSlide(slide: Boolean) {
        isSlide = slide
    }

    constructor(context:Context,attrs: AttributeSet) : this(context)

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return isSlide
    }

}