package com.gdg.pune.devfest19.ui.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gdg.pune.devfest19.R
import com.gdg.pune.devfest19.viewholder.OpenSourceViewHolder


class OpenSourceFragment : AbstractRecyclerFragment() {

    override fun getAdapter(): RecyclerView.Adapter<*>? {
        hideSwipeRefreshLayout()
        return OpenSourceAdapter
    }

    object OpenSourceAdapter : RecyclerView.Adapter<OpenSourceViewHolder>() {

        val OPENSOURCE_TITLE = intArrayOf(
            R.string.opensource_title_material,
            R.string.opensource_title_material_drawer,
            R.string.opensource_title_material_dialog,
            R.string.opensource_title_firebase,
            R.string.opensource_title_firebaseui,
            R.string.opensource_title_glide
        )

        val OPENSOURCE_DESCRIPTION = intArrayOf(
            R.string.opensource_description_material,
            R.string.opensource_description_material_drawer,
            R.string.opensource_description_material_dialog,
            R.string.opensource_description_firebase,
            R.string.opensource_description_firebaseui,
            R.string.opensource_description_glide
        )

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = OpenSourceViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_open_source, parent, false)
        )

        override fun getItemCount(): Int = OPENSOURCE_TITLE.size

        override fun onBindViewHolder(holder: OpenSourceViewHolder, position: Int) {
            holder.bind(OPENSOURCE_TITLE[position], OPENSOURCE_DESCRIPTION[position])
        }

    }
}


