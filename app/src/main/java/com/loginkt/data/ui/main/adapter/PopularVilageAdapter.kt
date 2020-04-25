package com.loginkt.data.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loginkt.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_popular_vilage.view.*

class PopularVilageAdapter : RecyclerView.Adapter<PopularVilageAdapter.ViewHolder>() {

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        // each data item is just a string in this case
        val tvDesc = v.tv_desc
        val imgPopular = v.img_icon
    }


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PopularVilageAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_popular_vilage, parent, false)
        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Picasso.get()
            .load(R.drawable.balige)
            .into(holder.imgPopular)

        holder?.tvDesc.text = "Detail "+position
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return 5
    }
}