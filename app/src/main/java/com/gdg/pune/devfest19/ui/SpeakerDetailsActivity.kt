package com.gdg.pune.devfest19.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.gdg.pune.devfest19.Constants
import com.gdg.pune.devfest19.R
import com.gdg.pune.devfest19.model.Speaker
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.android.synthetic.main.activity_speaker_details.*
import kotlinx.android.synthetic.main.content_speaker_details.*
import kotlinx.android.synthetic.main.layout_social_links_speaker.*

class SpeakerDetailsActivity : AppCompatActivity() {

    private lateinit var mSpeakerReference: DocumentReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_speaker_details)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val speakerKey = intent.getStringExtra(SPEAKER_KEY)
        if (speakerKey != null) {
            mSpeakerReference = Constants.speakersRef.document(speakerKey)
        } else {
            finish()
            return
        }

        initSpeakerView()
    }

    private fun initSpeakerView() {
        mSpeakerReference.addSnapshotListener { snapshot: DocumentSnapshot?,
                                                exception: FirebaseFirestoreException? ->
            if (!this.isDestroyed) {
                if (snapshot != null) {
                    if (snapshot.exists()) {
                        val speaker = snapshot.toObject(Speaker::class.java)

                        if (speaker != null) {
                            speakerName.text = speaker.name
                            speakerDesignation.text = speaker.designation
                            speakerIntro.text = speaker.introduction

                            if (speaker.photoUrl != null) {
                                Glide.with(this)
                                    .load(speaker.photoUrl)
                                    .centerCrop()
                                    .into(speakerImg)
                            } else {
                                speakerImg.setImageResource(R.drawable.ic_account_circle)
                            }

                            val viewIntent = Intent(Intent.ACTION_VIEW)

                            val onLinkClickListener = View.OnClickListener {
                                when (it.id) {
                                    R.id.buttonWeb -> viewIntent.data = Uri.parse(speaker.websiteLink)
                                    R.id.buttonTwitter -> viewIntent.data = Uri.parse(speaker.twitterLink)
                                    R.id.buttonGitHub -> viewIntent.data = Uri.parse(speaker.gitHubLink)
                                    R.id.buttonLinkedIn -> viewIntent.data = Uri.parse(speaker.linkedInLink)
                                }
                                startActivity(viewIntent)
                            }

                            buttonWeb.setOnClickListener(onLinkClickListener)
                            buttonGitHub.setOnClickListener(onLinkClickListener)
                            buttonTwitter.setOnClickListener(onLinkClickListener)
                            buttonLinkedIn.setOnClickListener(onLinkClickListener)

                            // Init Social Links
                            speaker.run {
                                // Website Button
                                if (websiteLink.isNullOrBlank()) {
                                    buttonWeb.gone()
                                } else {
                                    buttonWeb.visible()
                                }

                                // Twitter Button
                                if (twitterLink.isNullOrBlank()) {
                                    buttonTwitter.gone()
                                } else {
                                    buttonTwitter.visible()
                                }

                                // GitHub Button
                                if (gitHubLink.isNullOrBlank()) {
                                    buttonGitHub.gone()
                                } else {
                                    buttonGitHub.visible()
                                }

                                // LinkedIn Button
                                if (linkedInLink.isNullOrBlank()) {
                                    buttonLinkedIn.gone()
                                } else {
                                    buttonLinkedIn.visible()
                                }
                            }
                        } else {
                            finish()
                        }
                    } else {
                        finish()
                    }
                } else {
                    finish()
                }
            }
            exception?.printStackTrace()
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun Button.visible() {
        visibility = View.VISIBLE
    }


    private fun Button.gone() {
        visibility = View.GONE
    }

    companion object {
        const val SPEAKER_KEY = "speaker_key"
    }

}
