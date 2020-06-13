package com.PortalDesa.data.ui.main.adapter.admin

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
import com.PortalDesa.data.support.Preferences
import com.PortalDesa.data.support.Utils
import com.PortalDesa.data.ui.main.activity.PesananActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_pesanan_penginapan.view.*
import retrofit2.Response

/**
 * Created by Sogumontar Hendra Simangunsong on 26/05/2020.
 */
class DaftarPesananPenginapanSudahDibayarAdapter(val context: Context, val listPesanan : List<TransaksiPenginapanResponse>) :
    RecyclerView.Adapter<DaftarPesananPenginapanSudahDibayarAdapter.ViewHolder>() {

    lateinit private var preferences: Preferences

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        // each data item is just a string in this case

        val alamat = v.pesanan_alamat
        val img_icons = v.img_icon
        val penginapan = v.penginapan_alamat
        val lama = v.tv_lama_menginap
        val metode = v.pesanan_metode
        val harga = v.penginapan_harga
        val btnBayar = v.btn_bayar
        val btnDelete = v.btn_pesanan_del
        val btnTolak= v.btn_tolak
        val btnTerima= v.btn_terima
        val s_menunggu= v.status_menunggu
        val s_diterima= v.status_diterima
        val s_ditolak= v.status_ditolak
        val admin= v.lin_admin
        val checkin= v.tv_tgl_checkin

    }


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DaftarPesananPenginapanSudahDibayarAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pesanan_penginapan, parent, false)
        preferences = Preferences(context)
        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

//
        if (Connectivity().isNetworkAvailable(context)) {
            val client = APIServiceGenerator().createService
            val call = client.lihatPenginapanBySku(listPesanan.get(position).skuProduk!!)
            call.enqueue(object : retrofit2.Callback<PenginapanResponse> {
                override fun onResponse(
                    call: retrofit2.Call<PenginapanResponse>,
                    response: Response<PenginapanResponse>
                ) {
                    val listPenginapan = response.body()
                    Picasso.get()
                        .load("https://portal-desa.herokuapp.com" + listPenginapan?.gambar)
                        .into(holder.img_icons)
                    holder.alamat.setText(listPenginapan?.lokasi)
                    holder.penginapan.setText(listPenginapan?.nama)
                }

                override fun onFailure(call: retrofit2.Call<PenginapanResponse>, t: Throwable) {
                    Log.i(this.javaClass.simpleName, " Requested API : " + call.request().body()!!)
                    Log.e(this.javaClass.simpleName, " Exceptions : $t")
                }
            })

        }

        if(preferences.getRoles().equals("ROLE_ADMIN")){
            holder.btnDelete.visibility=View.GONE

            if(listPesanan.get(position).status == 2){
                holder.btnTerima.setOnClickListener{
                    terima(listPesanan.get(position).id!!)
                }
                holder.btnTolak.setOnClickListener{
                    tolak(listPesanan.get(position).id!!)
                }

                holder.admin.visibility=View.VISIBLE
                holder.s_menunggu.visibility=View.VISIBLE
            }else if(listPesanan.get(position).status == 3){
                holder.s_diterima.visibility=View.VISIBLE
            }else{

                holder.s_ditolak.visibility=View.VISIBLE
            }
        }
        holder.btnBayar.visibility=View.GONE
        holder.lama.text = listPesanan.get(position).lamaMenginap.toString()
        holder.metode.text = listPesanan.get(position).metode
        holder.harga.text = Utils().numberToIDR(listPesanan.get(position).harga!!.toInt(),true)
        holder.checkin.text = listPesanan.get(position).checkin
    }

    fun terima(id:String){
        if (Connectivity().isNetworkAvailable(context)) {
            val client = APIServiceGenerator().createService
            val call = client.terimaPenginapanPesanan(id)
            call.enqueue(object : retrofit2.Callback<DefaultResponse> {
                override fun onResponse(
                    call: retrofit2.Call<DefaultResponse>,
                    response: Response<DefaultResponse>
                ) {
                    Toast.makeText(context,"Terima Pesanan Sukses", Toast.LENGTH_LONG).show()
                    reload()
                }
                override fun onFailure(call: retrofit2.Call<DefaultResponse>, t: Throwable) {

                }
            })

        }
    }


    fun tolak(id:String){
        if (Connectivity().isNetworkAvailable(context)) {
            val client = APIServiceGenerator().createService
            val call = client.tolakPenginapanPesanan(id)
            call.enqueue(object : retrofit2.Callback<DefaultResponse> {
                override fun onResponse(
                    call: retrofit2.Call<DefaultResponse>,
                    response: Response<DefaultResponse>
                ) {
                    Toast.makeText(context,"Tolak Pesanan Sukses", Toast.LENGTH_LONG).show()
                    reload()
                }
                override fun onFailure(call: retrofit2.Call<DefaultResponse>, t: Throwable) {

                }
            })

        }
    }
    fun reload(){
        val intents = Intent(context, PesananActivity::class.java)
        context.startActivity(intents)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return listPesanan.size
    }

}