package com.jhdroid.roulette

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

class Roulette(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    companion object {
        const val DEFAULT_CIRCLE_BORDER_LINE_HEIGHT = 15f
        const val DEFAULT_PADDING = 20f
    }

    private var rouletteElementSize = 0

    private val strokePaint = Paint()
    private val fillPaint = Paint()
    private var shapeColors = arrayOf<String>()

    init {
        strokePaint.apply {
            color = Color.BLACK
            style = Paint.Style.STROKE
            strokeWidth = DEFAULT_CIRCLE_BORDER_LINE_HEIGHT
            isAntiAlias = true
        }

        fillPaint.apply {
            style = Paint.Style.FILL
            isAntiAlias = true
        }

        shapeColors = resources.getStringArray(R.array.shape_colors)
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

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val centerY = height / 2f

        val rectLeft = left.toFloat() + paddingLeft + DEFAULT_PADDING
        val rectRight = right.toFloat() - paddingRight - DEFAULT_PADDING
        val rectTop = ((centerY + top.toFloat()) / 2f) + paddingTop + DEFAULT_PADDING
        val rectBottom = ((centerY + bottom.toFloat()) / 2f) - paddingBottom - DEFAULT_PADDING

        val rectF = RectF(rectLeft, rectTop, rectRight, rectBottom)

        canvas?.drawArc(rectF, 0f, 360f, true, strokePaint)

        rouletteElementSize = 8
         drawRoulette(canvas, rectF, rouletteElementSize)
    }

    private fun drawRoulette(canvas: Canvas?, rectF: RectF, size: Int) {
        if (size in 2..8) {
            val sweepAngle = 360f / rouletteElementSize.toFloat()

            for (i in 0..size) {
                fillPaint.color = Color.parseColor(shapeColors[i])

                val startAngle = if (i == 0) 0f else sweepAngle * i
                canvas?.drawArc(rectF, startAngle, sweepAngle, true, fillPaint)
            }
        } else throw RuntimeException("size out of roulette size")
    }

    /**
     * getter & setter
     * */
    fun setRouletteElementSize(size: Int) {
        this.rouletteElementSize = size
    }

    fun getRouletteElementSize(): Int = rouletteElementSize
}