package com.PortalDesa.data.ui.main.adapter.admin

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.PortalDesa.R
import com.PortalDesa.data.model.response.PesananResponse
import com.PortalDesa.data.support.Preferences
import com.PortalDesa.data.ui.main.activity.PesananActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_pesanan.view.*

/**
 * Created by Sogumontar Hendra Simangunsong on 20/05/2020.
 */
class DaftarPesananSudahDibayarADapter(val context: Context, val listPesanan : List<PesananResponse>) :
    RecyclerView.Adapter<DaftarPesananSudahDibayarADapter.ViewHolder>() {

    lateinit private var preferences: Preferences

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        // each data item is just a string in this case

        val alamat = v.pesanan_alamat
        val metode = v.pesanan_metode
        val harga = v.penginapan_harga
        val btnBlocked = v.btn_bayar
        val btnActivated = v.btn_pesanan_del
        val image = v.imageview
        val lin =v.linDaftar

    }


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DaftarPesananSudahDibayarADapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pesanan, parent, false)
        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            holder?.btnBlocked.visibility=View.GONE
            holder?.btnActivated.visibility=View.GONE
            holder?.lin.visibility=View.VISIBLE
            Picasso.get()
                .load("https://portal-desa.herokuapp.com/transaksi/get/"+listPesanan.get(position).resi)
                .into(holder.image)
            holder?.image

        holder?.alamat.text = listPesanan.get(position).alamat
        holder?.metode.text = listPesanan.get(position).metode
        holder?.harga.text = listPesanan.get(position).harga
    }

    fun reload(){
        val intent = Intent(context, PesananActivity::class.java)
        context.startActivity(intent)
    }

    override fun getItemCount(): Int {
        return listPesanan.size
    }

}