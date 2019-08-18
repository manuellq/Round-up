package com.mlcorrea.roundup.ui.renders.base

import com.mlcorrea.roundup.ui.renders.ViewRenderer
import com.mlcorrea.roundup.ui.renders.model.ShimmerModal

/**
 * Created by manuel.correa on 21/03/2018.
 */
abstract class DefaultShimmerViewRender() :
    ViewRenderer<ShimmerModal, DefaultShimmerViewHolder>(ShimmerModal::class.java)