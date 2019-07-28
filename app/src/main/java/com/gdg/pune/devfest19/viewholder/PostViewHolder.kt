package com.gdg.pune.devfest19.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gdg.pune.devfest19.R
import com.gdg.pune.devfest19.model.Post
import com.gdg.pune.devfest19.utils.TimeUtils

class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var profileView: ImageView = itemView.findViewById(R.id.post_profileView)
    var authorView: TextView = itemView.findViewById(R.id.post_AuthorName)
    var messageView: TextView = itemView.findViewById(R.id.post_Message)
    var timeView: TextView = itemView.findViewById(R.id.post_TimeView)

    fun bind(post: Post, onClickListener: View.OnClickListener) {
        authorView.text = post.authorName
        messageView.text = post.body

        timeView.text = TimeUtils.getPeriod(post.timestamp!!.toDate())

        if (post.profilePicUrl != null) {
            Glide.with(itemView)
                .load(post.profilePicUrl)
                .centerCrop()
                .placeholder(R.drawable.ic_account_circle)
                .into(profileView)
        } else {
            profileView.setImageResource(R.drawable.ic_account_circle)
        }

        //Set ClickListener
        itemView.setOnClickListener(onClickListener)
    }

}