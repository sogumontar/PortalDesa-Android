package com.PortalDesa.data.ui.main.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.PortalDesa.R
import com.PortalDesa.data.apiService.APIServiceGenerator
import com.PortalDesa.data.base.AppActivity
import com.PortalDesa.data.model.request.SignupRequest
import com.PortalDesa.data.model.response.SignupResponse
import com.PortalDesa.data.support.Connectivity
import com.PortalDesa.data.support.FormValidation
import com.PortalDesa.data.support.TopSnackBar
import kotlinx.android.synthetic.main.activity_register_form.*
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppActivity(), View.OnClickListener {
    lateinit var topSnackBar: TopSnackBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_form)
        initView()
        topSnackBar = TopSnackBar()
        btn_daftar.setOnClickListener(this)
    }

    private fun initView() {
        initToolbar(R.id.toolbar)
        tv_toolbar_title.text = getString(R.string.sign_up_tab_title_sign_up)
    }

    private fun doRegister(){
        if(checkField()) {
            if (Connectivity().isNetworkAvailable(this)) {
                showProgressDialog()
                val client = APIServiceGenerator().createService
                val call = client.doSignup(getSignUpRequest())
                call.enqueue(object : Callback<SignupResponse> {
                    override fun onResponse(
                        call: retrofit2.Call<SignupResponse>,
                        response: Response<SignupResponse>
                    ) {
                        val signupResponse = response.body()
                        goToHome()
                        finish()
                    }

                    override fun onFailure(call: retrofit2.Call<SignupResponse>, t: Throwable) {
                        dismissProgressDialog()
                    }
                })
            }
        }
    }

    private fun goToHome(){
        intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)
    }

    private fun checkField(): Boolean {
        var check = true
        if (!FormValidation().required(et_name.getText().toString())) {
            showMessage("Nama tidak boleh kosong")
            check = false
        }else if (!FormValidation().required(et_username.getText().toString())) {
            showMessage("Username tidak boleh kosong")
            check = false
        }else if (!FormValidation().required(et_email.getText().toString())) {
            showMessage("Email tidak boleh kosong")
            check = false
        }else if (!FormValidation().required(et_alamat.getText().toString())) {
            showMessage("Alamat tidak boleh kosong")
            check = false
        }else if (!FormValidation().required(et_password.getText().toString())) {
            showMessage("Password tidak boleh kosong")
            check = false
        }else if (!FormValidation().required(et_repass.getText().toString())) {
            showMessage("Re-Password tidak boleh kosong")
            check = false
        }

        return check
    }


    override fun onClick(v: View?) {
        when(v!!.id){
            btn_daftar.id -> doRegister()
        }
    }

    private fun showMessage(message: String) {
        topSnackBar.showError(this, findViewById(R.id.snackbar_container), message)
    }

    private fun getSignUpRequest(): SignupRequest {
        val signupRequest = SignupRequest()
        signupRequest.name = et_name.text.toString()
        signupRequest.username = et_username.text.toString()
        signupRequest.email = et_email.text.toString()
        signupRequest.alamat = et_alamat.text.toString()
        signupRequest.password = et_password.text.toString()
        signupRequest.confirmPassword = et_repass.text.toString()
        return signupRequest
    }
}

