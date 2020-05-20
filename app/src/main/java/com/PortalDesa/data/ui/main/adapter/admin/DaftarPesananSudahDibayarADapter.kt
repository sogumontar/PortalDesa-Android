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

        if(listPesanan.get(position).status!=1){
            holder?.btnBlocked.visibility=View.GONE
            holder?.btnActivated.visibility=View.VISIBLE
        }
        holder?.btnBlocked.setOnClickListener { hapus(listPesanan.get(position).id!!) }
        holder?.btnActivated.visibility=View.GONE
        holder?.alamat.text = listPesanan.get(position).alamat
        holder?.metode.text = listPesanan.get(position).metode
        holder?.harga.text = listPesanan.get(position).harga
    }

    fun reload(){
        val intent = Intent(context, DaftarPesananSudahDibayarADapter::class.java)
        context.startActivity(intent)
    }
    fun hapus(id:String){

//        if (Connectivity().isNetworkAvailable(context)) {
//            val client = APIServiceGenerator().createService
//            val call = client.suspendAccount(sku)
//            call.enqueue(object : retrofit2.Callback<DefaultResponse> {
//                override fun onResponse(
//                    call: retrofit2.Call<DefaultResponse>,
//                    response: Response<DefaultResponse>
//                ) {
//                    Toast.makeText(context,"Suspend Account Success", Toast.LENGTH_LONG).show()
//                    reload()
//                }
//                override fun onFailure(call: retrofit2.Call<DefaultResponse>, t: Throwable) {
//
//                }
//            })
//
//        }
    }
    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return listPesanan.size
    }

}