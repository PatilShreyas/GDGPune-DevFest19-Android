package com.gdg.pune.devfest19.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
class Schedule(
    var title: String? = null,
    var speaker: String? = null,
    var tags: List<String>? = null,
    var description: String? = null,
    var level: String? = null,
    var timestamp: Timestamp? = null
)