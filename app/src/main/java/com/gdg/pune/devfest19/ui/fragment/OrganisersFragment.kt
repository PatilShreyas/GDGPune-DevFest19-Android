package com.gdg.pune.devfest19.ui.fragment

import androidx.recyclerview.widget.RecyclerView
import com.gdg.pune.devfest19.Constants


class OrganisersFragment : AbstractRecyclerFragment() {

    private var mQuery = Constants.organisersRef

    override fun getAdapter(): RecyclerView.Adapter<*>? {
        // TODO Implement Organiser RecyclerView
        //val mAdapter = FirestoreRecyclerAdapter<Organiser, OrganiserViewHolder>
        return null
    }
}


