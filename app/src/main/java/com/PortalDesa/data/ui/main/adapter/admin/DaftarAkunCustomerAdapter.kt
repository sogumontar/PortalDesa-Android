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
import com.PortalDesa.data.model.response.DaftarAkunResponse
import com.PortalDesa.data.model.response.DefaultResponse
import com.PortalDesa.data.support.Connectivity
import com.PortalDesa.data.support.Preferences
import com.PortalDesa.data.ui.main.activity.admin.DaftarAkunActivity
import kotlinx.android.synthetic.main.item_daftar_akun_customer.view.*
import retrofit2.Response

/**
 * Created by Sogumontar Hendra Simangunsong on 08/05/2020.
 */
class DaftarAkunCustomerAdapter(val context: Context,val listAkun : List<DaftarAkunResponse>) :
    RecyclerView.Adapter<DaftarAkunCustomerAdapter.ViewHolder>() {

    lateinit private var preferences: Preferences

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        // each data item is just a string in this case
        val tvDesc = v.tv_nama_akun_admin
        val tvAlamat = v.tv_alamat
        val tvEmail = v.tv_email
        val btnBlocked = v.btn_blocked
        val btnActivated = v.btn_activated

    }


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DaftarAkunCustomerAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_daftar_akun_customer, parent, false)
        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if(listAkun.get(position).status!=1){
            holder?.btnBlocked.visibility=View.GONE
            holder?.btnActivated.visibility=View.VISIBLE
        }
        holder?.btnBlocked.setOnClickListener { blocked(listAkun.get(position).sku!!) }
        holder?.btnActivated.setOnClickListener { activated(listAkun.get(position).sku!!) }
        holder?.tvDesc.text = listAkun.get(position).name
        holder?.tvAlamat.text = listAkun.get(position).alamat
        holder?.tvEmail.text = listAkun.get(position).email
    }

    fun reload(){
        val intent = Intent(context, DaftarAkunActivity::class.java)
        context.startActivity(intent)
    }
    fun blocked(sku:String){
        if (Connectivity().isNetworkAvailable(context)) {
            val client = APIServiceGenerator().createService
            val call = client.suspendAccount(sku)
            call.enqueue(object : retrofit2.Callback<DefaultResponse> {
                override fun onResponse(
                    call: retrofit2.Call<DefaultResponse>,
                    response: Response<DefaultResponse>
                ) {
                    Toast.makeText(context,"Suspend Account Success", Toast.LENGTH_LONG).show()
                    reload()
                }
                override fun onFailure(call: retrofit2.Call<DefaultResponse>, t: Throwable) {

                }
            })

        }
    }

    fun activated(sku:String){
        if (Connectivity().isNetworkAvailable(context)) {
            val client = APIServiceGenerator().createService
            val call = client.activateAccount(sku)
            call.enqueue(object : retrofit2.Callback<DefaultResponse> {
                override fun onResponse(
                    call: retrofit2.Call<DefaultResponse>,
                    response: Response<DefaultResponse>
                ) {
                    Toast.makeText(context,"ACtivated Account Success", Toast.LENGTH_LONG).show()
                    reload()
                }
                override fun onFailure(call: retrofit2.Call<DefaultResponse>, t: Throwable) {

                }
            })

        }
    }
    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return listAkun.size
    }
}