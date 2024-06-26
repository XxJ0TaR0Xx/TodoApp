package com.example.todoapp.ui.collapsingToolbar

import android.content.Context
import android.util.AttributeSet
import com.example.todoapp.R
import com.google.android.material.appbar.AppBarLayout

class CollapsibleAppbar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppBarLayout(context, attrs, defStyleAttr), AppBarLayout.OnOffsetChangedListener {
    private val toolbarElevation = context.resources.getDimension(R.dimen.toolbar_elevation)

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        addOnOffsetChangedListener(this)
    }

    override fun onDetachedFromWindow() {
        removeOnOffsetChangedListener(this)
        super.onDetachedFromWindow()
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        val progress = -verticalOffset / appBarLayout?.totalScrollRange?.toFloat()!!
        appBarLayout.elevation = if (progress == 1f) {
            toolbarElevation
        } else {
            0f
        }
    }
}