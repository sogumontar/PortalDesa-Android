package com.loginkt.data.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loginkt.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_penginapan.view.*

class PenginapanAdapter : RecyclerView.Adapter<PenginapanAdapter.ViewHolder>() {

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        // each data item is just a string in this case
        val tvAlamat = v.tv_alamat
        val tvHarga = v.tv_harga
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
            .load(R.drawable.penginapan)
            .into(holder.imgLogo)

        holder?.tvAlamat.text = "Siborong borong"
        holder?.tvHarga.text = "Rp 100.000 / hari"
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return 5
    }
}