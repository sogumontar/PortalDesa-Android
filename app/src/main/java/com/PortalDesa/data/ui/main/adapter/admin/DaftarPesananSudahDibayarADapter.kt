package com.PortalDesa.data.ui.main.adapter.admin

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
import com.PortalDesa.data.support.Preferences
import com.PortalDesa.data.support.Utils
import com.PortalDesa.data.ui.main.activity.PesananActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_pesanan.view.*
import retrofit2.Response

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
        val btnBayar = v.btn_bayar
        val btnHapus = v.btn_pesanan_del
        val btnBlocked = v.btn_pesanan_tolak
        val btnActivated = v.btn_terima
        val image = v.imageview
        val lin =v.linDaftar
        val s_menunggu= v.status_menunggu
        val s_diterima= v.status_diterima
        val s_ditolak= v.status_ditolak

    }


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DaftarPesananSudahDibayarADapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pesanan, parent, false)
        preferences = Preferences(context)
        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            if(preferences.getRoles().equals("ROLE_ADMIN")) {
                if(listPesanan.get(position).status == 5){
                    holder.s_ditolak.visibility=View.VISIBLE
                }else if(listPesanan.get(position).status == 4){
                    holder.s_diterima.visibility=View.VISIBLE
                }else{
                    holder.s_menunggu.visibility=View.VISIBLE
                    holder.btnBlocked.visibility=View.VISIBLE
                    holder.btnActivated.visibility=View.VISIBLE
                }
                holder.btnBayar.visibility=View.GONE
                holder.btnHapus.visibility=View.GONE

                holder?.btnBlocked.setOnClickListener {
                    tolak(listPesanan.get(position).id!!)
                }
                holder?.btnActivated.setOnClickListener {
                    terima(listPesanan.get(position).id!!)
                }
            }
            holder?.lin.visibility=View.VISIBLE
            Picasso.get()
                .load("https://portal-desa.herokuapp.com/transaksi/get/"+listPesanan.get(position).resi)
                .into(holder.image)
            holder?.image

        holder?.alamat.text = listPesanan.get(position).alamat
        holder?.metode.text = listPesanan.get(position).metode
        holder?.harga.text = Utils().numberToIDR(listPesanan.get(position).harga!!.toInt(),true)
    }
    fun tolak(id:String){
        if (Connectivity().isNetworkAvailable(context)) {
            val client = APIServiceGenerator().createService
            val call = client.tolakPesanan(id)
            call.enqueue(object : retrofit2.Callback<DefaultResponse> {
                override fun onResponse(
                    call: retrofit2.Call<DefaultResponse>,
                    response: Response<DefaultResponse>
                ) {
                    Toast.makeText(context,"Tolak pesanan sukses", Toast.LENGTH_LONG).show()
                    reload()
                }
                override fun onFailure(call: retrofit2.Call<DefaultResponse>, t: Throwable) {

                }
            })

        }
    }

    fun terima(id:String){
        if (Connectivity().isNetworkAvailable(context)) {
            val client = APIServiceGenerator().createService
            val call = client.terimaPesanan(id)
            call.enqueue(object : retrofit2.Callback<DefaultResponse> {
                override fun onResponse(
                    call: retrofit2.Call<DefaultResponse>,
                    response: Response<DefaultResponse>
                ) {
                    Toast.makeText(context,"Terima pesanan sukses", Toast.LENGTH_LONG).show()
                    reload()
                }
                override fun onFailure(call: retrofit2.Call<DefaultResponse>, t: Throwable) {

                }
            })

        }
    }

    fun reload(){
        val intent = Intent(context, PesananActivity::class.java)
        context.startActivity(intent)
    }

    override fun getItemCount(): Int {
        return listPesanan.size
    }

}