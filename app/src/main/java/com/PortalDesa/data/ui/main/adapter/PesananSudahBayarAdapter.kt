package com.PortalDesa.data.ui.main.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.PortalDesa.R
import com.PortalDesa.data.model.response.PesananResponse
import com.PortalDesa.data.support.Flag
import com.PortalDesa.data.ui.main.activity.BayarPesananActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_pesanan_sudah_bayar.view.*

/**
 * Created by Sogumontar Hendra Simangunsong on 23/05/2020.
 */

class PesananSudahBayarAdapter(val context: Context, val list : List<PesananResponse>) : RecyclerView.Adapter<PesananSudahBayarAdapter.ViewHolder>() {

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        // each data item is just a string in this case
        val alamat = v.pesanan_alamat
        val metode = v.pesanan_metode
        val harga = v.penginapan_harga
        val image =v.imageview
        val ubah =v.btn_ubah
        val s_menunggu= v.status_menunggu
        val s_diterima= v.status_diterima
        val s_ditolak= v.status_ditolak
    }


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PesananSudahBayarAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pesanan_sudah_bayar, parent, false)
        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Picasso.get()
            .load("https://portal-desa.herokuapp.com/transaksi/get/"+list.get(position).resi)
            .into(holder.image)
        holder?.image
        holder.alamat.text = list.get(position).resi
        holder.ubah.setOnClickListener{
            val intent = Intent(context, BayarPesananActivity::class.java)
            intent.putExtra(Flag.ID_PESANAN, list.get(position).id)
            context.startActivity(intent)
        }
        if(list.get(position).status == 5){
            holder.ubah.visibility=View.GONE
            holder.s_ditolak.visibility=View.VISIBLE
        }else if(list.get(position).status == 4){
            holder.ubah.visibility=View.GONE
            holder.s_diterima.visibility=View.VISIBLE
        }else{
            holder.s_menunggu.visibility=View.VISIBLE
        }
        holder.harga.text = list.get(position).harga.toString()
        holder.metode.text = list.get(position).metode
    }


    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return list.size
    }
}