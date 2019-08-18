package com.mlcorrea.roundup.ui.feature.transaction.adapter

import android.view.View
import android.widget.TextView
import com.mlcorrea.roundup.R
import com.mlcorrea.roundup.ui.renders.ViewHolderA

/**
 * Created by manuel on 17/08/19
 */
class TransactionsViewHolder(itemView: View) : ViewHolderA(itemView) {
    val uiCurrency: TextView = itemView.findViewById<View>(R.id.ui_currency) as TextView
    val uiAmount: TextView = itemView.findViewById<View>(R.id.ui_amount) as TextView
    val uiType: TextView = itemView.findViewById<View>(R.id.ui_type) as TextView
    val uiReference: TextView = itemView.findViewById<View>(R.id.ui_reference) as TextView
}