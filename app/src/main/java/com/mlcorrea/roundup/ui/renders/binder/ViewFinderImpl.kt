package com.mlcorrea.roundup.ui.renders.binder

import android.util.SparseArray
import android.view.View
import androidx.annotation.IdRes

/**
 * Created by manuel.correa on 29/05/2018
 */
class ViewFinderImpl(private val itemView: View) : ViewFinder {

    private val mCachedViews = SparseArray<View>()

    override fun <V : View> find(id: Int, viewProvider: ViewProvider<V>): ViewFinder {
        viewProvider.provide(findViewById(id) as V)
        return this
    }

    override fun <V : View> find(id: Int): V {
        return findViewById(id) as V
    }

    private fun findViewById(@IdRes id: Int): View {
        val cachedView = mCachedViews.get(id)
        if (cachedView != null) {
            return cachedView
        }
        val view = itemView.findViewById<View>(id)
        mCachedViews.put(id, view)
        return view
    }
}