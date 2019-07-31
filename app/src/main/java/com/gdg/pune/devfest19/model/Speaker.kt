package com.gdg.pune.devfest19.model

import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
class Speaker(
    var name: String? = null,
    var designation: String? = null,
    var photoUrl: String? = null,
    var introduction: String? = null,
    var gitHubLink: String? = null,
    var twitterLink: String? = null,
    var linkedInLink: String? = null
)