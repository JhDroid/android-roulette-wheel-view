package com.jhdroid.roulette

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
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

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val rectLeft = left.toFloat() + paddingLeft + DEFAULT_PADDING
        val rectRight = right - paddingRight - DEFAULT_PADDING
        val rectTop = height / 2f - rectRight / 2f + paddingTop + DEFAULT_PADDING
        val rectBottom = height / 2f + rectRight / 2f - paddingRight - DEFAULT_PADDING

        val rectF = RectF(rectLeft, rectTop, rectRight, rectBottom)

        drawRoulette(canvas, rectF)
    }

    private fun drawRoulette(canvas: Canvas?, rectF: RectF) {
        canvas?.drawArc(rectF, 0f, 360f, false, strokePaint)

        if (rouletteSize in 2..8) {
            val sweepAngle = 360f / rouletteSize.toFloat()
            val centerX = (rectF.left + rectF.right) / 2
            val centerY = (rectF.top + rectF.bottom) / 2
            val radius = (rectF.right - rectF.left) / 2 * 0.5

            for (i in 0 until rouletteSize) {
                fillPaint.color = Color.parseColor(shapeColors[i])

                val startAngle = if (i == 0) 0f else sweepAngle * i
                canvas?.drawArc(rectF, startAngle, sweepAngle, true, fillPaint)

                val medianAngle = (startAngle + sweepAngle / 2f) * Math.PI / 180f
                val x = (centerX + (radius * cos(medianAngle))).toFloat()
                val y = (centerY + (radius * sin(medianAngle))).toFloat() + DEFAULT_PADDING
                canvas?.drawText(rouletteDataList[i], x, y, textPaint)
            }
        } else throw RuntimeException("size out of roulette")
    }

    fun rotateRoulette(toDegrees: Float, duration: Long) {
        val rotateAnim = RotateAnimation(
            0f, toDegrees,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )
        rotateAnim.duration = duration
        rotateAnim.fillAfter = true

        startAnimation(rotateAnim)
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