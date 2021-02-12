package com.jhdroid.roulette

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class Roulette(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var circleRadius = 0f

    private var circlePaint = Paint()

    init {
        circlePaint = Paint()
        circlePaint.color = Color.BLACK
        circlePaint.style = Paint.Style.STROKE
        circlePaint.strokeWidth = 10f
        circlePaint.isAntiAlias = true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(measureDimension(widthMeasureSpec, true), measureDimension(heightMeasureSpec, false))
    }

    private fun measureDimension(measureSpec: Int, isWidth: Boolean): Int {
        var result: Int
        val specMode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize
        } else {
            val padding = if (isWidth) paddingLeft + paddingRight else paddingTop + paddingBottom
            result = if (isWidth) suggestedMinimumWidth else suggestedMinimumHeight
            result += padding
            if (specMode == MeasureSpec.AT_MOST) {
                result = if (isWidth) {
                    result.coerceAtLeast(specSize)
                } else {
                    result.coerceAtMost(specSize)
                }
            }
        }

        return result
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val centerX = width / 2f
        val centerY = height / 2f

        circleRadius = centerX

        canvas?.drawCircle(centerX, centerY, circleRadius, circlePaint)
    }
}