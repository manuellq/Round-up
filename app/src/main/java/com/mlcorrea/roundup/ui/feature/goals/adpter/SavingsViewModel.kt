package com.mlcorrea.roundup.ui.feature.goals.adpter

import android.view.View
import android.widget.TextView
import com.mlcorrea.roundup.R
import com.mlcorrea.roundup.ui.renders.ViewHolderA

/**
 * Created by manuel on 18/08/19
 */
class SavingsViewModel(itemView: View) : ViewHolderA(itemView) {
    val uiName: TextView = itemView.findViewById<View>(R.id.ui_name) as TextView
    val uiCurrency: TextView = itemView.findViewById<View>(R.id.ui_currency) as TextView
    val uiAmount: TextView = itemView.findViewById<View>(R.id.ui_amount) as TextView
    val uiTargetAmount: TextView = itemView.findViewById<View>(R.id.ui_amount_goal) as TextView
}