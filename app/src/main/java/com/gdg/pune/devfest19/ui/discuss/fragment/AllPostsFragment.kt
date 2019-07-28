package com.gdg.pune.devfest19.ui.discuss.fragment

import android.os.Bundle
import com.gdg.pune.devfest19.Constants
import com.google.firebase.firestore.Query

class AllPostsFragment : PostListFragment() {
    override fun getQuery(): Query {
        return Constants.discussRef.orderBy("timestamp", Query.Direction.DESCENDING)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.title = "Posts"
    }
}