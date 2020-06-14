package com.PortalDesa.data.ui.main.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.PortalDesa.R
import com.PortalDesa.data.apiService.APIServiceGenerator
import com.PortalDesa.data.model.response.DefaultResponse
import com.PortalDesa.data.model.response.PenginapanResponse
import com.PortalDesa.data.model.response.TransaksiPenginapanResponse
import com.PortalDesa.data.support.Connectivity
import com.PortalDesa.data.support.Flag
import com.PortalDesa.data.ui.main.activity.BayarPesananActivity
import com.PortalDesa.data.ui.main.activity.BayarPesananPenginapanActivity
import com.PortalDesa.data.ui.main.activity.PesananActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_pesanan_penginapan.view.*
import retrofit2.Response

/**
 * Created by Sogumontar Hendra Simangunsong on 27/05/2020.
 */

class PenginapanSudahDibayarAdapter(val context: Context, val list : List<TransaksiPenginapanResponse>) : RecyclerView.Adapter<PenginapanSudahDibayarAdapter.ViewHolder>() {

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        // each data item is just a string in this case
        val alamat = v.pesanan_alamat
        val penginapan_alamat = v.penginapan_alamat
        val update_resi = v.btn_update
        val img = v.img_icon
        val metode = v.pesanan_metode
        val harga = v.penginapan_harga
        val lama_menginap = v.tv_lama_menginap
        val btn_delete = v.btn_pesanan_del
        val btn_bayar = v.btn_bayar
        val lin_image= v.linDaftar
        val image= v.imageview
        val s_menunggu= v.status_menunggu
        val s_diterima= v.status_diterima
        val s_ditolak= v.status_ditolak
    }


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PenginapanSudahDibayarAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pesanan_penginapan, parent, false)
        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (Connectivity().isNetworkAvailable(context)) {
            val client = APIServiceGenerator().createService
            val call = client.lihatPenginapanBySku(list.get(position).skuProduk.toString())
            call.enqueue(object : retrofit2.Callback<PenginapanResponse> {
                override fun onResponse(
                    call: retrofit2.Call<PenginapanResponse>,
                    response: Response<PenginapanResponse>
                ) {
                    val listProduk = response.body()
                    holder.penginapan_alamat.text = listProduk!!.nama
                    holder.alamat.text = listProduk.lokasi
                    Picasso.get()
                        .load("https://portal-desa.herokuapp.com" + listProduk.gambar)
                        .into(holder.img)
                }

                override fun onFailure(call: retrofit2.Call<PenginapanResponse>, t: Throwable) {
                    Log.i(this.javaClass.simpleName, " Requested API : " + call.request().body()!!)
                    Log.e(this.javaClass.simpleName, " Exceptions : $t")
                }
            })

        }
        holder.update_resi.visibility=View.VISIBLE
        holder.update_resi.setOnClickListener{
            val intent = Intent(context, BayarPesananActivity::class.java)
            intent.putExtra(Flag.ID_PESANAN, list.get(position).id)
            context.startActivity(intent)
        }
        holder.harga.text = list.get(position).harga.toString()
        holder.lama_menginap.text = list.get(position).lamaMenginap.toString() +" Hari"
        holder.metode.text = list.get(position).metode
        holder.btn_delete.visibility=View.GONE
        holder.btn_bayar.visibility=View.GONE
        holder.lin_image.visibility=View.VISIBLE
        if(list.get(position).status == 2){
            holder.s_menunggu.visibility=View.VISIBLE
        }else if(list.get(position).status == 3){
            holder.s_diterima.visibility=View.VISIBLE
        }else{
            holder.s_ditolak.visibility=View.VISIBLE
        }
        Picasso.get()
            .load("https://portal-desa.herokuapp.com/transaksi/get/" + list.get(position).resi)
            .into(holder.image)
        holder.btn_bayar.setOnClickListener(View.OnClickListener {
            val intent = Intent(context, BayarPesananPenginapanActivity::class.java)
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
            val call = client.cancelPesananPenginapan(sku)
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