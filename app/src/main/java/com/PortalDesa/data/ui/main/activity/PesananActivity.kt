package com.PortalDesa.data.ui.main.activity

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
import com.PortalDesa.data.ui.main.adapter.PesananSudahBayarAdapter
import kotlinx.android.synthetic.main.activity_pesanan.*
import retrofit2.Response

class PesananActivity : AppActivity() {
    private var pesananResponse: List<PesananResponse>? = null
    private var pesananResponseSudahBayar: List<PesananResponse>? = null
    lateinit var preferences: Preferences
    override fun onCreate(savedInstanceState: Bundle?) {
        preferences = Preferences(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pesanan)
        recycler_view_pesanan.setHasFixedSize(true)
        recycler_view_pesanan_sudah_bayar.setHasFixedSize(true)
        val menuListLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        val menuListLayoutManagers = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recycler_view_pesanan.setLayoutManager(menuListLayoutManager)
        recycler_view_pesanan.setNestedScrollingEnabled(false)
        recycler_view_pesanan_sudah_bayar.setLayoutManager(menuListLayoutManagers)
        recycler_view_pesanan_sudah_bayar.setNestedScrollingEnabled(false)
        initData()
        initDataSudahBayar()
    }

    fun initData() {
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
                    pesananResponse = listProduk
                    if(listProduk != null) {
                        val adapter =
                            PesananAdapter(this@PesananActivity, listProduk!!)
                        recycler_view_pesanan.setAdapter(adapter)
                    }

                }

                override fun onFailure(call: retrofit2.Call<List<PesananResponse>>, t: Throwable) {
                    Log.i(this.javaClass.simpleName, " Requested API : " + call.request().body()!!)
                    Log.e(this.javaClass.simpleName, " Exceptions : $t")
                }
            })

        }
    }

    fun initDataSudahBayar() {
        val sku = preferences.getSku()
        if (Connectivity().isNetworkAvailable(this)) {
            val client = APIServiceGenerator().createService
            val call = client.getPesananSudahBayar(sku)
            call.enqueue(object : retrofit2.Callback<List<PesananResponse>> {
                override fun onResponse(
                    call: retrofit2.Call<List<PesananResponse>>,
                    response: Response<List<PesananResponse>>
                ) {
                    val listProduk = response.body()
                    pesananResponseSudahBayar = listProduk

                    if(listProduk != null) {
                        val adapter =
                            PesananSudahBayarAdapter(this@PesananActivity, listProduk!!)
                        recycler_view_pesanan_sudah_bayar.setAdapter(adapter)
                    }

                }

                override fun onFailure(call: retrofit2.Call<List<PesananResponse>>, t: Throwable) {
                    Log.i(this.javaClass.simpleName, " Requested API : " + call.request().body()!!)
                    Log.e(this.javaClass.simpleName, " Exceptions : $t")
                }
            })

        }
    }



}
