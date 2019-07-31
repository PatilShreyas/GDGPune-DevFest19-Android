package com.gdg.pune.devfest19.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
class Schedule(
    var title: String? = null,
    var speaker: String? = null,
    var tag: String? = null,
    var chipColor: String? = null,
    var description: String? = null,
    var level: String? = null,
    var clickable: Boolean = false,
    var timestamp: Timestamp? = null
)