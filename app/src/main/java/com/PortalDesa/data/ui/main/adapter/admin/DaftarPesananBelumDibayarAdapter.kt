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
import com.PortalDesa.data.ui.main.activity.admin.DaftarPesananActivity
import kotlinx.android.synthetic.main.item_pesanan.view.*
import retrofit2.Response

/**
 * Created by Sogumontar Hendra Simangunsong on 20/05/2020.
 */
class DaftarPesananBelumDibayarAdapter(val context: Context, val listPesanan : List<PesananResponse>) :
    RecyclerView.Adapter<DaftarPesananBelumDibayarAdapter.ViewHolder>() {

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
    ): DaftarPesananBelumDibayarAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pesanan, parent, false)
        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if(listPesanan.get(position).status!=1){
//            holder?.btnBayar.visibility=View.GONE
//            holder?.btnDelete.visibility=View.VISIBLE
        }
        holder?.btnDelete.setOnClickListener { hapus(listPesanan.get(position).id!!) }
        holder?.btnBayar.visibility=View.GONE
        holder?.alamat.text = listPesanan.get(position).alamat
        holder?.metode.text = listPesanan.get(position).metode
        holder?.harga.text = listPesanan.get(position).harga.toString()
    }

    fun reload(){
        val intent = Intent(context, DaftarPesananActivity::class.java)
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
        return listPesanan.size
    }

}