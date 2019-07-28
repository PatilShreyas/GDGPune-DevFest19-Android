package com.gdg.pune.devfest19.ui.discuss.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.firebase.ui.firestore.paging.FirestorePagingAdapter
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.firebase.ui.firestore.paging.LoadingState
import com.gdg.pune.devfest19.R
import com.gdg.pune.devfest19.model.Post
import com.gdg.pune.devfest19.ui.discuss.DiscussDetailsActivity
import com.gdg.pune.devfest19.viewholder.PostViewHolder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.Query

abstract class PostListFragment : Fragment() {

    private lateinit var fabNewPost: FloatingActionButton
    private lateinit var mAdapter: FirestorePagingAdapter<Post, PostViewHolder>
    private lateinit var mRecycler: RecyclerView
    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout
    private lateinit var mNoPostsText: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_posts, container, false)
        mRecycler = view.findViewById(R.id.postsRecyclerView)
        fabNewPost = view.findViewById(R.id.fabNewPost)
        mSwipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)
        mNoPostsText = view.findViewById(R.id.noPostsTextView)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mRecycler.setHasFixedSize(true)
        mRecycler.layoutManager = LinearLayoutManager(activity)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPrefetchDistance(2)
            .setPageSize(10)
            .build()

        val options = FirestorePagingOptions.Builder<Post>()
            .setLifecycleOwner(this)
            .setQuery(getQuery(), config, Post::class.java)
            .build()

        mAdapter = object : FirestorePagingAdapter<Post, PostViewHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
                val view = layoutInflater.inflate(R.layout.item_discuss_post, parent, false)
                return PostViewHolder(view)
            }

            override fun onBindViewHolder(viewHolder: PostViewHolder, position: Int, post: Post) {

                viewHolder.bind(post, View.OnClickListener {
                    val intent = Intent(activity, DiscussDetailsActivity::class.java)
                    intent.putExtra(DiscussDetailsActivity.KEY_DISCUSS_ID, getItem(position)?.reference?.id)
                    intent.putExtra(DiscussDetailsActivity.KEY_DISCUSS_PATH, getItem(position)?.reference?.path)
                    startActivity(intent)
                })
            }

            override fun onLoadingStateChanged(state: LoadingState) {
                when (state) {
                    LoadingState.LOADING_INITIAL -> {
                        mSwipeRefreshLayout.isRefreshing = true
                    }

                    LoadingState.LOADING_MORE -> {
                        mSwipeRefreshLayout.isRefreshing = true
                    }

                    LoadingState.LOADED -> {
                        mSwipeRefreshLayout.isRefreshing = false
                        updateUi()
                    }

                    LoadingState.ERROR -> {
                        Toast.makeText(
                            activity,
                            "Error Occurred!",
                            Toast.LENGTH_SHORT
                        ).show()
                        mSwipeRefreshLayout.isRefreshing = false
                        updateUi()
                    }

                    LoadingState.FINISHED -> {
                        mSwipeRefreshLayout.isRefreshing = false
                        updateUi()
                    }
                }
            }

        }
        mRecycler.adapter = mAdapter

        fabNewPost.setOnClickListener {
            val addFragment = AddBottomSheetFragment(object :
                AddBottomSheetFragment.OnPostAdded {
                override fun onAdded() {
                    mAdapter.refresh()
                }
            })
            addFragment.isCancelable = false
            addFragment.show(fragmentManager, "Post")
        }

        mSwipeRefreshLayout.setOnRefreshListener {
            mAdapter.refresh()
        }
    }

    private fun updateUi() {
        if (mAdapter.itemCount == 0) {
            mNoPostsText.visibility = View.VISIBLE
        } else {
            mNoPostsText.visibility = View.GONE
        }
    }

    protected abstract fun getQuery(): Query
}