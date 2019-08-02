package com.gdg.pune.devfest19.viewholder

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gdg.pune.devfest19.R
import com.gdg.pune.devfest19.model.Session
import com.gdg.pune.devfest19.utils.TimeUtils

class ScheduleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var titleView: TextView = itemView.findViewById(R.id.schedule_title)
    var amPmView: TextView = itemView.findViewById(R.id.schedule_ampm)
    var timeView: TextView = itemView.findViewById(R.id.schedule_time)
    var chipView: TextView = itemView.findViewById(R.id.schedule_tag)

    fun bind(schedule: Session, onClickListener: View.OnClickListener?) {
        titleView.text = schedule.title
        timeView.text = TimeUtils.getTime(schedule.timestamp!!)
        amPmView.text = TimeUtils.getAmPm(schedule.timestamp!!)
        chipView.text = schedule.tag

        // Check if clickable
        if (schedule.clickable) {
            itemView.setOnClickListener(onClickListener)
        } else {
            chipView.visibility = View.GONE
        }

        // Check for chip color
        if (schedule.chipColor != null) {
            (chipView.compoundDrawablesRelative[0] as? GradientDrawable)?.setColor(Color.parseColor(schedule.chipColor))
        }
    }
}