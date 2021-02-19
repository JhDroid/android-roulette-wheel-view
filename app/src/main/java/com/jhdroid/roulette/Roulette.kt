package com.jhdroid.roulette

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import kotlin.math.cos
import kotlin.math.sin

class Roulette @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    companion object {
        const val DEFAULT_CIRCLE_BORDER_LINE_HEIGHT = 15f
        const val DEFAULT_PADDING = 20f
        const val DEFAULT_ROULETTE_SIZE = 2
        const val DEFAULT_TEXT_SIZE = 60f
    }

    private var rouletteSize = 0
    private var rouletteDataList = listOf<String>()

    private val strokePaint = Paint()
    private val fillPaint = Paint()
    private val textPaint = Paint()
    private var shapeColors = arrayOf<String>()

    init {
        val typedArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.RouletteView,
            defStyleAttr,
            0
        )

        val colorStrokeColor = typedArray.getColor(
            R.styleable.RouletteView_circleStrokeColor,
            Color.BLACK
        )

        rouletteSize = typedArray.getInt(
            R.styleable.RouletteView_rouletteSize,
            DEFAULT_ROULETTE_SIZE
        )

        typedArray.recycle()

        strokePaint.apply {
            color = colorStrokeColor
            style = Paint.Style.STROKE
            strokeWidth = DEFAULT_CIRCLE_BORDER_LINE_HEIGHT
            isAntiAlias = true
        }

        fillPaint.apply {
            style = Paint.Style.FILL
            isAntiAlias = true
        }

        textPaint.apply {
            color = Color.BLACK
            textSize = DEFAULT_TEXT_SIZE
            textAlign = Paint.Align.CENTER
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

        val rectLeft = left.toFloat() + paddingLeft + DEFAULT_PADDING
        val rectRight = right - paddingRight - DEFAULT_PADDING
        val rectTop = top.toFloat() + paddingTop + DEFAULT_PADDING
        val rectBottom = right - paddingBottom - DEFAULT_PADDING

        val rectF = RectF(rectLeft, rectTop, rectRight, rectBottom)

        canvas?.drawArc(rectF, 0f, 360f, true, strokePaint)

        drawRoulette(canvas, rectF, rouletteSize)
    }

    private fun drawRoulette(canvas: Canvas?, rectF: RectF, size: Int) {
        if (size in 2..8) {
            val sweepAngle = 360f / rouletteSize.toFloat()
            val centerX = (rectF.left + rectF.right) / 2
            val centerY = (rectF.top + rectF.bottom) / 2
            val radius = (rectF.right - rectF.left) / 2 * 0.65

            for (i in 0 until size) {
                fillPaint.color = Color.parseColor(shapeColors[i])

                val startAngle = if (i == 0) 0f else sweepAngle * i
                canvas?.drawArc(rectF, startAngle, sweepAngle, true, fillPaint)

                val medianAngle = (startAngle + sweepAngle / 2f) * Math.PI / 180f

                canvas?.drawText(rouletteDataList[i], (centerX + (radius * cos(medianAngle))).toFloat(),
                        (centerY + (radius * sin(medianAngle))).toFloat(), textPaint)
            }
        } else throw RuntimeException("size out of roulette")
    }

    /**
     * getter & setter
     * */
    fun setCircleStrokeColor(color: Int) {
        this.strokePaint.color = color
        invalidate()
    }

    fun getCircleStrokeColor(): Int = this.strokePaint.color

    fun setRouletteSize(size: Int) {
        this.rouletteSize = size
        invalidate()
    }

    fun getRouletteSize(): Int = rouletteSize

    fun setRouletteDataList(rouletteDataList: List<String>) {
        this.rouletteDataList = rouletteDataList
        invalidate()
    }

    fun getRouletteDataList(): List<String> = rouletteDataList
}