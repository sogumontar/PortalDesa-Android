package com.PortalDesa.data.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.PortalDesa.R
import com.PortalDesa.data.model.response.ListDesaKecamatanResponse
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_penginapan.view.*

class PenginapanAdapter(val listDesa : List<ListDesaKecamatanResponse>) : RecyclerView.Adapter<PenginapanAdapter.ViewHolder>() {

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        // each data item is just a string in this case
        val tvDesa = v.tv_desa
        val tvKecamatan = v.tv_kecamatan
        val imgLogo = v.img_logo
    }


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PenginapanAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_penginapan, parent, false)
        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Picasso.get()
            .load("https://portal-desa.herokuapp.com/desa/get/PINT-0001.png")
            .into(holder.imgLogo)

        holder.tvDesa.text = listDesa.get(position).nama
        holder.tvKecamatan.text = listDesa.get(position).kecamatan
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return listDesa.size
    }
}