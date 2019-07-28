package com.gdg.pune.devfest19.ui.discuss

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.gdg.pune.devfest19.Constants
import com.gdg.pune.devfest19.R
import com.gdg.pune.devfest19.model.Post
import com.gdg.pune.devfest19.ui.BaseActivity
import com.gdg.pune.devfest19.ui.discuss.fragment.CommentBottomSheetFragment
import com.gdg.pune.devfest19.utils.TimeUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog
import kotlinx.android.synthetic.main.activity_discuss_details_view.*
import kotlinx.android.synthetic.main.item_discuss_detail.*

class DiscussDetailsActivity : BaseActivity() {

    private lateinit var mDocumentReference: DocumentReference
    private var mPostId: String? = null

    private var mCommentFragment: CommentBottomSheetFragment? = null

    companion object {
        const val KEY_DISCUSS_PATH = "key_disc_path"
        const val KEY_DISCUSS_ID = "key_discuss_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_discuss_details_view)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val path = intent.getStringExtra(KEY_DISCUSS_PATH)
        val postId = intent.getStringExtra(KEY_DISCUSS_ID)

        if (path != null && postId != null) {
            mDocumentReference = FirebaseFirestore.getInstance().document(path)
            mPostId = postId
        } else {
            finish()
        }

        swipeRefreshLayout.isRefreshing = true

        buttonComments.visibility = View.GONE
        buttonComments.setOnClickListener {
            showCommentFragment(postId)
        }
    }

    override fun onStart() {
        super.onStart()

        mDocumentReference.get().addOnSuccessListener { documentSnapshot ->

            if (documentSnapshot != null) {
                if (documentSnapshot.exists()) {
                    val post = documentSnapshot.toObject(Post::class.java)
                    mPostId = documentSnapshot.id
                    card_alert.visibility = View.VISIBLE
                    swipeRefreshLayout.isRefreshing = false
                    swipeRefreshLayout.isEnabled = false

                    post_authorName.text = post!!.authorName
                    post_body.text = post.body
                    post_timestamp.text = TimeUtils.getPeriod(post.timestamp!!.toDate())
                    buttonComments.visibility = View.VISIBLE

                    // Check for post author
                    if (post.uid.equals(FirebaseAuth.getInstance().currentUser?.uid)) {
                        buttonDelete.visibility = View.VISIBLE
                    } else {
                        buttonDelete.visibility = View.GONE
                    }

                    if (post.profilePicUrl != null) {
                        Glide.with(this@DiscussDetailsActivity)
                            .load(post.profilePicUrl)
                            .centerCrop()
                            .placeholder(R.drawable.ic_account_circle)
                            .into(post_profileView)
                    } else {
                        post_profileView.setImageResource(R.drawable.ic_account_circle)
                    }

                    buttonDelete.setOnClickListener {
                        BottomSheetMaterialDialog.Builder(this)
                            .setTitle("Delete?")
                            .setMessage("Are you sure want to Delete this post?")
                            .setPositiveButton(
                                "Delete"
                            ) { _, _ ->
                                showProgressDialog("Deleting...")
                                Constants.commentsRef.child(mPostId!!).removeValue()
                                mDocumentReference.delete().addOnSuccessListener {
                                    hideProgressDialog()
                                    Toast.makeText(
                                        applicationContext,
                                        "Post deleted!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    finish()
                                }
                            }
                            .setNegativeButton(
                                "No"
                            ) { dialogInterface, _ ->
                                dialogInterface?.dismiss()
                            }
                            .build()
                            .show()
                    }
                } else {
                    Toast.makeText(
                        applicationContext,
                        "Post Not Exists!",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }
            } else {
                Toast.makeText(
                    applicationContext,
                    "Post Not Exists!",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun showCommentFragment(postId: String) {
        if (mCommentFragment == null) {
            mCommentFragment = CommentBottomSheetFragment(postId)
        }
        mCommentFragment?.show(supportFragmentManager, mCommentFragment?.tag)
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
}