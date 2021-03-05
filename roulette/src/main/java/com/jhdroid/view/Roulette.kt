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
import io.reactivex.Single
import kotlin.math.cos
import kotlin.math.sin

class Roulette @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var rouletteSize = 0
    private var rouletteDataList = listOf<String>()
    private var shapeColors = arrayOf<String>()
    private var rouletteTextSize = 0f
    private var emptyMessage = ""
    private var textColor = Color.BLACK
    private var rouletteBorderLineColor = Color.BLACK

    private val strokePaint = Paint()
    private val fillPaint = Paint()
    private val textPaint = Paint()

    init {
        val typedArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.RouletteView,
            defStyleAttr,
            0
        )

        rouletteBorderLineColor = typedArray.getColor(
            R.styleable.RouletteView_rouletteBorderLineColor,
            Color.BLACK
        )

        textColor = typedArray.getColor(
            R.styleable.RouletteView_textColor,
            Color.BLACK
        )

        rouletteSize = typedArray.getInt(
            R.styleable.RouletteView_rouletteSize,
            Constant.DEFAULT_ROULETTE_SIZE
        )

        rouletteTextSize = typedArray.getDimension(
            R.styleable.RouletteView_textSize,
            Constant.DEFAULT_TEXT_SIZE
        )

        emptyMessage = typedArray.getString(
            R.styleable.RouletteView_emptyMessage
        ) ?: Constant.DEFAULT_EMPTY_MESSAGE

        typedArray.recycle()

        strokePaint.apply {
            color = rouletteBorderLineColor
            style = Paint.Style.STROKE
            strokeWidth = Constant.DEFAULT_CIRCLE_BORDER_LINE_HEIGHT
            isAntiAlias = true
        }

        fillPaint.apply {
            style = Paint.Style.FILL
            isAntiAlias = true
        }

        textPaint.apply {
            color = Color.BLACK
            textSize = rouletteTextSize
            textAlign = Paint.Align.CENTER
        }

        shapeColors = resources.getStringArray(R.array.shape_colors)
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val rectLeft = left + paddingLeft + Constant.DEFAULT_PADDING
        val rectRight = right - paddingRight - Constant.DEFAULT_PADDING
        val rectTop = height / 2f - rectRight / 2f + paddingTop + Constant.DEFAULT_PADDING
        val rectBottom = height / 2f + rectRight / 2f - paddingRight -Constant. DEFAULT_PADDING

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
                val y = (centerY + (radius * sin(medianAngle))).toFloat() + Constant.DEFAULT_PADDING

                val text = if (i > rouletteDataList.size - 1)  emptyMessage else rouletteDataList[i]
                canvas?.drawText(text, x, y, textPaint)
            }

            fillPaint.color = Color.BLACK
            canvas?.drawCircle(centerX, centerY, 15f, fillPaint)
        } else throw RuntimeException("size out of roulette")
    }

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

    fun rotateRoulette(toDegrees: Float, duration: Long): Single<String> {
        return Single.create {
            val animListener = object : Animation.AnimationListener {
                override fun onAnimationRepeat(animation: Animation?) {}

                override fun onAnimationStart(animation: Animation?) {
                    it.onSuccess("")
                }

                override fun onAnimationEnd(animation: Animation?) {
                    it.onSuccess(getRouletteRotateResult(toDegrees))
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
    }

    private fun getRouletteRotateResult(degrees: Float): String {
        val moveDegrees = degrees % 360
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
    fun setRouletteBorderLineColor(borderLineColor: Int) {
        this.rouletteBorderLineColor = borderLineColor
        invalidate()
    }

    fun getRouletteBorderLineColor(): Int = rouletteBorderLineColor

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

    fun setRouletteTextSize(textSize: Float) {
        rouletteTextSize = textSize
        invalidate()
    }

    fun getRouletteTextSize(): Float = rouletteTextSize

    fun setEmptyMessage(emptyMessage: String) {
        this.emptyMessage = emptyMessage
        invalidate()
    }

    fun getEmptyMessage(): String = emptyMessage

    fun setTextColor(textColor: Int) {
        this.textColor = textColor
        invalidate()
    }

    fun getTextColor(): Int = textColor
}