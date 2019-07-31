package com.gdg.pune.devfest19.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gdg.pune.devfest19.R
import com.gdg.pune.devfest19.model.Organiser

class OrganiserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var organiserCard: CardView = itemView.findViewById(R.id.organiser_card)
    var nameView: TextView = itemView.findViewById(R.id.organiser_name)
    var introductionView: TextView = itemView.findViewById(R.id.organisation_introduction)
    var profileView: ImageView = itemView.findViewById(R.id.organiser_photo)

    fun bind(organiser: Organiser, onClickListener: View.OnClickListener?) {
        nameView.text = organiser.name
        introductionView.text = organiser.introduction

        if (organiser.photoUrl != null) {
            Glide.with(itemView)
                .load(organiser.photoUrl)
                .fitCenter()
                .into(profileView)
        }
        organiserCard.setOnClickListener(onClickListener)
    }
}