package com.gdg.pune.devfest19.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.gdg.pune.devfest19.Constants
import com.gdg.pune.devfest19.R
import com.gdg.pune.devfest19.model.Speaker
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.android.synthetic.main.activity_speaker_details.*
import kotlinx.android.synthetic.main.layout_social_links.*

class SpeakerDetailsActivity : AppCompatActivity() {

    private lateinit var mSpeakerReference: DocumentReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_speaker_details)

        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.elevation = 0.0F

        val speakerKey = intent.getStringExtra(SPEAKER_KEY)
        if (speakerKey != null) {
            mSpeakerReference = Constants.speakersRef.document(speakerKey)
        } else {
            finish()
        }
    }

    override fun onStart() {
        super.onStart()

        mSpeakerReference.addSnapshotListener { snapshot: DocumentSnapshot?,
                                                exception: FirebaseFirestoreException? ->

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

                        //Append "https://" to url links that don't have the prefix
                        speaker.run {
                            // Website Button
                            if (websiteLink.isNullOrBlank()) {
                                buttonWeb.gone()
                            } else {
                                if (!websiteLink!!.startsWith("http://") && !websiteLink!!.startsWith("https://")) {
                                    websiteLink = "http://$websiteLink";
                                }
                                buttonWeb.visible()
                            }


                            // Twitter Button
                            if (twitterLink.isNullOrBlank()) {
                                buttonTwitter.gone()
                            } else {
                                if (!twitterLink!!.startsWith("http://") && !twitterLink!!.startsWith("https://")) {
                                    twitterLink = "http://$twitterLink"
                                }
                                buttonTwitter.visible()
                            }


                            // GitHub Button
                            if (gitHubLink.isNullOrBlank()) {
                                buttonGitHub.gone()
                            } else {
                                if (!gitHubLink!!.startsWith("http://") && !gitHubLink!!.startsWith("https://")) {
                                    gitHubLink = "http://$gitHubLink"
                                }
                                buttonGitHub.visible()
                            }


                            // LinkedIn Button
                            if (linkedInLink.isNullOrBlank()) {
                                buttonLinkedIn.gone()
                            } else {
                                if (!linkedInLink!!.startsWith("http://") && !linkedInLink!!.startsWith("https://")) {
                                    linkedInLink = "http://$linkedInLink"
                                }
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

            exception?.printStackTrace()
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun ImageButton.visible() {
        visibility = View.VISIBLE
    }


    private fun ImageButton.gone() {
        visibility = View.GONE
    }

    companion object {
        const val SPEAKER_KEY = "speaker_key"
    }

}
