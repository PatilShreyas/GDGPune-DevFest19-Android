package com.gdg.pune.devfest19.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gdg.pune.devfest19.R
import com.gdg.pune.devfest19.model.Announcement
import com.gdg.pune.devfest19.utils.TimeUtils

class AnnouncementViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var titleView: TextView = itemView.findViewById(R.id.alert_title)
    var bodyView: TextView = itemView.findViewById(R.id.alert_body)
    var timeView: TextView = itemView.findViewById(R.id.alert_timestamp)


    fun bind(announcement: Announcement) {
        titleView.text = announcement.title
        bodyView.text = announcement.body
        timeView.text = TimeUtils.getPeriod(announcement.timestamp!!.toDate())
    }
}