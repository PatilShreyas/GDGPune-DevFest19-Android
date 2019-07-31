package com.gdg.pune.devfest19.ui.fragment

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.gdg.pune.devfest19.Constants
import com.gdg.pune.devfest19.R
import com.gdg.pune.devfest19.model.Organiser
import com.gdg.pune.devfest19.viewholder.OrganiserViewHolder


class OrganisersFragment : AbstractRecyclerFragment() {

    private var mQuery = Constants.organisersRef

    override fun getAdapter(): RecyclerView.Adapter<*>? {

        val options = FirestoreRecyclerOptions.Builder<Organiser>()
            .setLifecycleOwner(this)
            .setQuery(mQuery, Organiser::class.java)
            .build()

        return object : FirestoreRecyclerAdapter<Organiser, OrganiserViewHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = OrganiserViewHolder(
                layoutInflater.inflate(R.layout.item_organiser, parent, false)
            )

            override fun onBindViewHolder(viewHolder: OrganiserViewHolder, position: Int, model: Organiser) {
                viewHolder.bind(model, null)
            }

            override fun onDataChanged() {
                hideSwipeRefreshLayout()
            }

        }
    }
}


