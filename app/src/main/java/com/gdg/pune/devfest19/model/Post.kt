package com.gdg.pune.devfest19.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.ServerTimestamp

@IgnoreExtraProperties
class Post(
    var uid: String? = null,
    var authorName: String? = null,
    var body: String? = null,
    var profilePicUrl: String? = null,
    @ServerTimestamp var timestamp: Timestamp? = null
)