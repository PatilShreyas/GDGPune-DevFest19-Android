package com.gdg.pune.devfest19.ui.discuss.fragment

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.view.ContextThemeWrapper
import com.gdg.pune.devfest19.Constants
import com.gdg.pune.devfest19.R
import com.gdg.pune.devfest19.model.Post
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth

class AddBottomSheetFragment(onPostAdded: OnPostAdded) : BottomSheetDialogFragment() {
    private val mAuth = FirebaseAuth.getInstance().currentUser

    private lateinit var mPostEditText: EditText
    private lateinit var mPostButton: Button
    private lateinit var mCancelButton: Button
    private val mListener: OnPostAdded = onPostAdded


    val progressDialog by lazy {
        ProgressDialog(activity)
    }

    fun showProgressDialog(message: String? = resources.getString(R.string.loading)) {
        progressDialog.setMessage(message)
        progressDialog.isIndeterminate = true
        progressDialog.show()
    }

    fun hideProgressDialog() {
        if (progressDialog.isShowing) {
            progressDialog.dismiss()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val contextThemeWrapper = ContextThemeWrapper(activity, R.style.AppTheme)
        val view =
            inflater.cloneInContext(contextThemeWrapper).inflate(R.layout.layout_add_post_fragment, container, false)

        mPostButton = view.findViewById(R.id.buttonPost)
        mPostEditText = view.findViewById(R.id.editTextWritePost)
        mCancelButton = view.findViewById(R.id.buttonCancel)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialog.window?.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        mPostButton.setOnClickListener {
            if (mAuth != null) {
                showProgressDialog("Posting...")
                if (mPostEditText.text.toString().isNotBlank()) {
                    val post =
                        Post(
                            mAuth.uid,
                            mAuth.displayName,
                            mPostEditText.text.toString().trim(),
                            mAuth.photoUrl.toString()
                        )
                    Constants.discussRef.document().set(post).addOnSuccessListener {
                        Toast.makeText(
                            activity,
                            "Posted!",
                            Toast.LENGTH_SHORT
                        ).show()
                        mListener.onAdded()
                        this@AddBottomSheetFragment.dismiss()
                    }
                } else {
                    hideProgressDialog()
                    Toast.makeText(
                        activity,
                        "Post Field is Mandatory!",
                        Toast.LENGTH_SHORT
                    ).show()
                    mPostEditText.requestFocus()
                }
            }
        }

        mCancelButton.setOnClickListener {
            this@AddBottomSheetFragment.dismiss()
        }
    }

    override fun onStop() {
        super.onStop()
        hideProgressDialog()
    }

    interface OnPostAdded {
        fun onAdded()
    }
}
