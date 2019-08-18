package com.mlcorrea.roundup.ui.feature.roundup.adapter

import android.view.View
import android.widget.TextView
import com.mlcorrea.roundup.R
import com.mlcorrea.roundup.ui.renders.ViewHolderA

/**
 * Created by manuel on 17/08/19
 */
class AccountsViewModel(itemView: View) : ViewHolderA(itemView) {
    val uiCurrency: TextView = itemView.findViewById<View>(R.id.ui_currency) as TextView
    val uiDate: TextView = itemView.findViewById<View>(R.id.ui_date) as TextView
}