package com.PortalDesa.data.ui.main.activity.Form

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.PortalDesa.R
import com.PortalDesa.data.apiService.APIServiceGenerator
import com.PortalDesa.data.base.AppActivity
import com.PortalDesa.data.model.request.DaftarAdminDesaRequest
import com.PortalDesa.data.model.response.DefaultResponse
import com.PortalDesa.data.support.Connectivity
import com.PortalDesa.data.ui.main.activity.admin.MainActivityAdmin
import kotlinx.android.synthetic.main.activity_daftar_admin_desa.*
import retrofit2.Response

class DaftarAdminDesa : AppActivity(),  View.OnClickListener  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daftar_admin_desa)
        btn_admin_daftar_merchant.setOnClickListener(this)
    }

    fun simpan(){
        showProgressDialog()
        if (Connectivity().isNetworkAvailable(this)) {
            val client = APIServiceGenerator().createService
            val call = client.createDataMerchant(getData())
            call.enqueue(object : retrofit2.Callback<DefaultResponse> {
                override fun onResponse(
                    call: retrofit2.Call<DefaultResponse>,
                    response: Response<DefaultResponse>
                ) {
                    goToMainActivity()
                }

                override fun onFailure(call: retrofit2.Call<DefaultResponse>, t: Throwable) {
                    Log.i(this.javaClass.simpleName, " Requested API : " + call.request().body()!!)
                    Log.e(this.javaClass.simpleName, " Exceptions : $t")
                }
            })
        }
    }

    fun goToMainActivity(){
        intent = Intent(this, MainActivityAdmin::class.java)
        startActivity(intent)
    }

    fun getData() : DaftarAdminDesaRequest {
        val daftarAdminDesaRequest= DaftarAdminDesaRequest()
        daftarAdminDesaRequest.nama = namaDesa.text.toString()
        daftarAdminDesaRequest.kecamatan = kecamatan.text.toString()
        daftarAdminDesaRequest.username = username.text.toString()
        daftarAdminDesaRequest.email = email.text.toString()
        daftarAdminDesaRequest.password = password.text.toString()
        daftarAdminDesaRequest.confirmPassword = confirmPassword.text.toString()
        return daftarAdminDesaRequest
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            btn_admin_daftar_merchant.id -> simpan()
        }
    }
}
