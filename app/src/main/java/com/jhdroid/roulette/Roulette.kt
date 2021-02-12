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

        fillPaint.isAntiAlias = true

        rouletteElementSize = 8
        val sweepAngle = 360f / rouletteElementSize.toFloat()
        when (rouletteElementSize) {
            2 -> {
                fillPaint.color = Color.parseColor(shapeColors[0])
                canvas?.drawArc(rectF, 0f, sweepAngle, true, fillPaint)

                fillPaint.color = Color.parseColor(shapeColors[1])
                canvas?.drawArc(rectF, sweepAngle, sweepAngle, true, fillPaint)
            }

            3 -> {
                fillPaint.color = Color.parseColor(shapeColors[0])
                canvas?.drawArc(rectF, 0f, sweepAngle, true, fillPaint)

                fillPaint.color = Color.parseColor(shapeColors[1])
                canvas?.drawArc(rectF, sweepAngle, sweepAngle, true, fillPaint)

                fillPaint.color = Color.parseColor(shapeColors[2])
                canvas?.drawArc(rectF, sweepAngle * 2f, sweepAngle, true, fillPaint)
            }

            4 -> {
                fillPaint.color = Color.parseColor(shapeColors[0])
                canvas?.drawArc(rectF, 0f, sweepAngle, true, fillPaint)

                fillPaint.color = Color.parseColor(shapeColors[1])
                canvas?.drawArc(rectF, sweepAngle, sweepAngle, true, fillPaint)

                fillPaint.color = Color.parseColor(shapeColors[2])
                canvas?.drawArc(rectF, sweepAngle * 2f, sweepAngle, true, fillPaint)

                fillPaint.color = Color.parseColor(shapeColors[3])
                canvas?.drawArc(rectF, sweepAngle * 3f, sweepAngle, true, fillPaint)
            }

            5 -> {
                fillPaint.color = Color.parseColor(shapeColors[0])
                canvas?.drawArc(rectF, 0f, sweepAngle, true, fillPaint)

                fillPaint.color = Color.parseColor(shapeColors[1])
                canvas?.drawArc(rectF, sweepAngle, sweepAngle, true, fillPaint)

                fillPaint.color = Color.parseColor(shapeColors[2])
                canvas?.drawArc(rectF, sweepAngle * 2f, sweepAngle, true, fillPaint)

                fillPaint.color = Color.parseColor(shapeColors[3])
                canvas?.drawArc(rectF, sweepAngle * 3f, sweepAngle, true, fillPaint)

                fillPaint.color = Color.parseColor(shapeColors[4])
                canvas?.drawArc(rectF, sweepAngle * 4f, sweepAngle, true, fillPaint)
            }

            6 -> {
                fillPaint.color = Color.parseColor(shapeColors[0])
                canvas?.drawArc(rectF, 0f, sweepAngle, true, fillPaint)

                fillPaint.color = Color.parseColor(shapeColors[1])
                canvas?.drawArc(rectF, sweepAngle, sweepAngle, true, fillPaint)

                fillPaint.color = Color.parseColor(shapeColors[2])
                canvas?.drawArc(rectF, sweepAngle * 2f, sweepAngle, true, fillPaint)

                fillPaint.color = Color.parseColor(shapeColors[3])
                canvas?.drawArc(rectF, sweepAngle * 3f, sweepAngle, true, fillPaint)

                fillPaint.color = Color.parseColor(shapeColors[4])
                canvas?.drawArc(rectF, sweepAngle * 4f, sweepAngle, true, fillPaint)

                fillPaint.color = Color.parseColor(shapeColors[5])
                canvas?.drawArc(rectF, sweepAngle * 5f, sweepAngle, true, fillPaint)
            }

            7 -> {
                fillPaint.color = Color.parseColor(shapeColors[0])
                canvas?.drawArc(rectF, 0f, sweepAngle, true, fillPaint)

                fillPaint.color = Color.parseColor(shapeColors[1])
                canvas?.drawArc(rectF, sweepAngle, sweepAngle, true, fillPaint)

                fillPaint.color = Color.parseColor(shapeColors[2])
                canvas?.drawArc(rectF, sweepAngle * 2f, sweepAngle, true, fillPaint)

                fillPaint.color = Color.parseColor(shapeColors[3])
                canvas?.drawArc(rectF, sweepAngle * 3f, sweepAngle, true, fillPaint)

                fillPaint.color = Color.parseColor(shapeColors[4])
                canvas?.drawArc(rectF, sweepAngle * 4f, sweepAngle, true, fillPaint)

                fillPaint.color = Color.parseColor(shapeColors[5])
                canvas?.drawArc(rectF, sweepAngle * 5f, sweepAngle, true, fillPaint)

                fillPaint.color = Color.parseColor(shapeColors[6])
                canvas?.drawArc(rectF, sweepAngle * 6f, sweepAngle, true, fillPaint)
            }

            8 -> {
                fillPaint.color = Color.parseColor(shapeColors[0])
                canvas?.drawArc(rectF, 0f, sweepAngle, true, fillPaint)

                fillPaint.color = Color.parseColor(shapeColors[1])
                canvas?.drawArc(rectF, sweepAngle, sweepAngle, true, fillPaint)

                fillPaint.color = Color.parseColor(shapeColors[2])
                canvas?.drawArc(rectF, sweepAngle * 2f, sweepAngle, true, fillPaint)

                fillPaint.color = Color.parseColor(shapeColors[3])
                canvas?.drawArc(rectF, sweepAngle * 3f, sweepAngle, true, fillPaint)

                fillPaint.color = Color.parseColor(shapeColors[4])
                canvas?.drawArc(rectF, sweepAngle * 4f, sweepAngle, true, fillPaint)

                fillPaint.color = Color.parseColor(shapeColors[5])
                canvas?.drawArc(rectF, sweepAngle * 5f, sweepAngle, true, fillPaint)

                fillPaint.color = Color.parseColor(shapeColors[6])
                canvas?.drawArc(rectF, sweepAngle * 6f, sweepAngle, true, fillPaint)

                fillPaint.color = Color.parseColor(shapeColors[7])
                canvas?.drawArc(rectF, sweepAngle * 7f, sweepAngle, true, fillPaint)
            }
            else -> throw RuntimeException("number out of roulette size")
        }
    }

    /**
     * getter & setter
     * */
    fun setRouletteElementSize(size: Int) {
        this.rouletteElementSize = size
    }

    fun getRouletteElementSize(): Int = rouletteElementSize
}