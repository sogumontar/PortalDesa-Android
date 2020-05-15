package com.PortalDesa.data.ui.main.activity

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.PortalDesa.R
import com.PortalDesa.data.apiService.APIServiceGenerator
import com.PortalDesa.data.base.AppActivity
import com.PortalDesa.data.model.response.KeranjangResponse
import com.PortalDesa.data.support.Connectivity
import com.PortalDesa.data.support.Preferences
import com.PortalDesa.data.ui.main.adapter.KeranjangAdapter
import kotlinx.android.synthetic.main.activity_keranjang.*
import retrofit2.Response

class KeranjangActivity : AppActivity() {

    private var keranjangResponse: List<KeranjangResponse>? = null
    var sku: String = ""
    lateinit var preferences: Preferences
    override fun onCreate(savedInstanceState: Bundle?) {
        preferences = Preferences(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_keranjang)
        recycler_view_keranjang.setHasFixedSize(true)
        val menuListLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recycler_view_keranjang.setLayoutManager(menuListLayoutManager)
        recycler_view_keranjang.setNestedScrollingEnabled(false)
        initData()
        initView()
    }


    fun initData(){
        val role = preferences.getRoles()
        val sku = preferences.getSku()
        if (Connectivity().isNetworkAvailable(this)) {
            val client = APIServiceGenerator().createService
                val call = client.cartCustomer(sku)
                call.enqueue(object : retrofit2.Callback<List<KeranjangResponse>> {
                    override fun onResponse(
                        call: retrofit2.Call<List<KeranjangResponse>>,
                        response: Response<List<KeranjangResponse>>
                    ) {
                        val listProduk = response.body()
                        keranjangResponse= listProduk
                        displayProduct()
                    }

                    override fun onFailure(call: retrofit2.Call<List<KeranjangResponse>>, t: Throwable) {
                        Log.i(this.javaClass.simpleName, " Requested API : " + call.request().body()!!)
                        Log.e(this.javaClass.simpleName, " Exceptions : $t")
                    }
                })

        }
    }
    fun displayProduct(){
        if (keranjangResponse != null && recycler_view_keranjang!= null) {
            val produkListLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
            recycler_view_keranjang.setLayoutManager(produkListLayoutManager)
            val adapter = KeranjangAdapter(this, keranjangResponse!!)
            recycler_view_keranjang.setAdapter(adapter)
        }
    }

    fun initView(){
        initToolbar(R.id.toolbar)
//        val adapter = KeranjangAdapter(this,keranjangResponse!!)
//        recycler_view_keranjang.setAdapter(adapter)

    }


}
