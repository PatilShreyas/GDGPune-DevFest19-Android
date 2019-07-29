package com.gdg.pune.devfest19.ui.fragment

import androidx.recyclerview.widget.RecyclerView
import com.gdg.pune.devfest19.Constants
import com.google.firebase.firestore.Query


class ScheduleFragment : AbstractRecyclerFragment() {

    private var mQuery = Constants.scheduleRef.orderBy("timestamp", Query.Direction.DESCENDING)

    override fun getAdapter(): RecyclerView.Adapter<*>? {
        // TODO Implement Schedule RecyclerView
        //val mAdapter = FirestoreRecyclerAdapter<Schedule, ScheduleViewHolder>
        return null
    }
}
