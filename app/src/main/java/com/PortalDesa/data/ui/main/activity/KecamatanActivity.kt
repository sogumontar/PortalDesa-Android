package com.PortalDesa.data.ui.main.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.PortalDesa.R
import com.PortalDesa.data.apiService.APIServiceGenerator
import com.PortalDesa.data.base.AppActivity
import com.PortalDesa.data.model.response.KecamatanResponse
import com.PortalDesa.data.model.response.PenginapanResponse
import com.PortalDesa.data.support.Connectivity
import com.PortalDesa.data.support.Preferences
import com.PortalDesa.data.ui.main.activity.merchant.CreatePenginapanForm
import com.PortalDesa.data.ui.main.adapter.ListPenginapanAdapter
import com.PortalDesa.data.ui.main.adapter.PopularVilageAdapter
import kotlinx.android.synthetic.main.activity_kecamatan.*
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Callback
import retrofit2.Response

class KecamatanActivity : AppActivity(){
    lateinit private var preferences : Preferences
    private var listKecamatan : List<KecamatanResponse>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kecamatan)

        preferences = Preferences(this )
        initView()
        initData()
    }
    fun initView(){
        initToolbar(R.id.toolbar)
        tv_toolbar_title.text = "Daftar Kecamatan"
    }

    fun initData(){
        if (Connectivity().isNetworkAvailable(this)) {
            val client = APIServiceGenerator().createService
            val call = client.getKecamatanList()
            call.enqueue(object : Callback<List<KecamatanResponse>> {
                override fun onResponse(
                    call: retrofit2.Call<List<KecamatanResponse>>,
                    response: Response<List<KecamatanResponse>>
                ) {
                    val listKecamatanResponse = response.body()
                    listKecamatan = listKecamatanResponse
                    displayKecamatan()
                }

                override fun onFailure(
                    call: retrofit2.Call<List<KecamatanResponse>>,
                    t: Throwable
                ) {
                }
            })
        }
    }

    fun displayKecamatan(){
        if (listKecamatan != null && recycler_view_kecamatan != null) {
            recycler_view_kecamatan.setHasFixedSize(true)
            val mainMenuLayoutManager = GridLayoutManager(this, 2)
            recycler_view_kecamatan.setLayoutManager(mainMenuLayoutManager)
            val adapter = PopularVilageAdapter(this!!, listKecamatan!!, false)
            view_animator.setDisplayedChild(1)
            recycler_view_kecamatan.setAdapter(adapter)

        }
    }

    fun goToForm(){
        val intent = Intent(this, CreatePenginapanForm::class.java)
        startActivity(intent)
    }

}
