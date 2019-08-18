package com.mlcorrea.roundup.ui.renders.binder

import android.view.View
import androidx.annotation.IdRes

/**
 * Created by manuel.correa on 29/05/2018
 */
interface ViewFinder {

    fun <V : View> find(@IdRes id: Int, viewProvider: ViewProvider<V>): ViewFinder
    fun <V : View> find(@IdRes id: Int): V
}