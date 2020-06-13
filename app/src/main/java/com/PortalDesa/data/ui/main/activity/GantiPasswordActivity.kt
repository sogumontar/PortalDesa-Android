package com.PortalDesa.data.ui.main.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.PortalDesa.R
import com.PortalDesa.data.apiService.APIServiceGenerator
import com.PortalDesa.data.base.AppActivity
import com.PortalDesa.data.model.request.GantiPasswordRequest
import com.PortalDesa.data.model.response.DefaultResponse
import com.PortalDesa.data.support.Preferences
import kotlinx.android.synthetic.main.activity_ganti_password.*
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Callback
import retrofit2.Response

class GantiPasswordActivity : AppActivity() , View.OnClickListener {

    lateinit var preferences: Preferences
    var sku: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        preferences = Preferences(this)
        sku = preferences.getSku()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ganti_password)
        btnKirim.setOnClickListener(this)
        initView()
    }

    fun initView(){
        initToolbar(R.id.toolbar)
        tv_toolbar_title.text = "Ganti Password"
    }

    fun gantiPassword(){
        showProgressDialog()
        val client = APIServiceGenerator().createService
        val call = client.gantiPassword(sku,getRequest())
        call.enqueue(object : Callback<DefaultResponse> {
            override fun onResponse(
                call: retrofit2.Call<DefaultResponse>,
                response: Response<DefaultResponse>
            ) {
                val userResponse = response.body()
                if(userResponse?.status == 400) {
                    Toast.makeText(
                        applicationContext,
                        "Password Lama Anda Salah",
                        Toast.LENGTH_SHORT
                    ).show()
                }else{
                    Toast.makeText(
                        applicationContext,
                        "Ganti Password Sukses",
                        Toast.LENGTH_SHORT
                    ).show()
                    goToProfile()
                }
                dismissProgressDialog()
            }

            override fun onFailure(call: retrofit2.Call<DefaultResponse>, t: Throwable) {
                dismissProgressDialog()
            }
        })
    }
    fun goToProfile(){
        val intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
    }

    private fun getRequest(): GantiPasswordRequest{
        val request = GantiPasswordRequest()
        request.currentPassword= passwordLama.text.toString()
        request.newPassword = passwordBaru.text.toString()
        return request
    }
    fun check(){
        if(passwordLama.text.equals("") || passwordBaru.text.equals("") || rePassword.text.equals("")){
            Toast.makeText(this,"Semua Field Harus Di isi",Toast.LENGTH_SHORT).show()
        }else if(!passwordBaru.text.toString().equals(rePassword.text.toString())){
            Toast.makeText(this,"Password Tidak Sama",Toast.LENGTH_SHORT).show()
        }else{
            gantiPassword()
        }
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            btnKirim.id -> check()
        }
    }
}
