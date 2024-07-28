package com.example.smartmoviemock.ui.customview

import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs

class CarouselEffectTransformer(private val isHorizontal: Boolean = true) : RecyclerView.ItemDecoration() {
    private val scaleDownFactor = 0.25f // Scale down factor for side items
    private val scaleStartPosition = 0.5f // Start scaling when 50% of item is offscreen

    override fun onDrawOver(c: android.graphics.Canvas, parent: RecyclerView, state: RecyclerView.State) {
        setupEffect(isHorizontal, parent)
    }

    private fun setupEffect(isHorizontal: Boolean, recyclerView: RecyclerView) {
        var center = recyclerView.width / 2f
        if (!isHorizontal) {
            center = recyclerView.height / 2f
        }

        var d1 = scaleStartPosition * recyclerView.width
        if (!isHorizontal) {
            d1 = scaleStartPosition * recyclerView.height
        }

        val maxScale = 1f
        val minScale = 1f - scaleDownFactor

        for (i in 0 until recyclerView.childCount) {
            val child = recyclerView.getChildAt(i)

            var childCenter = (child.left + child.right) / 2f
            if (!isHorizontal) {
                childCenter = (child.top + child.bottom) / 2f
            }

            val d = abs(center - childCenter)
            val scale = if (d < d1) {
                minScale + (maxScale - minScale) * (1 - d / d1)
            } else {
                minScale
            }
            child.scaleX = scale
            child.scaleY = scale
        }
    }
}