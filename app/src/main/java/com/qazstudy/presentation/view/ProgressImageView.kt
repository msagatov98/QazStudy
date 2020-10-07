package com.qazstudy.presentation.view

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.annotation.Nullable

class ProgressImageView  : View, View.OnClickListener {
    var segment = Path()
    var paint = Paint()
    var pm: PathMeasure? = null
    var TAG = "View"
    var path: Path? = null

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(
        context: Context?,
        @Nullable attrs: AttributeSet?
    ) : super(context, attrs) {
        init()
    }

    constructor(
        context: Context?,
        @Nullable attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        init()
    }

    constructor(
        context: Context?,
        @Nullable attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        init()
    }

    fun init() {
        setOnClickListener(this)
        paint.color = -0x55ff0100
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 10f
        paint.strokeCap = Paint.Cap.ROUND
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        val src = Path()
        src.moveTo(8.80000f, 0.630000f) //0
        src.lineTo(26.030001f, 0.630000f) //1
        src.lineTo(32.109999f, 14.030000f) //2
        src.lineTo(26.030001f, 28.440001f) //3
        src.lineTo(7.800000f, 28.440001f) //4
        src.lineTo(2.200000f, 14.030000f) //5
        src.lineTo(8.80000f, 0.630000f) //6
        val m = Matrix()
        val srcRect = RectF(0F, 0F, 32F, 40F)
        val dstRect = RectF(0F, 0F, w.toFloat(), h.toFloat())
        dstRect.inset(paint.strokeWidth / 2, paint.strokeWidth / 2)
        m.setRectToRect(srcRect, dstRect, Matrix.ScaleToFit.CENTER)
        val dst = Path()
        src.transform(m, dst)
        pm = PathMeasure(dst, true)
        pm!!.getSegment(0f, pm!!.length / 2, segment, true)
        //  segment.rLineTo(0, 0);
        setProgress(pm!!.length)
    }

    fun setProgress(progress: Float) {
        segment.reset()
        val start = 0f
        if (start < progress) {
            pm!!.getSegment(start, progress, segment, true)
        } /*else {
        pm.getSegment(start, length, segment, true);
      //  pm.getSegment(0, end, segment, true);
    }*/
        segment.rLineTo(0f, 0f)
        invalidate()
    }

    override fun onClick(v: View) {
        ObjectAnimator.ofFloat(
            this, "progress", 0f,
            pm!!.length
        ).setDuration(5 * 2500.toLong()).start()
        Log.d(TAG, pm!!.length.toString() + "")
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawPath(segment, paint)
    }
}