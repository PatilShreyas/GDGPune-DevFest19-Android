package com.gdg.pune.devfest19.ui.fragment

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.gdg.pune.devfest19.Constants
import com.gdg.pune.devfest19.R
import com.gdg.pune.devfest19.model.Developer
import com.gdg.pune.devfest19.viewholder.DeveloperViewHolder
import com.google.firebase.firestore.Query


class DevelopersFragment : AbstractRecyclerFragment() {

    private var mQuery = Constants.developersRef.orderBy("no", Query.Direction.ASCENDING)

    override fun getAdapter(): RecyclerView.Adapter<*>? {

        val options = FirestoreRecyclerOptions.Builder<Developer>()
            .setLifecycleOwner(this)
            .setQuery(mQuery, Developer::class.java)
            .build()

        return object : FirestoreRecyclerAdapter<Developer, DeveloperViewHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DeveloperViewHolder(
                layoutInflater.inflate(R.layout.item_developer, parent, false)
            )

            override fun onBindViewHolder(viewHolder: DeveloperViewHolder, position: Int, model: Developer) {
                viewHolder.bind(model, activity!!.applicationContext)
            }

            override fun onDataChanged() {
                hideSwipeRefreshLayout()
            }

        }
    }
}


