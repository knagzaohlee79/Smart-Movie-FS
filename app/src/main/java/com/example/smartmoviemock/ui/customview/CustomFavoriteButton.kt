package com.example.smartmoviemock.ui.customview

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageButton
import com.example.smartmoviemock.R

class CustomFavoriteButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageButton(context, attrs, defStyleAttr) {


    private var _isFavorite: Boolean = false
    var isFavorite: Boolean
        get() = _isFavorite
        set(value) {
            _isFavorite = value
            updateStarIcon(_isFavorite)
        }

    private var customClickListener: (() -> Unit)? = null
    fun setCustomClickListener(listener: () -> Unit) {
        customClickListener = listener
    }

    init {
        super.setOnClickListener {
            _isFavorite = !_isFavorite
            updateStarIcon(_isFavorite)
            customClickListener?.invoke()
        }
    }

    private fun updateStarIcon(isFavorite: Boolean) {
        if (isFavorite) {
            this.setImageResource(R.drawable.ic_star_full)
        } else {
            this.setImageResource(R.drawable.ic_star_border)
        }
    }
}