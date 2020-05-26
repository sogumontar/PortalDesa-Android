package com.PortalDesa.data.ui.main.adapter.admin

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.PortalDesa.R
import com.PortalDesa.data.model.response.TransaksiPenginapanResponse
import com.PortalDesa.data.support.Preferences
import kotlinx.android.synthetic.main.item_pesanan_penginapan.view.*

/**
 * Created by Sogumontar Hendra Simangunsong on 26/05/2020.
 */
class DaftarPesananPenginapanSudahDibayarAdapter(val context: Context, val listPesanan : List<TransaksiPenginapanResponse>) :
    RecyclerView.Adapter<DaftarPesananPenginapanSudahDibayarAdapter.ViewHolder>() {

    lateinit private var preferences: Preferences

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        // each data item is just a string in this case

        val alamat = v.pesanan_alamat
        val metode = v.pesanan_metode
        val harga = v.penginapan_harga
        val btnBayar = v.btn_bayar
        val btnDelete = v.btn_pesanan_del

    }


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DaftarPesananPenginapanSudahDibayarAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pesanan_penginapan, parent, false)
        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

//
        holder?.btnBayar.visibility=View.GONE
        holder?.alamat.text = listPesanan.get(position).lamaMenginap.toString()
        holder?.metode.text = listPesanan.get(position).metode
        holder?.harga.text = listPesanan.get(position).harga.toString()
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return listPesanan.size
    }

}