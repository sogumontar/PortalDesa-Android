package com.PortalDesa.data.ui.main.activity.admin

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.PortalDesa.R
import com.PortalDesa.data.apiService.APIServiceGenerator
import com.PortalDesa.data.base.AppActivity
import com.PortalDesa.data.model.response.DaftarAkunResponse
import com.PortalDesa.data.support.Connectivity
import com.PortalDesa.data.ui.main.adapter.admin.DaftarAkunAdapter
import com.PortalDesa.data.ui.main.adapter.admin.DaftarAkunCustomerAdapter
import kotlinx.android.synthetic.main.activity_daftar_akun.*
import retrofit2.Response

class DaftarAkunActivity : AppActivity(),  View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daftar_akun)
        recycler_daftar_akun.setHasFixedSize(true)
        recycler_daftar_akun_customer.setHasFixedSize(true)
        val daftarAkunLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        val daftarAkunCustomerLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recycler_daftar_akun.setLayoutManager(daftarAkunLayoutManager)
        recycler_daftar_akun_customer.setLayoutManager(daftarAkunCustomerLayoutManager)
        recycler_daftar_akun.setNestedScrollingEnabled(false)
        recycler_daftar_akun_customer.setNestedScrollingEnabled(false)
        initViewCustomer()
        initView()
    }
    fun initView(){
        if (Connectivity().isNetworkAvailable(this)) {
            val client = APIServiceGenerator().createService
            val call = client.getDaftarAkunMerchantList()
            call.enqueue(object : retrofit2.Callback<List<DaftarAkunResponse>> {
                override fun onResponse(
                    call: retrofit2.Call<List<DaftarAkunResponse>>,
                    response: Response<List<DaftarAkunResponse>>
                ) {
                    val listKecamatan = response.body()
                    val adapter = DaftarAkunAdapter(this@DaftarAkunActivity, listKecamatan!!)
                    recycler_daftar_akun.setAdapter(adapter)
                }

                override fun onFailure(
                    call: retrofit2.Call<List<DaftarAkunResponse>>,
                    t: Throwable
                ) {
                    Log.i(this.javaClass.simpleName, " Requested API : " + call.request().body()!!)
                    Log.e(this.javaClass.simpleName, " Exceptions : $t")
                }
            })
        }

    }

    fun initViewCustomer(){
        if (Connectivity().isNetworkAvailable(this)) {
            val client = APIServiceGenerator().createService
            val call = client.getDaftarAkunCustomerList()
            call.enqueue(object : retrofit2.Callback<List<DaftarAkunResponse>> {
                override fun onResponse(
                    call: retrofit2.Call<List<DaftarAkunResponse>>,
                    response: Response<List<DaftarAkunResponse>>
                ) {
                    val listKecamatan = response.body()
                    val adapter = DaftarAkunCustomerAdapter(this@DaftarAkunActivity, listKecamatan!!)
                    recycler_daftar_akun_customer.setAdapter(adapter)
                }

                override fun onFailure(
                    call: retrofit2.Call<List<DaftarAkunResponse>>,
                    t: Throwable
                ) {
                    Log.i(this.javaClass.simpleName, " Requested API : " + call.request().body()!!)
                    Log.e(this.javaClass.simpleName, " Exceptions : $t")
                }
            })
        }

    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }
}
