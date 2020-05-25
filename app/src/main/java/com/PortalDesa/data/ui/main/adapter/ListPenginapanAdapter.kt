package com.PortalDesa.data.ui.main.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.PortalDesa.R
import com.PortalDesa.data.apiService.APIServiceGenerator
import com.PortalDesa.data.model.response.DefaultResponse
import com.PortalDesa.data.model.response.PenginapanResponse
import com.PortalDesa.data.support.Connectivity
import com.PortalDesa.data.support.Flag
import com.PortalDesa.data.support.Preferences
import com.PortalDesa.data.ui.main.activity.DetailPenginapanActivity
import com.PortalDesa.data.ui.main.activity.DetailProductAcitivity
import com.PortalDesa.data.ui.main.activity.PenginapanActivity
import com.PortalDesa.data.ui.main.activity.merchant.UpdatePenginapan
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_penginapan.view.*
import retrofit2.Response

/**
 * Created by Sogumontar Hendra Simangunsong on 13/05/2020.
 */
class ListPenginapanAdapter(val context: Context, val listpenginapan: List<PenginapanResponse>) :
    RecyclerView.Adapter<ListPenginapanAdapter.ViewHolder>() {

    lateinit private var preferences: Preferences

    private var penginapanResponse: List<PenginapanResponse>? = null

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        // each data item is just a string in this case
        val tvDesc = v.penginapan_item_nama
        val btnHapus = v.penginapan_btn_hapus
        val btnUpdate = v.penginapan_btn_update
        val ln_product = v.penginapan_item
        val imgPopular = v.img_penginapan
    }
    fun reload(){
        val intents = Intent(context, PenginapanActivity::class.java)
        context.startActivity(intents)
    }
    fun delete(sku : String = ""){
        if (Connectivity().isNetworkAvailable(context)) {
            val client = APIServiceGenerator().createService
            val call = client.deletePenginapan(sku)
            call.enqueue(object : retrofit2.Callback<DefaultResponse> {
                override fun onResponse(
                    call: retrofit2.Call<DefaultResponse>,
                    response: Response<DefaultResponse>
                ) {
                    Toast.makeText(context,"Delete Success", Toast.LENGTH_LONG).show()
                    reload()
                }
                override fun onFailure(call: retrofit2.Call<DefaultResponse>, t: Throwable) {

                }
            })

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int): ListPenginapanAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_penginapan, parent, false)
        preferences = Preferences(context)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Picasso.get()
            .load("https://portal-desa.herokuapp.com" + listpenginapan.get(position).gambar)
            .into(holder.imgPopular)

        holder?.tvDesc.text = listpenginapan.get(position).nama
        holder?.ln_product.setOnClickListener(View.OnClickListener {
            val intent = Intent(context, DetailPenginapanActivity::class.java)
            intent.putExtra(Flag.PENGINAPAN_ID, listpenginapan.get(position).sku)
            context.startActivity(intent)
        })

        if (!preferences.getRoles().equals("ROLE_MERCHANT")) {
            holder.btnHapus.visibility = View.GONE
            holder.btnUpdate.visibility = View.GONE
        }
        holder.btnHapus.setOnClickListener(View.OnClickListener {
            delete(listpenginapan.get(position).sku!!)
        })
        holder.btnUpdate.setOnClickListener(View.OnClickListener {
            val intent = Intent(context, UpdatePenginapan::class.java)
            intent.putExtra(Flag.SKU_PENGINAPAN, listpenginapan.get(position).sku)
            context.startActivity(intent)
        })

    }

    override fun getItemCount(): Int {
        return listpenginapan.size
    }


}