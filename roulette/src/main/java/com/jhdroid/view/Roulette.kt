package com.jhdroid.view

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

    // Roulette Attr
    private var rouletteSize = 0
    private var rouletteDataList = listOf<String>()

    private var rouletteBorderLineColor = Color.BLACK
    private var rouletteBorderLineWidth = 0f

    private var shapeColors = arrayOf<String>()

    private var rouletteTextColor = Color.BLACK
    private var rouletteTextSize = 0f
    private var emptyMessage = ""

    // Center Point Attr
    private var centerPointColor = Color.BLACK
    private var centerPointRadius = 0f

    // Paint
    private val strokePaint = Paint()
    private val fillPaint = Paint()
    private val centerPointPaint = Paint()
    private val textPaint = Paint()

    init {
        val typedArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.RouletteView,
            defStyleAttr,
            0
        )

        rouletteBorderLineColor = typedArray.getColor(
            R.styleable.RouletteView_backgroundLineColor,
            Color.BLACK
        )

        rouletteBorderLineWidth = typedArray.getDimension(
            R.styleable.RouletteView_backgroundLineWidth,
            Constant.DEFAULT_CIRCLE_BORDER_LINE_WIDTH
        )

        rouletteTextColor = typedArray.getColor(
            R.styleable.RouletteView_rouletteTextColor,
            Color.BLACK
        )

        rouletteSize = typedArray.getInt(
            R.styleable.RouletteView_rouletteSize,
            Constant.DEFAULT_ROULETTE_SIZE
        )

        rouletteTextSize = typedArray.getDimension(
            R.styleable.RouletteView_rouletteTextSize,
            Constant.DEFAULT_TEXT_SIZE
        )

        emptyMessage = typedArray.getString(
            R.styleable.RouletteView_emptyMessage
        ) ?: Constant.DEFAULT_EMPTY_MESSAGE

        typedArray.recycle()

        strokePaint.apply {
            color = rouletteBorderLineColor
            style = Paint.Style.STROKE
            strokeWidth = rouletteBorderLineWidth
            isAntiAlias = true
        }

        fillPaint.apply {
            style = Paint.Style.FILL
            isAntiAlias = true
        }

        centerPointPaint.apply {
            color = centerPointColor
            isAntiAlias = true
        }

        textPaint.apply {
            color = rouletteTextColor
            textSize = rouletteTextSize
            textAlign = Paint.Align.CENTER
        }

        shapeColors = resources.getStringArray(R.array.shape_colors)
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (canvas == null) return

        val rectLeft = left + paddingLeft + Constant.DEFAULT_PADDING
        val rectRight = right - paddingRight - Constant.DEFAULT_PADDING
        val rectTop = height / 2f - rectRight / 2f + paddingTop + Constant.DEFAULT_PADDING
        val rectBottom = height / 2f + rectRight / 2f - paddingRight - Constant. DEFAULT_PADDING

        val rectF = RectF(rectLeft, rectTop, rectRight, rectBottom)

        drawRoulette(canvas, rectF)
    }

    private fun drawRoulette(canvas: Canvas, rectF: RectF) {
        // draw roulette border line
        canvas.drawArc(rectF, 0f, 360f, false, strokePaint)

        if (rouletteSize in 2..8) {
            val sweepAngle = 360f / rouletteSize.toFloat()
            val centerX = (rectF.left + rectF.right) / 2
            val centerY = (rectF.top + rectF.bottom) / 2
            val radius = (rectF.right - rectF.left) / 2 * 0.5

            for (i in 0 until rouletteSize) {
                fillPaint.color = Color.parseColor(shapeColors[i])

                // draw roulette arc
                val startAngle = if (i == 0) 0f else sweepAngle * i
                canvas.drawArc(rectF, startAngle, sweepAngle, true, fillPaint)

                // draw roulette text
                val medianAngle = (startAngle + sweepAngle / 2f) * Math.PI / 180f
                val x = (centerX + (radius * cos(medianAngle))).toFloat()
                val y = (centerY + (radius * sin(medianAngle))).toFloat() + Constant.DEFAULT_PADDING
                val text = if (i > rouletteDataList.size - 1)  emptyMessage else rouletteDataList[i]

                canvas.drawText(text, x, y, textPaint)
            }

            // draw center point
            canvas.drawCircle(centerX, centerY, centerPointRadius, centerPointPaint)
        } else throw RuntimeException("size out of roulette")
    }

    /**
     * 룰렛 회전 함수
     * @param toDegrees : 종료 각도(시작 각도 : 0)
     * @param duration : 회전 시간
     * @param rotateListener : 회전 애니메이션 시작, 종료 확인을 위한 리스너 (선택)
     * */
    fun rotateRoulette(toDegrees: Float, duration: Long, rotateListener: RotateListener?) {
        val animListener = object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {}

            override fun onAnimationStart(animation: Animation?) {
                rotateListener?.onRotateStart()
            }

            override fun onAnimationEnd(animation: Animation?) {
                rotateListener?.onRotateEnd(getRouletteRotateResult(toDegrees))
            }
        }

        val rotateAnim = RotateAnimation(
            0f, toDegrees,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )
        rotateAnim.duration = duration
        rotateAnim.fillAfter = true
        rotateAnim.setAnimationListener(animListener)

        startAnimation(rotateAnim)
    }

    /**
     * 룰레 회전 결과 리턴
     * @param toDegrees : 회전 각도
     * @return 회전 결과
     * */
    private fun getRouletteRotateResult(toDegrees: Float): String {
        val moveDegrees = toDegrees % 360
        val resultAngle = if (moveDegrees > 270) 360 - moveDegrees + 270 else 270 - moveDegrees
        for (i in 1..rouletteSize) {
            if (resultAngle < (360 / rouletteSize) * i) {
                if (i - 1 >= rouletteDataList.size) {
                    return emptyMessage
                }

                return rouletteDataList[i - 1]
            }
        }

        return ""
    }

    /**
     * getter & setter
     * */
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

    fun setEmptyMessage(emptyMessage: String) {
        this.emptyMessage = emptyMessage
        invalidate()
    }

    fun getEmptyMessage(): String = emptyMessage

    fun setRouletteTextSize(textSize: Float) {
        rouletteTextSize = textSize
        invalidate()
    }

    fun getRouletteTextSize(): Float = rouletteTextSize

    fun setRouletteTextColor(textColor: Int) {
        this.rouletteTextColor = textColor
        invalidate()
    }

    fun getRouletteTextColor(): Int = rouletteTextColor

    fun setRouletteBorderLineColor(borderLineColor: Int) {
        rouletteBorderLineColor = borderLineColor
        invalidate()
    }

    fun getRouletteBorderLineColor(): Int = rouletteBorderLineColor

    fun setRouletteBorderLineWidth(width: Float) {
        rouletteBorderLineWidth = width
        invalidate()
    }

    fun getRouletteBorderLineWidth(): Float = rouletteBorderLineWidth
}