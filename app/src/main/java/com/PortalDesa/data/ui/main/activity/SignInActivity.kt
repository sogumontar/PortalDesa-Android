package com.PortalDesa.data.ui.main.activity

import android.content.Intent
import android.os.Bundle
import android.view.View

import com.PortalDesa.R
import com.PortalDesa.data.apiService.APIServiceGenerator
import com.PortalDesa.data.model.request.UserRequest
import com.PortalDesa.data.model.response.UserResponse
import com.PortalDesa.data.support.Preferences
import com.PortalDesa.data.ui.main.activity.admin.MainActivityAdmin
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Callback
import retrofit2.Response
import com.PortalDesa.data.base.AppActivity


class SignInActivity : AppActivity(), View.OnClickListener{

   lateinit var preferences : Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)
        preferences = Preferences(this)
        btnLogin.setOnClickListener(this)
        tvDaftar.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when(v!!.id){
            btnLogin.id->userLogin(getUser())
            tvDaftar.id->goToRegister()
        }
    }
    private fun goToRegister(){
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }



    fun userLogin(user : UserRequest){
        showProgressDialog()
        val client = APIServiceGenerator().createService
        val call = client.doSignIn(user)
        call.enqueue(object : Callback<UserResponse> {
            override fun onResponse(
                call: retrofit2.Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                val userResponse = response.body()
                if(userResponse!!.status != null){
                    val statusCode = userResponse!!.status
                    val token = userResponse!!.accessToken
                    val sku = userResponse!!.skuLog
                    if (statusCode != 401) {
                        preferences.setToken(token)
                        preferences.getSku()
                        preferences.setRole(userResponse!!.role)
                        preferences.setSku(sku)
                        preferences.getSku()

                        goToHome(userResponse   !!.role)
                    }
                }
            }

            override fun onFailure(call: retrofit2.Call<UserResponse>, t: Throwable) {
                dismissProgressDialog()
            }
        })
    }

    private fun goToHome( role: String?){
        var intent = Intent()
        if(role!!.equals("ROLE_ADMIN")){
            intent = Intent(this  , MainActivityAdmin::class.java)
        }else{
            intent = Intent(this, MainActivity::class.java)
        }
        startActivity(intent)
    }

    private fun getUser(): UserRequest{
        val requestUser = UserRequest()
        requestUser.username = username.text.toString()
        requestUser.password = password.text.toString()
        return requestUser
    }


}
