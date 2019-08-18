package com.mlcorrea.roundup.ui.renders.baserenders

import android.view.ViewGroup
import com.mlcorrea.roundup.R
import com.mlcorrea.roundup.ui.renders.base.DefaultShimmerViewHolder
import com.mlcorrea.roundup.ui.renders.base.DefaultShimmerViewRender
import com.mlcorrea.roundup.ui.renders.model.ShimmerModal

/**
 * Created by manuel.correa on 21/03/2018.
 */
class ShimmerViewRender : DefaultShimmerViewRender() {

    override fun bindView(model: ShimmerModal, holder: DefaultShimmerViewHolder) {
    }

    override fun createViewHolder(parent: ViewGroup): DefaultShimmerViewHolder {
        return DefaultShimmerViewHolder(
            inflate(R.layout.layout_shimmer_list_default, parent)
        )
    }
}