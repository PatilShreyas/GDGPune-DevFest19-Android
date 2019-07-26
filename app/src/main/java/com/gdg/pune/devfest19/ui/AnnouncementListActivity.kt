package com.gdg.pune.devfest19.ui

import android.os.Bundle
import android.view.MenuItem
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.gdg.pune.devfest19.Constants
import com.gdg.pune.devfest19.R
import com.gdg.pune.devfest19.model.Announcement
import com.gdg.pune.devfest19.viewholder.AnnouncementViewHolder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_alerts_list.*

class AnnouncementListActivity : AppCompatActivity() {
    private lateinit var mAdapter: FirestoreRecyclerAdapter<Announcement, AnnouncementViewHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alerts_list)

        // Init Toolbar
        toolbar.navigationIcon = resources.getDrawable(R.drawable.ic_left_arrow)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //Set Refreshing
        swipeRefreshLayout.isRefreshing = true

        // Init Layout Manager
        val mLayoutManager = LinearLayoutManager(this)
        alertRecyclerView.layoutManager = mLayoutManager
        alertRecyclerView.setHasFixedSize(true)

        val options = FirestoreRecyclerOptions.Builder<Announcement>()
            .setQuery(
                Constants.announcementsRef.orderBy("timestamp", Query.Direction.DESCENDING),
                Announcement::class.java
            )
            .build()

        mAdapter = object : FirestoreRecyclerAdapter<Announcement, AnnouncementViewHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnnouncementViewHolder {
                val view = layoutInflater.inflate(R.layout.item_alert, parent, false)
                return AnnouncementViewHolder(view)
            }

            override fun onBindViewHolder(viewHolder: AnnouncementViewHolder, position: Int, model: Announcement) {
                //Bind ViewHolder
                viewHolder.bind(model)
            }

            override fun onDataChanged() {
                super.onDataChanged()
                swipeRefreshLayout.isRefreshing = false
                swipeRefreshLayout.isEnabled = false
            }

            override fun onError(e: FirebaseFirestoreException) {
                super.onError(e)
                swipeRefreshLayout.isRefreshing = false
                swipeRefreshLayout.isEnabled = false
            }
        }
        alertRecyclerView.adapter = mAdapter

        mAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                alertRecyclerView.smoothScrollToPosition(0)
            }
        })

    }

    override fun onStart() {
        super.onStart()
        mAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        mAdapter.stopListening()
    }

    private fun getUid(): String = FirebaseAuth.getInstance().currentUser?.uid!!

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
