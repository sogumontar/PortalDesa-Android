package com.PortalDesa.data.ui.main.activity.admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.PortalDesa.R
import com.PortalDesa.data.apiService.APIServiceGenerator
import com.PortalDesa.data.base.AppActivity
import com.PortalDesa.data.model.response.PesananResponse
import com.PortalDesa.data.support.Connectivity
import com.PortalDesa.data.ui.main.adapter.admin.DaftarPesananBelumDibayarAdapter
import com.PortalDesa.data.ui.main.adapter.admin.DaftarPesananSudahDibayarADapter
import kotlinx.android.synthetic.main.activity_daftar_pesanan2.*
import retrofit2.Response

class DaftarPesananActivity : AppActivity(),  View.OnClickListener  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daftar_pesanan2)
        recycler_daftar_pesanan_belum_dibayar.setHasFixedSize(true)
        recycler_daftar_pesanan_sudah_dibayar.setHasFixedSize(true)
        val daftarPesananBelumDibayarLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        val daftarPesananSudahDibayarLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recycler_daftar_pesanan_belum_dibayar.setLayoutManager(daftarPesananBelumDibayarLayoutManager)
        recycler_daftar_pesanan_sudah_dibayar.setLayoutManager(daftarPesananSudahDibayarLayoutManager)
        recycler_daftar_pesanan_belum_dibayar.setNestedScrollingEnabled(false)
        recycler_daftar_pesanan_sudah_dibayar.setNestedScrollingEnabled(false)
        initViewDibayar()
        initViewBelumDibayar()
    }

    fun initViewBelumDibayar(){
        if (Connectivity().isNetworkAvailable(this)) {
            val client = APIServiceGenerator().createService
            val call = client.getPesananAll()
            call.enqueue(object : retrofit2.Callback<List<PesananResponse>> {
                override fun onResponse(
                    call: retrofit2.Call<List<PesananResponse>>,
                    response: Response<List<PesananResponse>>
                ) {
                    val listKecamatan = response.body()
                    if(listKecamatan!=null) {
                        val adapter = DaftarPesananBelumDibayarAdapter(
                            this@DaftarPesananActivity,
                            listKecamatan!!
                        )
                        recycler_daftar_pesanan_belum_dibayar.setAdapter(adapter)
                    }
                }

                override fun onFailure(
                    call: retrofit2.Call<List<PesananResponse>>,
                    t: Throwable
                ) {
                    Log.i(this.javaClass.simpleName, " Requested API : " + call.request().body()!!)
                    Log.e(this.javaClass.simpleName, " Exceptions : $t")
                }
            })
        }

    }

    fun initViewDibayar(){
        if (Connectivity().isNetworkAvailable(this)) {
            val client = APIServiceGenerator().createService
            val call = client.getPesananAllSudahBayar()
            call.enqueue(object : retrofit2.Callback<List<PesananResponse>> {
                override fun onResponse(
                    call: retrofit2.Call<List<PesananResponse>>,
                    response: Response<List<PesananResponse>>
                ) {
                    val listData = response.body()
                    if(listData != null) {
                        val adapter =
                            DaftarPesananSudahDibayarADapter(this@DaftarPesananActivity, listData!!)
                        recycler_daftar_pesanan_sudah_dibayar.setAdapter(adapter)
                    }
                }

                override fun onFailure(
                    call: retrofit2.Call<List<PesananResponse>>,
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
