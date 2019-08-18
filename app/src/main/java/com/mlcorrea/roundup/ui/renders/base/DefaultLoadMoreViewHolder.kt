package com.mlcorrea.roundup.ui.renders.base

import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import com.mlcorrea.roundup.R
import com.mlcorrea.roundup.ui.renders.ViewHolderA

/**
 * Created by manuel.correa on 20/03/2018.
 */
class DefaultLoadMoreViewHolder(itemView: View) : ViewHolderA(itemView) {

    val uiRetryBtn: Button = itemView.findViewById<View>(R.id.retryLoadingButton) as Button
    val uiProgressBar: ProgressBar =
        itemView.findViewById<View>(R.id.loadingProgressBar) as ProgressBar
    val uiErrorMessage: TextView = itemView.findViewById<View>(R.id.errorMessageTextView) as TextView
}