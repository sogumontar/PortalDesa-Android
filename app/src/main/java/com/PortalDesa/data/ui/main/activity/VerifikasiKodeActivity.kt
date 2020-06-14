package com.PortalDesa.data.ui.main.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.PortalDesa.R
import com.PortalDesa.data.apiService.APIServiceGenerator
import com.PortalDesa.data.base.AppActivity
import com.PortalDesa.data.model.request.VerificationCodeRequest
import com.PortalDesa.data.model.response.DefaultResponse
import com.PortalDesa.data.support.Preferences
import kotlinx.android.synthetic.main.activity_verifikasi_kode.*
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Callback
import retrofit2.Response

class VerifikasiKodeActivity : AppActivity(), View.OnClickListener  {

    lateinit var preferences : Preferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verifikasi_kode)
        preferences = Preferences(this)
        btnKirim.setOnClickListener(this)
        initView()
    }

    fun initView(){
        initToolbar(R.id.toolbar)
        tv_toolbar_title.text = "Verifikasi Kode"
    }

    fun kirimKode(){
        showProgressDialog()
        val client = APIServiceGenerator().createService
        val call = client.checkVerificationCode(getRequest())
        call.enqueue(object : Callback<DefaultResponse> {
            override fun onResponse(
                call: retrofit2.Call<DefaultResponse>,
                response: Response<DefaultResponse>
            ) {
                val userResponse = response.body()
                if(userResponse?.status == 402){
                    Toast.makeText(applicationContext,"Username tidak terdaftar", Toast.LENGTH_SHORT).show()
                }else if(userResponse?.status == 400){
                    Toast.makeText(applicationContext,"Kode verifikasi anda salah", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(applicationContext,"Kode verifikasi sudah dikirim ke email anda.", Toast.LENGTH_SHORT).show()
                    goToLoginForm()
                }
                dismissProgressDialog()
            }

            override fun onFailure(call: retrofit2.Call<DefaultResponse>, t: Throwable) {
                dismissProgressDialog()
            }
        })
    }

    fun getRequest(): VerificationCodeRequest{
        val request = VerificationCodeRequest()
        request.kode = kode.text.toString()
        request.username = usernameVal.text.toString()
        return request
    }

    fun goToLoginForm(){
        val intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)
    }
    override fun onClick(v: View?) {
        when(v!!.id){
            btnKirim.id -> kirimKode()
        }
    }
}
