package com.mlcorrea.roundup.ui.renders.base

import android.view.View
import android.widget.TextView
import com.mlcorrea.roundup.R
import com.mlcorrea.roundup.ui.renders.ViewHolderA

/**
 * Created by manuel.correa on 27/03/2018.
 */
class DefaultEmptyStateViewHolder(itemView: View) : ViewHolderA(itemView) {

    val uiMessage: TextView = itemView.findViewById<View>(R.id.ui_text_description) as TextView
}