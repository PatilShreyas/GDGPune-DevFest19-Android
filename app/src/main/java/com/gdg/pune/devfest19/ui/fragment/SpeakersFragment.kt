package com.gdg.pune.devfest19.ui.fragment

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.gdg.pune.devfest19.Constants
import com.gdg.pune.devfest19.R
import com.gdg.pune.devfest19.model.Speaker
import com.gdg.pune.devfest19.viewholder.SpeakerViewHolder


class SpeakersFragment : AbstractRecyclerFragment() {

    private var mQuery = Constants.speakersRef

    override fun getAdapter(): RecyclerView.Adapter<*>? {

        val options = FirestoreRecyclerOptions.Builder<Speaker>()
            .setLifecycleOwner(this)
            .setQuery(mQuery, Speaker::class.java)
            .build()

        return object : FirestoreRecyclerAdapter<Speaker, SpeakerViewHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SpeakerViewHolder(
                layoutInflater.inflate(R.layout.item_speaker, parent, false)
            )

            override fun onBindViewHolder(viewHolder: SpeakerViewHolder, position: Int, model: Speaker) {
                viewHolder.bind(model, null)
            }

            override fun onDataChanged() {
                hideSwipeRefreshLayout()
            }

        }
    }
}

