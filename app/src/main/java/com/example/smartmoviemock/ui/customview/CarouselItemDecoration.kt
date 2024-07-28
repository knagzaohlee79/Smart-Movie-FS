package com.example.smartmoviemock.ui.customview

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class CarouselItemDecoration(private val horizontalSpacing: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        if (position == RecyclerView.NO_POSITION) return

        if (position == 0) {
            outRect.left = horizontalSpacing
        }
        outRect.right = horizontalSpacing
    }
}