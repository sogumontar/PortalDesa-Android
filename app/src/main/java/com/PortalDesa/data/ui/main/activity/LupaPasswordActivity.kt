package com.PortalDesa.data.ui.main.activity


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.PortalDesa.R
import com.PortalDesa.data.apiService.APIServiceGenerator
import com.PortalDesa.data.base.AppActivity
import com.PortalDesa.data.model.response.DefaultResponse
import com.PortalDesa.data.support.Preferences
import kotlinx.android.synthetic.main.activity_lupa_password.*
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Callback
import retrofit2.Response

class LupaPasswordActivity : AppActivity(), View.OnClickListener {

    lateinit var preferences : Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lupa_password)

        preferences = Preferences(this)
        btnKirim.setOnClickListener(this)
        initView()
    }

    fun initView(){
        initToolbar(R.id.toolbar)
        tv_toolbar_title.text = "Lupa Password"
    }

    fun kirimKode(){
        showProgressDialog()
        val client = APIServiceGenerator().createService
        val call = client.lupaPassword(usernameVal.text.toString())
        call.enqueue(object : Callback<DefaultResponse> {
            override fun onResponse(
                call: retrofit2.Call<DefaultResponse>,
                response: Response<DefaultResponse>
            ) {
                val userResponse = response.body()
                if(userResponse?.status == 400){
                    Toast.makeText(applicationContext,"Username tidak terdaftar", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(applicationContext,"Kode verifikasi sudah dikirim ke email anda.", Toast.LENGTH_SHORT).show()
                    goToVerifikasiKode()
                }
                dismissProgressDialog()
            }

            override fun onFailure(call: retrofit2.Call<DefaultResponse>, t: Throwable) {
                dismissProgressDialog()
            }
        })
    }

    fun goToVerifikasiKode(){
        val intent = Intent(this, VerifikasiKodeActivity::class.java)
        startActivity(intent)
    }
    override fun onClick(v: View?) {
        when(v!!.id){
            btnKirim.id -> kirimKode()
        }
    }
}
