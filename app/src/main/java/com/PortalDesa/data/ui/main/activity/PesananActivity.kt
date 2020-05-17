package com.PortalDesa.data.ui.main.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.PortalDesa.R
import com.PortalDesa.data.apiService.APIServiceGenerator
import com.PortalDesa.data.base.AppActivity
import com.PortalDesa.data.model.response.PesananResponse
import com.PortalDesa.data.support.Connectivity
import com.PortalDesa.data.support.Preferences
import com.PortalDesa.data.ui.main.adapter.PesananAdapter
import kotlinx.android.synthetic.main.activity_pesanan.*
import retrofit2.Response

class PesananActivity : AppActivity() {
    private var pesananResponse:  List<PesananResponse>? = null
    lateinit var preferences: Preferences
    override fun onCreate(savedInstanceState: Bundle?) {
        preferences = Preferences(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pesanan)
        recycler_view_pesanan.setHasFixedSize(true)
        val menuListLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recycler_view_pesanan.setLayoutManager(menuListLayoutManager)
        recycler_view_pesanan.setNestedScrollingEnabled(false)
        initData()
    }
    fun initData(){
        val sku = preferences.getSku()
        if (Connectivity().isNetworkAvailable(this)) {
            val client = APIServiceGenerator().createService
            val call = client.getPesanan(sku)
            call.enqueue(object : retrofit2.Callback<List<PesananResponse>> {
                override fun onResponse(
                    call: retrofit2.Call<List<PesananResponse>>,
                    response: Response<List<PesananResponse>>
                ) {
                    val listProduk = response.body()
                    pesananResponse= listProduk

                        initView()
                        displayProduct()

                }

                override fun onFailure(call: retrofit2.Call<List<PesananResponse>>, t: Throwable) {
                    Log.i(this.javaClass.simpleName, " Requested API : " + call.request().body()!!)
                    Log.e(this.javaClass.simpleName, " Exceptions : $t")
                }
            })

        }
    }

    fun initView(){
        initToolbar(R.id.toolbar)
        val adapter = PesananAdapter(this,pesananResponse!!)
        recycler_view_pesanan.setAdapter(adapter)
    }

    fun displayProduct(){
        if (pesananResponse != null && recycler_view_pesanan!= null) {
            val produkListLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
            recycler_view_pesanan.setLayoutManager(produkListLayoutManager)

            val adapter = PesananAdapter(this, pesananResponse!!)
            recycler_view_pesanan.setAdapter(adapter)
        }
    }
}
