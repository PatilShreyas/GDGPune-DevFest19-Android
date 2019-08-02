package com.gdg.pune.devfest19.ui

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.gdg.pune.devfest19.Constants
import com.gdg.pune.devfest19.R
import com.gdg.pune.devfest19.model.Session
import com.gdg.pune.devfest19.utils.TimeUtils
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.android.synthetic.main.activity_session_details.*
import kotlinx.android.synthetic.main.content_session_details.*

class SessionDetailsActivity : AppCompatActivity() {

    private lateinit var mSessionReference: DocumentReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_session_details)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Get session key
        val sessionKey = intent.getStringExtra(SESSION_KEY)

        if (sessionKey != null) {
            mSessionReference = Constants.scheduleRef.document(sessionKey)
        } else {
            finish()
        }
    }

    override fun onStart() {
        super.onStart()

        mSessionReference.addSnapshotListener { snapshot: DocumentSnapshot?,
                                                exception: FirebaseFirestoreException? ->
            if (snapshot != null) {
                if (snapshot.exists()) {
                    val session = snapshot.toObject(Session::class.java)

                    if (session != null) {
                        schedule_title.text = session.title
                        schedule_speaker.text = session.speaker
                        schedule_time.text = TimeUtils.getFormattedTime(session.timestamp!!)
                        schedule_level.text = session.level
                        schedule_tag.text = session.tag
                        schedule_desription.text = session.description

                        (schedule_tag.compoundDrawablesRelative[0] as? GradientDrawable)?.setColor(
                            Color.parseColor(
                                session.chipColor
                            )
                        )

                    } else {
                        finish()
                    }
                } else {
                    finish()
                }
            } else {
                finish()
            }

            exception?.printStackTrace()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val SESSION_KEY = "session_key"
    }
}
