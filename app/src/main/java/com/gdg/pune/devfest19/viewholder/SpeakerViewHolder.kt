package com.gdg.pune.devfest19.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gdg.pune.devfest19.R
import com.gdg.pune.devfest19.model.Speaker

class SpeakerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var speakerCard: CardView = itemView.findViewById(R.id.speaker_card)
    var nameView: TextView = itemView.findViewById(R.id.speaker_name)
    var designationView: TextView = itemView.findViewById(R.id.speaker_designation)
    var profileView: ImageView = itemView.findViewById(R.id.speaker_profile)

    fun bind(speaker: Speaker, onClickListener: View.OnClickListener?) {
        nameView.text = speaker.name
        designationView.text = speaker.designation

        if (speaker.photoUrl != null) {
            Glide.with(itemView)
                .load(speaker.photoUrl)
                .centerCrop()
                .placeholder(R.drawable.ic_account_circle)
                .into(profileView)
        } else {
            profileView.setImageResource(R.drawable.ic_account_circle)
        }
        speakerCard.setOnClickListener(onClickListener)
    }
}