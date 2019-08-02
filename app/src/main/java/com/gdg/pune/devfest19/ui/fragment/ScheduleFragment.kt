package com.gdg.pune.devfest19.ui.fragment

import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.gdg.pune.devfest19.Constants
import com.gdg.pune.devfest19.R
import com.gdg.pune.devfest19.model.Session
import com.gdg.pune.devfest19.ui.SessionDetailsActivity
import com.gdg.pune.devfest19.viewholder.ScheduleViewHolder
import com.google.firebase.firestore.Query


class ScheduleFragment : AbstractRecyclerFragment() {

    private var mQuery = Constants.scheduleRef.orderBy("timestamp", Query.Direction.DESCENDING)

    override fun getAdapter(): RecyclerView.Adapter<*>? {

        val options = FirestoreRecyclerOptions.Builder<Session>()
            .setLifecycleOwner(this)
            .setQuery(mQuery, Session::class.java)
            .build()

        return object : FirestoreRecyclerAdapter<Session, ScheduleViewHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ScheduleViewHolder(
                layoutInflater.inflate(R.layout.item_schedule, parent, false)
            )

            override fun onBindViewHolder(viewHolder: ScheduleViewHolder, position: Int, model: Session) {
                // Get reference
                val ref = snapshots.getSnapshot(position).reference

                val intent = Intent(activity, SessionDetailsActivity::class.java)
                intent.putExtra(SessionDetailsActivity.SESSION_KEY, ref.id)

                viewHolder.bind(model, View.OnClickListener {
                    startActivity(intent)
                })
            }

            override fun onDataChanged() {
                hideSwipeRefreshLayout()
            }

        }
    }
}
