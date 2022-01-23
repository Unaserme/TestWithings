package com.synaone.testwithings.presentation.common.views

import android.content.Context
import android.util.AttributeSet
import android.widget.Checkable
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.res.use
import com.synaone.testwithings.presentation.R

/**
 * ImageView, with a checked state.
 */
class CheckableImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(
        context,
        attrs,
        defStyleAttr
    ), Checkable {

    private var isChecked = false
    private var broadcasting = false

    /** Checked state change listener. */
    var onCheckedChangeListener: ((Boolean) -> Unit)? = null

    init {
        context.obtainStyledAttributes(attrs, R.styleable.CheckableImageView).use {
            setChecked(it.getBoolean(R.styleable.CheckableImageView_android_checked, false))
        }

        setOnClickListener { toggle() }
    }

    override fun onCreateDrawableState(
        extraSpace: Int
    ): IntArray {
        val drawableState = super.onCreateDrawableState(extraSpace + 1)

        if (isChecked) {
            mergeDrawableStates(drawableState, CHECKED_STATE_SET)
        }

        return drawableState
    }

    override fun toggle() {
        setChecked(!isChecked)
    }

    override fun isChecked(): Boolean {
        return isChecked
    }

    override fun setChecked(
        checked: Boolean
    ) {
        if (isChecked != checked) {
            isChecked = checked
            refreshDrawableState()

            // Avoid infinite recursions if setChecked() is called from a listener
            if (broadcasting) {
                return
            }

            broadcasting = true

            if (onCheckedChangeListener != null) {
                onCheckedChangeListener?.invoke(checked)
            }

            broadcasting = false
        }
    }

    companion object {
        private val CHECKED_STATE_SET = intArrayOf(android.R.attr.state_checked)
    }
}