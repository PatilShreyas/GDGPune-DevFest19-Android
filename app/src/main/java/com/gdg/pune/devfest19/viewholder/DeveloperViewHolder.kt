package com.gdg.pune.devfest19.viewholder

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gdg.pune.devfest19.R
import com.gdg.pune.devfest19.model.Developer

class DeveloperViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var nameView: TextView = itemView.findViewById(R.id.developer_name)
    var profileView: ImageView = itemView.findViewById(R.id.developer_profile)
    var websiteButton: ImageButton = itemView.findViewById(R.id.buttonWeb)
    var twitterButton: ImageButton = itemView.findViewById(R.id.buttonTwitter)
    var githubButton: ImageButton = itemView.findViewById(R.id.buttonGitHub)
    var linkedInButton: ImageButton = itemView.findViewById(R.id.buttonLinkedIn)

    fun bind(developer: Developer, context: Context) {
        nameView.text = developer.name

        // Init Profile View
        if (developer.photoUrl != null) {
            Glide.with(itemView)
                .load(developer.photoUrl)
                .centerCrop()
                .placeholder(R.drawable.ic_account_circle)
                .into(profileView)
        } else {
            profileView.setImageResource(R.drawable.ic_account_circle)
        }

        // Init Social Links
        val viewIntent = Intent(Intent.ACTION_VIEW)

        // Set flag for crash fix. Issue #21
        viewIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

        val onLinkClickListener = View.OnClickListener {
            when (it.id) {
                R.id.buttonWeb -> viewIntent.data = Uri.parse(developer.websiteLink)
                R.id.buttonTwitter -> viewIntent.data = Uri.parse(developer.twitterLink)
                R.id.buttonGitHub -> viewIntent.data = Uri.parse(developer.gitHubLink)
                R.id.buttonLinkedIn -> viewIntent.data = Uri.parse(developer.linkedInLink)
            }

            // Launch Link in browser
            context.startActivity(viewIntent)
        }

        websiteButton.setOnClickListener(onLinkClickListener)
        githubButton.setOnClickListener(onLinkClickListener)
        twitterButton.setOnClickListener(onLinkClickListener)
        linkedInButton.setOnClickListener(onLinkClickListener)

        developer.run {
            // Website Button
            if (websiteLink.isNullOrBlank()) {
                websiteButton.gone()
            } else {
                websiteButton.visible()
            }

            // Twitter Button
            if (twitterLink.isNullOrBlank()) {
                twitterButton.gone()
            } else {
                twitterButton.visible()
            }

            // GitHub Button
            if (gitHubLink.isNullOrBlank()) {
                githubButton.gone()
            } else {
                githubButton.visible()
            }

            // LinkedIn Button
            if (linkedInLink.isNullOrBlank()) {
                linkedInButton.gone()
            } else {
                linkedInButton.visible()
            }
        }
    }
}

private fun ImageButton.visible() {
    visibility = View.VISIBLE
}


private fun ImageButton.gone() {
    visibility = View.GONE
}