package com.PortalDesa.data.ui.main.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.PortalDesa.R
import com.PortalDesa.data.apiService.APIServiceGenerator
import com.PortalDesa.data.model.response.KeranjangResponse
import com.PortalDesa.data.model.response.PenginapanResponse
import com.PortalDesa.data.model.response.ProductResponse
import com.PortalDesa.data.support.Connectivity
import com.PortalDesa.data.support.Flag
import kotlinx.android.synthetic.main.item_keranjang.view.*
import retrofit2.Response

/**
 * Created by Sogumontar Hendra Simangunsong on 14/05/2020.
 */
class KeranjangAdapter(val context : Context, val listKeranjang: List<KeranjangResponse>)
    : RecyclerView.Adapter<KeranjangAdapter.ViewHolder>() {

    private var productResponse:ProductResponse? = null


    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        // each data item is just a string in this case
        val tvDesc = v.btn_del
        val name= v.tv_name
        val harga = v.tv_desc

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_keranjang, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listKeranjang.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.tvDesc.text = "delete"
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
                    holder.harga.setText(listProduk?.harga)
                }

                override fun onFailure(call: retrofit2.Call<ProductResponse>, t: Throwable) {
                    Log.i(this.javaClass.simpleName, " Requested API : " + call.request().body()!!)
                    Log.e(this.javaClass.simpleName, " Exceptions : $t")
                }
            })

        }

    }

}