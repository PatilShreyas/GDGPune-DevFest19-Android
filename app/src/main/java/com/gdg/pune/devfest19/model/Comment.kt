package com.gdg.pune.devfest19.model

import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
class Comment(
    var uid: String? = null,
    var authorName: String? = null,
    var body: String? = null,
    var profilePicUrl: String? = null
)