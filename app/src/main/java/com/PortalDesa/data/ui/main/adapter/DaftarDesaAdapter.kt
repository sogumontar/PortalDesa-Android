package com.PortalDesa.data.ui.main.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.PortalDesa.R
import com.PortalDesa.data.model.response.ListDesaKecamatanResponse
import com.PortalDesa.data.support.Flag
import com.PortalDesa.data.ui.main.activity.DetailDesaActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_penginapan.view.*

/**
 * Created by Sogumontar Hendra Simangunsong on 26/05/2020.
 */

class DaftarDesaAdapter(val context: Context,val listDesa : List<ListDesaKecamatanResponse>) : RecyclerView.Adapter<DaftarDesaAdapter.ViewHolder>() {

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        // each data item is just a string in this case
        val tvDesa = v.penginapan_item_nama
        val btn_update = v.penginapan_btn_update
        val btn_delete = v.penginapan_btn_hapus
        val tvKecamatan = v.tv_kecamatan
        val imgLogo = v.img_penginapan
        val lin = v.item_layout
    }


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DaftarDesaAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_penginapan, parent, false)
        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Picasso.get()
            .load("https://portal-desa.herokuapp.com/desa/get/"+listDesa.get(position).gambar)
            .into(holder.imgLogo)
        holder.btn_delete.visibility=View.GONE
        holder.btn_update.visibility=View.GONE
        holder.tvDesa.text = listDesa.get(position).nama
        holder.tvKecamatan.text = listDesa.get(position).kecamatan
        val ss=listDesa.get(position)!!.skuAdmin!!
        holder.lin.setOnClickListener{goToDetailDesa(listDesa.get(position)!!.nama!!) }
    }

    fun goToDetailDesa(id:String){
        val intent = Intent(context, DetailDesaActivity::class.java)
        intent.putExtra(Flag.NAMA_DESA,id )
        context.startActivity(intent)
    }
    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return listDesa.size
    }
}