package com.gdg.pune.devfest19.viewholder

import android.view.View
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import com.gdg.pune.devfest19.R

class OpenSourceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var titleView: TextView = itemView.findViewById(R.id.opensource_title)
    var descriptionView: TextView = itemView.findViewById(R.id.opensource_description)


    fun bind(@StringRes title: Int, @StringRes description: Int) {
        titleView.setText(title)
        descriptionView.setText(description)
    }

    fun setOnClickListener(onClickListener: View.OnClickListener) {
        itemView.setOnClickListener(onClickListener)
    }
}