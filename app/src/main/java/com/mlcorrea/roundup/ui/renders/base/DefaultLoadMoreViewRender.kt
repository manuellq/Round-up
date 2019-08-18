package com.mlcorrea.roundup.ui.renders.base

import com.mlcorrea.roundup.domain.network.NetworkRequestState
import com.mlcorrea.roundup.ui.renders.ViewHolderA
import com.mlcorrea.roundup.ui.renders.ViewRenderer

/**
 * Created by manuel.correa on 21/03/2018.
 */
abstract class DefaultLoadMoreViewRender<VH : ViewHolderA> :
    ViewRenderer<NetworkRequestState, VH>(NetworkRequestState::class.java)