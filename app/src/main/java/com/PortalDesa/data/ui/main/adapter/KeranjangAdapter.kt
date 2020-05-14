package com.PortalDesa.data.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.PortalDesa.R
import kotlinx.android.synthetic.main.item_keranjang.view.*

/**
 * Created by Sogumontar Hendra Simangunsong on 14/05/2020.
 */
class KeranjangAdapter(val context : Context) : RecyclerView.Adapter<KeranjangAdapter.ViewHolder>() {
    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        // each data item is just a string in this case
        val tvDesc = v.btn_del

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_keranjang, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 3
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.tvDesc.text = "delete"
    }

}
