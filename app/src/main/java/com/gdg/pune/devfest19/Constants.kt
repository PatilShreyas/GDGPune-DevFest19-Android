package com.gdg.pune.devfest19

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore

object Constants {
    val announcementsRef = FirebaseFirestore.getInstance().collection("announcements")
    val usersRef = FirebaseFirestore.getInstance().collection("users")
    val discussRef = FirebaseFirestore.getInstance().collection("discuss")
    val commentsRef = FirebaseDatabase.getInstance().getReference("discuss-comments")

    val scheduleRef = FirebaseFirestore.getInstance().collection("schedule")
    val speakersRef = FirebaseFirestore.getInstance().collection("speakers")
    val organisersRef = FirebaseFirestore.getInstance().collection("organisers")
    val developersRef = FirebaseFirestore.getInstance().collection("developersRef")
}