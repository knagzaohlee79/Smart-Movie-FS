package com.example.smartmoviemock.ui.customview

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.example.smartmoviemock.databinding.CustomSearchBarBinding

class CustomSearchBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding = CustomSearchBarBinding.inflate(
        LayoutInflater.from(context),
        this,
        true
    )

    var text: String?
        get() = binding.etSearch.text.toString()
        set(value) {
            binding.etSearch.setText(value)
        }

    var onSearchIconClick: () -> Unit = {

    }

    var onTypeSearchKey: () -> Unit = {

    }

    var onClearIconClick: () -> Unit = {

    }

    var onCancelClick: () -> Unit = {}

    private val handler = Handler(Looper.getMainLooper())
    private var searchRunnable: Runnable? = null

    init {
        //show search icon and clear icon when user continues focus on search bar
        binding.etSearch.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus && binding.etSearch.text.isNotEmpty()) {
                setIconVisibility(true)
            }

            if (!hasFocus) {
                hideSoftKeyboard()
            }
        }


        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val text = s.toString()

                if (text.isNotEmpty()) {
                    setIconVisibility(true)
                } else {
                    setIconVisibility(false)
                }

                // Remove any existing callbacks
                searchRunnable?.let { handler.removeCallbacks(it) }

                // Post a new callback with a delay
                searchRunnable = Runnable {
                    onTypeSearchKey()
                }

                searchRunnable?.let {
                    handler.postDelayed(it, 500)
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        binding.ivClearIcon.setOnClickListener {
            binding.etSearch.text.clear()
            onClearIconClick()
        }

        binding.ivSearchIcon.setOnClickListener {
            binding.etSearch.clearFocus()

            onSearchIconClick()
            hideSoftKeyboard()
        }

        binding.textCancel.setOnClickListener {
            onCancelClick()
        }
    }

    private fun hideSoftKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.etSearch.windowToken, 0)
    }

    private fun changeIconDrawable(imageView: ImageView, drawableResId: Int) {
        val drawable = ContextCompat.getDrawable(context, drawableResId)
        imageView.setImageDrawable(drawable)
    }

    private fun setIconVisibility(isVisible: Boolean) {
        if (isVisible) {
            binding.ivClearIcon.visibility = View.VISIBLE
        } else {
            binding.ivClearIcon.visibility = View.GONE
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()

        searchRunnable?.let { handler.removeCallbacks(it) }
    }
}