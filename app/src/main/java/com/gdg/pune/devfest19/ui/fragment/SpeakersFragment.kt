package com.gdg.pune.devfest19.ui.fragment

import androidx.recyclerview.widget.RecyclerView
import com.gdg.pune.devfest19.Constants


class SpeakersFragment : AbstractRecyclerFragment() {

    private var mQuery = Constants.speakersRef

    override fun getAdapter(): RecyclerView.Adapter<*>? {
        // TODO Implement Speakers RecyclerView
        //val mAdapter = FirestoreRecyclerAdapter<Speaker, SpeakerViewHolder>
        return null
    }
}

