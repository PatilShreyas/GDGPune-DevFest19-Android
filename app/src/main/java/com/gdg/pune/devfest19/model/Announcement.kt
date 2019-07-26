package com.gdg.pune.devfest19.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.ServerTimestamp

@IgnoreExtraProperties
class Announcement(
    var title: String? = null,
    var body: String? = null,
    @ServerTimestamp var timestamp: Timestamp? = null
)