package com.gdg.pune.devfest19.model

import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
class Developer(
    var name: String? = null,
    var photoUrl: String? = null,
    var websiteLink: String? = null,
    var gitHubLink: String? = null,
    var twitterLink: String? = null,
    var linkedInLink: String? = null
)