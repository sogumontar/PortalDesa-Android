package com.PortalDesa.data.ui.main.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.PortalDesa.R
import com.PortalDesa.data.apiService.APIServiceGenerator
import com.PortalDesa.data.base.AppActivity
import com.PortalDesa.data.model.response.ArtikelResponse
import com.PortalDesa.data.support.Connectivity
import com.PortalDesa.data.support.Flag
import com.PortalDesa.data.support.Preferences
import kotlinx.android.synthetic.main.activity_detail_artikel.*
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Callback
import retrofit2.Response

class DetailArtikelActivity : AppActivity() {
    var idArtikel = ""
    private var data: ArtikelResponse? = null
    lateinit var preferences: Preferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_artikel)
        preferences = Preferences(this)
        idArtikel = intent.getStringExtra(Flag.Id_Artikel)
        ln_btn
        initView()
    }

    private fun initView() {
        initToolbar(R.id.toolbar)
        tv_toolbar_title.text = "Artikel"

        showProgressDialog()
        if (Connectivity().isNetworkAvailable(this)) {
            val client = APIServiceGenerator().createService
            val call = client.getDetailArtikel(idArtikel)
            call.enqueue(object : Callback<ArtikelResponse> {
                override fun onResponse(
                    call: retrofit2.Call<ArtikelResponse>,
                    response: Response<ArtikelResponse>
                ) {
                    val detail = response.body()
                    data = detail
                    displayData()
                    dismissProgressDialog()
                }

                override fun onFailure(
                    call: retrofit2.Call<ArtikelResponse>,
                    t: Throwable
                ) {
                    dismissProgressDialog()
                }
            })
        }

    }

    fun displayData() {
        judul.setText(data?.judul)
        jenis.setText(data?.jenis)
        isi.setText(data?.isi)
        sumber.setText(data?.sumber)
        penulis.setText(data?.penulis)

        if(preferences.getRoles().equals("ROLE_MERCHANT") && preferences.getSku().equals(data!!.skuAdmin)){
            ln_btn.visibility=View.VISIBLE
        }
    }
}
