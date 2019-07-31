package com.gdg.pune.devfest19.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.gdg.pune.devfest19.R


abstract class AbstractRecyclerFragment : Fragment() {

    private lateinit var mRecyclerView: RecyclerView
    protected lateinit var mSwipeRefreshLayout: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_recycler, container, false)
        mRecyclerView = view.findViewById(R.id.recycler_view)
        mSwipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
        }

        mRecyclerView.adapter = getAdapter()
    }

    protected fun hideSwipeRefreshLayout() {
        mSwipeRefreshLayout.isRefreshing = false
        mSwipeRefreshLayout.isEnabled = false
    }

    abstract fun getAdapter(): RecyclerView.Adapter<*>?

}
