package com.loginkt.data.ui.main.adapter.admin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loginkt.R
import com.loginkt.data.model.response.DaftarAkunResponse
import kotlinx.android.synthetic.main.item_daftar_akun.view.*

/**
 * Created by Sogumontar Hendra Simangunsong on 08/05/2020.
 */
class DaftarAkunAdapter(val listAkun : List<DaftarAkunResponse>) : RecyclerView.Adapter<DaftarAkunAdapter.ViewHolder>() {

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        // each data item is just a string in this case
        val tvDesc = v.tv_nama_akun
        val tvAlamat = v.tv_alamat
        val tvEmail = v.tv_email

    }


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DaftarAkunAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_daftar_akun, parent, false)
        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        holder?.tvDesc.text = listAkun.get(position).name
        holder?.tvAlamat.text = listAkun.get(position).alamat
        holder?.tvEmail.text = listAkun.get(position).email
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return listAkun.size
    }
}