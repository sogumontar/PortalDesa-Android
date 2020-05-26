package com.PortalDesa.data.ui.main.activity

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.PortalDesa.R
import com.PortalDesa.data.apiService.APIServiceGenerator
import com.PortalDesa.data.base.AppActivity
import com.PortalDesa.data.model.response.ListDesaKecamatanResponse
import com.PortalDesa.data.support.Connectivity
import com.PortalDesa.data.support.Flag
import com.PortalDesa.data.ui.main.adapter.DaftarDesaAdapter
import com.PortalDesa.data.ui.main.adapter.PenginapanAdapter
import kotlinx.android.synthetic.main.activity_detail_kecamatan.*
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Callback
import retrofit2.Response

class DetailKecamatanActivtiy : AppActivity() {

    private var listDesa : List<ListDesaKecamatanResponse>? = null
    var name : String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_kecamatan)
        name = intent.getStringExtra(Flag.PRODUCT_NAME)

        initView()
        getDataKecamatan()

    }
    fun initView(){
        tv_kecamatan.setText("Daftar desa kecamatan "+ name )
        initToolbar(R.id.toolbar)
        tv_toolbar_title.text = getString(R.string.title_daftar_desa)
    }

    fun getDataKecamatan(){
        if (Connectivity().isNetworkAvailable(this)) {
            val client = APIServiceGenerator().createService
            val call = client.getDesaByKecamatan(name)
            call.enqueue(object : Callback<List<ListDesaKecamatanResponse>> {
                override fun onResponse(
                    call: retrofit2.Call<List<ListDesaKecamatanResponse>>,
                    response: Response<List<ListDesaKecamatanResponse>>
                ) {
                    val listKecamatanResponse = response.body()
                    listDesa = listKecamatanResponse
                    displayListDesa(listDesa!!)

                }

                override fun onFailure(
                    call: retrofit2.Call<List<ListDesaKecamatanResponse>>,
                    t: Throwable
                ) {
                }
            })
        }

    }
    fun displayListDesa(listData : List<ListDesaKecamatanResponse>){
        if (listDesa != null) {
            if(listData.size>0) {
                recycler_view.setHasFixedSize(true)
                val menuListLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
                recycler_view.setLayoutManager(menuListLayoutManager)
                val adapter = DaftarDesaAdapter(this,listData)
                view_animator.setDisplayedChild(1)
                recycler_view.setAdapter(adapter)
            }else{
                view_animator.setDisplayedChild(1)
                tv_error.visibility = View.VISIBLE
            }

        }
    }
}
