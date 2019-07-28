package com.gdg.pune.devfest19.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gdg.pune.devfest19.R
import com.gdg.pune.devfest19.model.Comment

class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var profileView: ImageView = itemView.findViewById(R.id.post_profileView)
    var authorView: TextView = itemView.findViewById(R.id.post_AuthorName)
    var bodyView: TextView = itemView.findViewById(R.id.commentBody)

    fun bind(comment: Comment) {
        authorView.text = comment.authorName
        bodyView.text = comment.body

        if (comment.profilePicUrl != null) {
            Glide.with(itemView)
                .load(comment.profilePicUrl)
                .centerCrop()
                .placeholder(R.drawable.ic_account_circle)
                .into(profileView)
        } else {
            profileView.setImageResource(R.drawable.ic_account_circle)
        }
    }

}