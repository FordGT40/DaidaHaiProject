package com.wisdom.project.weight

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.os.CountDownTimer
import android.util.AttributeSet
import android.view.Gravity
import android.widget.Button
import com.wisdom.project.R

@SuppressLint("AppCompatCustomView")
class CountDownButton @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    Button(context, attrs, defStyleAttr) {

    //总时长
    private val millisinfuture: Long

    //间隔时长
    private val countdowninterva: Long

    //默认背景颜色
    private val normalColor: Int

    //倒计时 背景颜色
    private val countDownColor: Int

    //是否结束
    var isFinish: Boolean = false
        private set

    //定时器
    private val countDownTimer: CountDownTimer

    init {
        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.CountDownButton, defStyleAttr, 0)
        //设置默认时长
        millisinfuture = typedArray.getInt(R.styleable.CountDownButton_millisinfuture, 60000).toLong()
        //设置默认间隔时长
        countdowninterva = typedArray.getInt(R.styleable.CountDownButton_countdowninterva, 1000).toLong()
        //设置默认背景色
        normalColor =
                typedArray.getColor(R.styleable.CountDownButton_normalColor, resources.getColor(R.color.translucent))
        //设置默认倒计时 背景色
        countDownColor =
                typedArray.getColor(R.styleable.CountDownButton_countDownColor, resources.getColor(R.color.translucent))
        typedArray.recycle()
        //默认为已结束状态
        isFinish = true
        //字体居中
        gravity = Gravity.CENTER
        //默认文字和背景色
//        normalBackground()
        //设置定时器
        countDownTimer = object : CountDownTimer(millisinfuture, countdowninterva) {
            override fun onTick(millisUntilFinished: Long) {
                //未结束
                isFinish = false

                text = (Math.round(millisUntilFinished.toDouble() / 1000) - 1).toString() + "秒"

//                setBackgroundResource(countDownColor)
            }

            override fun onFinish() {
                //结束
                isFinish = true

                normalBackground()
            }
        }
    }

    private fun normalBackground() {
        text = "获取验证码"
        setBackgroundResource(normalColor)
    }

    fun cancel() {
        countDownTimer.cancel()
    }

    fun start() {
        countDownTimer.start()
    }



}

