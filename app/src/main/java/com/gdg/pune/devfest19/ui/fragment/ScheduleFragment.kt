package com.gdg.pune.devfest19.ui.fragment

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.gdg.pune.devfest19.Constants
import com.gdg.pune.devfest19.R
import com.gdg.pune.devfest19.model.Schedule
import com.gdg.pune.devfest19.viewholder.ScheduleViewHolder
import com.google.firebase.firestore.Query


class ScheduleFragment : AbstractRecyclerFragment() {

    private var mQuery = Constants.scheduleRef.orderBy("timestamp", Query.Direction.DESCENDING)

    override fun getAdapter(): RecyclerView.Adapter<*>? {

        val options = FirestoreRecyclerOptions.Builder<Schedule>()
            .setLifecycleOwner(this)
            .setQuery(mQuery, Schedule::class.java)
            .build()

        return object : FirestoreRecyclerAdapter<Schedule, ScheduleViewHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ScheduleViewHolder(
                layoutInflater.inflate(R.layout.item_schedule, parent, false)
            )

            override fun onBindViewHolder(viewHolder: ScheduleViewHolder, position: Int, model: Schedule) {
                viewHolder.bind(model, null)
            }

            override fun onDataChanged() {
                hideSwipeRefreshLayout()
            }

        }
    }
}
