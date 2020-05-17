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
import com.PortalDesa.data.model.response.KeranjangResponse
import com.PortalDesa.data.model.response.PenginapanResponse
import com.PortalDesa.data.model.response.ProductResponse
import com.PortalDesa.data.support.Connectivity
import com.PortalDesa.data.support.Flag
import com.PortalDesa.data.ui.main.activity.KeranjangActivity
import kotlinx.android.synthetic.main.item_keranjang.view.*
import retrofit2.Response

/**
 * Created by Sogumontar Hendra Simangunsong on 14/05/2020.
 */
class KeranjangAdapter(val context: Context, val listKeranjang: List<KeranjangResponse>) :
    RecyclerView.Adapter<KeranjangAdapter.ViewHolder>() {

    private var productResponse: ProductResponse? = null


    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        // each data item is just a string in this case
        val tvDesc = v.btn_del
        val name = v.tv_name
        val harga = v.tv_desc
        val jumlah = v.tv_pcs
        val incr = v.incr_keranjang
        val decr = v.decr_keranjang

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_keranjang, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listKeranjang.size
    }

    fun goToKeranjang() {
        Toast.makeText(context, "Hapus item keranjang berhasil", Toast.LENGTH_SHORT)
        val intent = Intent(context, KeranjangActivity::class.java)
        context.startActivity(intent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (Connectivity().isNetworkAvailable(context)) {
            val client = APIServiceGenerator().createService
            val call = client.getProductBySku(listKeranjang.get(position).idProduk!!)
            call.enqueue(object : retrofit2.Callback<ProductResponse> {
                override fun onResponse(
                    call: retrofit2.Call<ProductResponse>,
                    response: Response<ProductResponse>
                ) {
                    val listProduk = response.body()
                    holder.name.setText(listProduk?.nama)
                    holder.harga.setText("Rp." + listProduk?.harga)
                    holder.jumlah.setText(listKeranjang.get(position).jumlah!!.toString())
                }

                override fun onFailure(call: retrofit2.Call<ProductResponse>, t: Throwable) {
                    Log.i(this.javaClass.simpleName, " Requested API : " + call.request().body()!!)
                    Log.e(this.javaClass.simpleName, " Exceptions : $t")
                }
            })

        }


        holder.tvDesc.text = "hapus"
        holder.tvDesc.setOnClickListener {
            delete(listKeranjang.get(position).id!!)
        }

        holder.decr.setOnClickListener {
            if (holder.jumlah.text.toString().equals("1")) {
                delete(listKeranjang.get(position).id!!)
            } else {
                holder.jumlah.setText((Integer.parseInt(holder.jumlah.text.toString()) - 1).toString())
            }
        }

        holder.incr.setOnClickListener {
            holder.jumlah.setText((Integer.parseInt(holder.jumlah.text.toString()) + 1).toString())

        }


    }

    fun delete(value: String) {
        if (Connectivity().isNetworkAvailable(context)) {
            val client = APIServiceGenerator().createService
            val call = client.deleteCart(value)
            call.enqueue(object : retrofit2.Callback<DefaultResponse> {
                override fun onResponse(
                    call: retrofit2.Call<DefaultResponse>,
                    response: Response<DefaultResponse>
                ) {
                    goToKeranjang()
                }

                override fun onFailure(call: retrofit2.Call<DefaultResponse>, t: Throwable) {
                    Log.e(this.javaClass.simpleName, " Exceptions : $t")
                }
            })

        }
    }


}
