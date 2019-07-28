package com.gdg.pune.devfest19.ui.discuss.fragment

import android.os.Bundle
import com.gdg.pune.devfest19.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Query

class MyPostsFragment : PostListFragment() {
    override fun getQuery(): Query {
        return Constants.discussRef.whereEqualTo("uid", FirebaseAuth.getInstance().currentUser?.uid.toString())
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.title = "My Posts"
    }
}