package com.mlcorrea.roundup.ui.renders.binder

/**
 * Created by manuel.correa on 29/05/2018
 */
interface ViewProvider<V> {

    fun provide(view: V)
}