package com.PortalDesa.data.ui.main.activity.merchant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.PortalDesa.R
import com.PortalDesa.data.apiService.APIServiceGenerator
import com.PortalDesa.data.base.AppActivity
import com.PortalDesa.data.model.request.PenginapanRequest
import com.PortalDesa.data.model.response.DefaultResponse
import com.PortalDesa.data.model.response.PenginapanResponse
import com.PortalDesa.data.support.Connectivity
import com.PortalDesa.data.support.Flag
import com.PortalDesa.data.support.Preferences
import kotlinx.android.synthetic.main.activity_update_penginapan.*
import retrofit2.Callback
import retrofit2.Response

class UpdatePenginapan : AppActivity(), View.OnClickListener {
    private var data: PenginapanResponse? = null
    var sku: String = ""
    lateinit var preferences: Preferences
    override fun onCreate(savedInstanceState: Bundle?) {
        val skuPenginapan = intent.getStringExtra(Flag.SKU_PENGINAPAN)
        preferences = Preferences(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_penginapan)
        sku = preferences.getSku()
        getDetailPenginapan()
        update_btn_save.setOnClickListener(this)
    }

    fun displayData(){
        update_penginapan_nama.setText(data?.nama)
        update_penginapan_harga.setText(data?.harga.toString())
        update_penginapan_deskripsi.setText(data?.deskripsi)
        update_penginapan_kamar.setText(data?.jumlahKamar.toString())
        update_penginapan_lokasi.setText(data?.lokasi)
    }

    fun getDetailPenginapan(){
        if (Connectivity().isNetworkAvailable(this)) {
            val client = APIServiceGenerator().createService
            val call = client.lihatPenginapanBySku(intent.getStringExtra(Flag.SKU_PENGINAPAN))
            call.enqueue(object : Callback<PenginapanResponse> {
                override fun onResponse(
                    call: retrofit2.Call<PenginapanResponse>,
                    response: Response<PenginapanResponse>
                ) {
                    val detail = response.body()
                    data = detail
                    displayData()
                }
                override fun onFailure(
                    call: retrofit2.Call<PenginapanResponse>,
                    t: Throwable
                ) {
                }
            })
        }

    }
    fun updatePenginapan(request: PenginapanRequest) {
        showProgressDialog()
        val client = APIServiceGenerator().createService
        val call = client.updatePenginapan(intent.getStringExtra(Flag.SKU_PENGINAPAN), request)
        call.enqueue(object : Callback<DefaultResponse> {
            override fun onResponse(
                call: retrofit2.Call<DefaultResponse>,
                response: Response<DefaultResponse>
            ) {
                val signupResponse = response.body()
                finish()
            }

            override fun onFailure(call: retrofit2.Call<DefaultResponse>, t: Throwable) {
                dismissProgressDialog()
            }
        })

    }


    private fun getData(): PenginapanRequest{
        val requestData = PenginapanRequest()
        requestData.nama= update_penginapan_nama.text.toString()
        requestData.jumlahKamar= Integer.parseInt(update_penginapan_kamar.text.toString())
        requestData.deskripsi= update_penginapan_deskripsi.text.toString()
        requestData.harga= Integer.parseInt(update_penginapan_harga.text.toString())
        requestData.lokasi= update_penginapan_lokasi.text.toString()
        requestData.desa="pintubatu"
        requestData.kecamatan="Silaen"
        requestData.skumerchant=preferences.getSku()
        return requestData
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            update_btn_save.id -> updatePenginapan(getData())
        }
    }
}
