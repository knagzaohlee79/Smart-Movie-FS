package com.example.smartmoviemock.ui.customview

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smartmoviemock.ui.adapter.MovieAdapter
import com.example.smartmoviemock.ui.customview.CarouselEffectTransformer

class CarouselRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    init {
        setUpRecyclerView(this)
    }

    private fun setUpRecyclerView(recyclerView: RecyclerView) {

        recyclerView.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
        recyclerView.addItemDecoration(CarouselEffectTransformer(false))
    }

    fun setOrientation(isHorizontal: Boolean) {
        val adapter = (this.adapter as? MovieAdapter)
        adapter?.isGridLayout = isHorizontal

        if (isHorizontal) {
            this.layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )

            this.addItemDecoration(CarouselEffectTransformer(true))
        } else {
            this.layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL,
                false
            )

            this.addItemDecoration(CarouselEffectTransformer(false))
        }
    }
}