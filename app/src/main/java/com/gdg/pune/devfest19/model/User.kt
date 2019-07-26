package com.gdg.pune.devfest19.model

import com.google.firebase.firestore.IgnoreExtraProperties

// [START blog_user_class]
@IgnoreExtraProperties
data class User(
    var uid: String? = null,
    var name: String? = null,
    var email: String? = null,
    var pictureUrl: String? = null
)