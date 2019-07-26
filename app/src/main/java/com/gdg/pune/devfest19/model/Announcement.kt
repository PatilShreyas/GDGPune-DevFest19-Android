package com.gdg.pune.devfest19.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.ServerTimestamp
import java.util.*

@IgnoreExtraProperties
class Alert(
    var title: String? = null,
    var body: String? = null,
    var file: String? = null,
    @ServerTimestamp var timestamp: Timestamp? = null,
    var visited: HashMap<String, Boolean> = HashMap()
)