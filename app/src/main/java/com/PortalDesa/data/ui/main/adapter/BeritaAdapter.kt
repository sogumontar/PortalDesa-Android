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
import com.PortalDesa.data.model.response.ArtikelResponse
import com.PortalDesa.data.model.response.DefaultResponse
import com.PortalDesa.data.support.Connectivity
import com.PortalDesa.data.support.Flag
import com.PortalDesa.data.support.Preferences
import com.PortalDesa.data.ui.main.activity.ArtikelActivity
import com.PortalDesa.data.ui.main.activity.DetailArtikelActivity
import com.PortalDesa.data.ui.main.activity.Form.EditArtikel
import kotlinx.android.synthetic.main.item_artikel.view.*
import retrofit2.Response

/**
 * Created by Sogumontar Hendra Simangunsong on 31/05/2020.
 */
class BeritaAdapter(val context: Context, var data: List<ArtikelResponse>) :
    RecyclerView.Adapter<BeritaAdapter.ViewHolder>() {

    lateinit private var preferences: Preferences

    private var artikelResponse: List<ArtikelResponse>? = null

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        // each data item is just a string in this case
        val judul = v.artikel_judul
        val isi = v.tv_isi
        val penulis = v.penulis
        val sumber = v.sumber
        val delete = v.btn_delete
        val update = v.btn_update
        val all = v.lin_all
        val allMerchant = v.lin


    }

    fun filterList(myDataset: List<ArtikelResponse>) {
        data = myDataset
        notifyDataSetChanged()
    }

    fun reload() {
        (context as ArtikelActivity).dismissProgressDialog()
        val intent = Intent(context, ArtikelActivity::class.java)
        intent.putExtra(Flag.Id_Artikel, 2)
        context.startActivity(intent)
        (context as ArtikelActivity).finish()
    }

    fun delete(sku: String = "") {
        if (Connectivity().isNetworkAvailable(context)) {
            val client = APIServiceGenerator().createService
            val call = client.deleteArtikel(sku)
            call.enqueue(object : retrofit2.Callback<DefaultResponse> {
                override fun onResponse(
                    call: retrofit2.Call<DefaultResponse>,
                    response: Response<DefaultResponse>
                ) {
                    Toast.makeText(context, "Delete Success", Toast.LENGTH_LONG).show()
                    reload()
                }

                override fun onFailure(call: retrofit2.Call<DefaultResponse>, t: Throwable) {

                }
            })

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BeritaAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_artikel, parent, false)
        preferences = Preferences(context)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (data.get(position).jenis!!.equals("Berita")) {
            holder?.judul.text = data.get(position).judul
            holder?.penulis.text = data.get(position).penulis
            holder?.sumber.text = data.get(position).sumber
            if ((data.get(position).isi)!!.length >= 50) {
                holder?.isi.text = data.get(position).isi!!.substring(50) + "..."
            } else {
                holder?.isi.text = data.get(position).isi
            }

            holder?.update.setOnClickListener(View.OnClickListener {
                val intent = Intent(context, EditArtikel::class.java)
                intent.putExtra(Flag.Id_Artikel, data.get(position).id)
                context.startActivity(intent)
            })
            holder?.all.setOnClickListener(View.OnClickListener {
                val intent = Intent(context, DetailArtikelActivity::class.java)
                intent.putExtra(Flag.Id_Artikel, data.get(position).id)
                context.startActivity(intent)
            })

            if (preferences.getRoles().equals("ROLE_ADMIN")) {
                holder.allMerchant.visibility = View.VISIBLE
                holder.update.visibility = View.GONE
            } else if (preferences.getRoles().equals("ROLE_MERCHANT")) {
                if (data.get(position).skuAdmin.equals(preferences.getSku())) {
                    holder.allMerchant.visibility = View.VISIBLE
                    holder.delete.setOnClickListener(View.OnClickListener {
                        delete(data.get(position).id!!)
                    })
                    holder.update.visibility = View.VISIBLE
                }
            }
//        holder.btnHapus.setOnClickListener(View.OnClickListener {
//            delete(listpenginapan.get(position).sku!!)
//        })
//        holder.btnUpdate.setOnClickListener(View.OnClickListener {
//            val intent = Intent(context, UpdatePenginapan::class.java)
//            intent.putExtra(Flag.SKU_PENGINAPAN, listpenginapan.get(position).sku)
//            context.startActivity(intent)
//        })
        } else {
            holder.all.visibility = View.GONE
        }

    }

    override fun getItemCount(): Int {
        return data.size
    }


}