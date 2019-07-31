package com.gdg.pune.devfest19.model

import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
class Organiser(
    var name: String? = null,
    var introduction: String? = null,
    var photoUrl: String? = null,
    var url: String? = null
)