package com.PortalDesa.data.ui.main.activity.merchant

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.PortalDesa.R
import com.PortalDesa.data.apiService.APIServiceGenerator
import com.PortalDesa.data.base.AppActivity
import com.PortalDesa.data.model.request.PenginapanRequest
import com.PortalDesa.data.model.response.ProfileResponse
import com.PortalDesa.data.support.Preferences
import com.PortalDesa.data.ui.main.activity.PenginapanActivity
import kotlinx.android.synthetic.main.activity_create_penginapan_form.*
import kotlinx.android.synthetic.main.activity_register_form.*
import kotlinx.android.synthetic.main.fragment_produk.*
import retrofit2.Callback
import retrofit2.Response

class CreatePenginapanForm : AppActivity(), View.OnClickListener {
    private var data: ProfileResponse? = null
    var sku: String = ""
    var nickName: String =""
    lateinit var preferences: Preferences
    override fun onCreate(savedInstanceState: Bundle?) {
        preferences = Preferences(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_penginapan_form)
        btn_save.setOnClickListener(this)
        sku = preferences.getSku()
        nickName = preferences.getNama()
    }
    fun save(request: PenginapanRequest) {
        showProgressDialog()
        val client = APIServiceGenerator().createService
        val call = client.addPenginapan(request)
        call.enqueue(object : Callback<PenginapanRequest> {
            override fun onResponse(
                call: retrofit2.Call<PenginapanRequest>,
                response: Response<PenginapanRequest>
            ) {
                goToPenginapan()
                finish()
            }

            override fun onFailure(call: retrofit2.Call<PenginapanRequest>, t: Throwable) {
                dismissProgressDialog()
            }
        })

    }

    private fun getData(): PenginapanRequest{
        val harga=penginapan_harga.text.toString()
        val jumlah=penginapan_kamar.text.toString()
        val requestUser = PenginapanRequest()
        requestUser.nama = penginapan_nama.text.toString()
        requestUser.harga= Integer.parseInt(harga);
        requestUser.deskripsi= penginapan_deskripsi.text.toString()
        requestUser.jumlahKamar= Integer.parseInt(jumlah);
        requestUser.lokasi= penginapan_lokasi.text.toString()
        requestUser.desa = nickName
        requestUser.kecamatan= "Silaen"
        return requestUser
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            btn_save.id -> save(getData())
        }
    }
    fun goToPenginapan(){
        val intent = Intent(this, PenginapanActivity::class.java)
        startActivity(intent)
    }
}
