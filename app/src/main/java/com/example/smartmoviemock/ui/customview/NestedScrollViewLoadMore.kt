package com.example.smartmoviemock.ui.customview

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.core.widget.NestedScrollView

class NestedScrollViewLoadMore @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : NestedScrollView(context, attrs, defStyleAttr) {

    private var loadMoreListener: (() -> Unit)? = null
    private var isLoading = false
    private var reachedBottom = false
    private var lastY = 0f

    init {
        setOnScrollChangeListener { _, _, scrollY, _, _ ->
            reachedBottom = scrollY == (getChildAt(0).measuredHeight - measuredHeight)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                // Save the Y position when the touch event starts
                lastY = event.rawY
            }
            MotionEvent.ACTION_MOVE -> {
                // Check the direction of movement
                val currentY = event.rawY
                val deltaY = lastY - currentY
                val movingYThreshHold = 30f
                if (deltaY >= movingYThreshHold && reachedBottom && !isLoading) {
                    // Moving upwards
                    loadMoreListener?.invoke()
                    isLoading = true
                }
                // Update the last Y position
                lastY = currentY
            }
        }
        return super.dispatchTouchEvent(event)
    }

    fun setOnLoadMoreListener(listener: () -> Unit) {
        loadMoreListener = listener
    }

    fun setLoadingComplete() {
        isLoading = false
    }
}