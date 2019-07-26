package com.gdg.pune.devfest19.utils

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class TimeUtils {
    companion object {
        public fun getPeriod(past: Date): String {
            val now = Date()
            val seconds = TimeUnit.MILLISECONDS.toSeconds(now.time - past.time)
            val minutes = TimeUnit.MILLISECONDS.toMinutes(now.time - past.time)
            val hours = TimeUnit.MILLISECONDS.toHours(now.time - past.time)
            val days = TimeUnit.MILLISECONDS.toDays(now.time - past.time)

            if (seconds < 60) {
                return "Few seconds ago"
            } else if (minutes < 60) {
                return "$minutes minutes ago"
            } else if (hours < 24) {
                return "$hours hours ago"
            } else if (days in 0..1) {
                val dateFormat = SimpleDateFormat("hh:mm a").format(past).toString()
                return "Yesterday, $dateFormat"
            } else {
                return SimpleDateFormat("dd/MM/yy, hh:mm a").format(past).toString()
            }
        }
    }
}