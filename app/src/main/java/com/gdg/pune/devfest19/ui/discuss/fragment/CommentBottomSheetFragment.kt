package com.gdg.pune.devfest19.ui.discuss.fragment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.view.ContextThemeWrapper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.gdg.pune.devfest19.Constants
import com.gdg.pune.devfest19.R
import com.gdg.pune.devfest19.model.Comment
import com.gdg.pune.devfest19.viewholder.CommentViewHolder
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth

class CommentBottomSheetFragment(var postId: String) : BottomSheetDialogFragment() {
    private val mFirebaseUser = FirebaseAuth.getInstance().currentUser

    private lateinit var mCommentImageButton: MaterialButton
    private lateinit var mCommentEditText: EditText
    private lateinit var mCommentButton: FloatingActionButton
    private lateinit var mRecycler: RecyclerView
    private lateinit var mAdapter: FirebaseRecyclerAdapter<Comment, CommentViewHolder>
    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val contextThemeWrapper = ContextThemeWrapper(activity, R.style.AppTheme)
        val view = inflater.cloneInContext(contextThemeWrapper).inflate(R.layout.fragment_comments, container, false)

        mCommentImageButton = view.findViewById(R.id.buttonComments)
        mCommentEditText = view.findViewById(R.id.editTextComment)
        mCommentButton = view.findViewById(R.id.buttonCommentSend)
        mRecycler = view.findViewById(R.id.recyclerComments)
        mSwipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE or WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        //dialog.window?.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

        // Init RecyclerView
        mRecycler.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.reverseLayout = true
        layoutManager.stackFromEnd = true
        mRecycler.layoutManager = layoutManager

        // Show Refreshing
        mSwipeRefreshLayout.isRefreshing = true

        mCommentButton.setOnClickListener {
            mCommentButton.isEnabled = false
            if (mFirebaseUser == null) {
                Toast.makeText(
                    activity,
                    "Unauthorized User",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                if (mCommentEditText.text.toString().isNotBlank()) {
                    val comment = Comment(
                        mFirebaseUser.uid,
                        mFirebaseUser.displayName,
                        mCommentEditText.text.toString().trim(),
                        mFirebaseUser.photoUrl.toString()
                    )

                    Constants.commentsRef.child(postId).push().setValue(comment).addOnSuccessListener {
                        mCommentEditText.setText("")
                        mCommentButton.isEnabled = true
                    }.addOnFailureListener {
                        Toast.makeText(
                            activity,
                            "Error Occurred!",
                            Toast.LENGTH_SHORT
                        ).show()
                        mCommentButton.isEnabled = true
                    }
                } else {
                    mCommentEditText.error = "Field is Mandatory!"
                    mCommentButton.isEnabled = true
                }
            }
        }

        mCommentImageButton.setOnClickListener {
            dismiss()
        }

        initComments()
    }

    private fun initComments() {
        val options = FirebaseRecyclerOptions.Builder<Comment>()
            .setQuery(Constants.commentsRef.child(postId), Comment::class.java)
            .build()

        mAdapter = object : FirebaseRecyclerAdapter<Comment, CommentViewHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
                val view = layoutInflater.inflate(R.layout.item_discuss_comment, parent, false)
                return CommentViewHolder(view)
            }

            override fun onBindViewHolder(viewHolder: CommentViewHolder, position: Int, comment: Comment) {
                viewHolder.bind(comment)
            }

            override fun onDataChanged() {
                super.onDataChanged()
                mSwipeRefreshLayout.isRefreshing = false
                mSwipeRefreshLayout.isEnabled = false
            }
        }

        mAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                mRecycler.smoothScrollToPosition(positionStart)
            }
        })

        mRecycler.adapter = mAdapter
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)

        dialog.setOnShowListener {
            val bottomSheetDialog: BottomSheetDialog = dialog as BottomSheetDialog
            val bottomSheet =
                bottomSheetDialog.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
            BottomSheetBehavior.from(bottomSheet).state = BottomSheetBehavior.STATE_EXPANDED
        }

        return dialog
    }

    override fun onStart() {
        super.onStart()
        if (mAdapter != null) {
            mAdapter.startListening()
        }
    }

    override fun onStop() {
        super.onStop()
        if (mAdapter != null) {
            mAdapter.stopListening()
        }
    }

}
