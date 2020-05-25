package com.PortalDesa.data.ui.main.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.PortalDesa.R
import com.PortalDesa.data.apiService.APIServiceGenerator
import com.PortalDesa.data.model.response.DefaultResponse
import com.PortalDesa.data.model.response.PesananResponse
import com.PortalDesa.data.support.Connectivity
import com.PortalDesa.data.support.Flag
import com.PortalDesa.data.ui.main.activity.BayarPesananActivity
import com.PortalDesa.data.ui.main.activity.PesananActivity
import kotlinx.android.synthetic.main.item_pesanan.view.*
import retrofit2.Response

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
        holder.harga.text = list.get(position).harga.toString()
        holder.metode.text = list.get(position).metode
        holder.btn_delete.setOnClickListener(View.OnClickListener {
            hapus(list.get(position).id!!)
        })
        holder.btn_bayar.setOnClickListener(View.OnClickListener {
            val intent = Intent(context, BayarPesananActivity::class.java)
            intent.putExtra(Flag.ID_PESANAN, list.get(position).id)
            context.startActivity(intent)
        })
    }

    fun reload(){
        val intent = Intent(context, PesananActivity::class.java)
        context.startActivity(intent)
    }
    fun hapus(sku:String){
        if (Connectivity().isNetworkAvailable(context)) {
            val client = APIServiceGenerator().createService
            val call = client.cancelPesanan(sku)
            call.enqueue(object : retrofit2.Callback<DefaultResponse> {
                override fun onResponse(
                    call: retrofit2.Call<DefaultResponse>,
                    response: Response<DefaultResponse>
                ) {
                    Toast.makeText(context,"Cancel pesanan sukses", Toast.LENGTH_LONG).show()
                    reload()
                }
                override fun onFailure(call: retrofit2.Call<DefaultResponse>, t: Throwable) {

                }
            })

        }
    }
    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return list.size
    }
}