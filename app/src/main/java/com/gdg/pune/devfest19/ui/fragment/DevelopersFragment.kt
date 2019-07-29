package com.gdg.pune.devfest19.ui.fragment

import androidx.recyclerview.widget.RecyclerView
import com.gdg.pune.devfest19.Constants


class DevelopersFragment : AbstractRecyclerFragment() {

    private var mQuery = Constants.developersRef

    override fun getAdapter(): RecyclerView.Adapter<*>? {
        // TODO Implement Developers RecyclerView
        //val mAdapter = FirestoreRecyclerAdapter<Developer, DeveloperViewHolder>
        return null
    }
}


