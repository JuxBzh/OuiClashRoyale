package com.jux.ouiclashroyale.old.common

import android.content.Context
import android.support.v4.widget.SwipeRefreshLayout
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup

/**
 * A {@link SwipeRefreshLayout} that supports multiple nested views (e.g. a scrollable view and an
 * empty view inside a FrameLayout)
 */
class NestedSwipeRefreshLayout : SwipeRefreshLayout {
    var mContainer: ViewGroup? = null

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun canChildScrollUp(): Boolean {
        // The swipe refresh layout has 2 children; the circle refresh indicator
        // and the view container. The container is needed here
        val container = getContainer()

        // The container has 2 children; the empty view and the scrollable view.
        if (container?.childCount != 2) {
            throw NestedSwipeRefreshLayoutException("Container must have an empty view and content view")
        }

        // Use whichever one is visible and test that it can scroll
        var view = container.getChildAt(0)
        if (view.visibility != View.VISIBLE) {
            view = container.getChildAt(1)
        }

        return view.canScrollVertically(-1)
    }

    private fun getContainer(): ViewGroup? {
        if (mContainer != null) return mContainer

        for (i in 0..childCount) {
            if (getChildAt(i) is ViewGroup) {
                mContainer = getChildAt(i) as ViewGroup?
                break
            }
        }

        if (mContainer == null) throw NestedSwipeRefreshLayoutException("Container view not found")

        return mContainer
    }

    private inner class NestedSwipeRefreshLayoutException(message: String) : RuntimeException(message)
}