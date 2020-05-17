package com.PortalDesa.data.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.PortalDesa.R
import com.PortalDesa.data.model.response.PesananResponse
import kotlinx.android.synthetic.main.item_pesanan.view.*

/**
 * Created by Sogumontar Hendra Simangunsong on 17/05/2020.
 */

class PesananAdapter(val context: Context, val list : List<PesananResponse>) : RecyclerView.Adapter<PesananAdapter.ViewHolder>() {

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        // each data item is just a string in this case
        val alamat = v.pesanan_alamat
        val metode = v.pesanan_metode
        val harga = v.penginapan_harga
        val btn_delete = v.btn_pesanan_del
        val btn_bayar = v.btn_bayar
    }


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PesananAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pesanan, parent, false)
        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.alamat.text = list.get(position).alamat
        holder.harga.text = list.get(position).harga
        holder.metode.text = list.get(position).metode
        holder.btn_delete.setOnClickListener(View.OnClickListener {

        })
        holder.btn_bayar.setOnClickListener(View.OnClickListener {

        })
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return list.size
    }
}