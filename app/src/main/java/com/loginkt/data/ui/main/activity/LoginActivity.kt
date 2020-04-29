package com.loginkt.data.ui.main.activity

import android.content.Intent
import android.os.Bundle
import android.telecom.Call
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.widget.*
import com.google.gson.Gson

import com.loginkt.R
import com.loginkt.data.apiService.APIServiceGenerator
import com.loginkt.data.apiService.ApiConfigs
import com.loginkt.data.apiService.ApiServices
import com.loginkt.data.model.request.UserRequest
import com.loginkt.data.model.response.UserResponse
import com.loginkt.data.support.Preferences
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

   lateinit var preferences : Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        btnLogin.setOnClickListener(){
//            intent = Intent(this, RegisterActivity::class.java)
//            startActivity(intent)
            userLogin(getUser())
        }

    }
    fun userLogin(user : UserRequest){
        val client = APIServiceGenerator().createService

        val call = client.doSignIn(user)
        call.enqueue(object : Callback<UserResponse> {
            override fun onResponse(
                call: retrofit2.Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                val userResponse = response.body()
                if(userResponse != null){
                    val statusCode = userResponse!!.code
                    val token = userResponse!!.token
                    if (statusCode == ApiConfigs.CODE_SUCCESS) {
                        preferences.setToken(token)

                        var intent = Intent(baseContext as LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                    }
                }
            }

            override fun onFailure(call: retrofit2.Call<UserResponse>, t: Throwable) {
                Log.i(this.javaClass.simpleName, " Requested API : " + call.request().body()!!)
                Log.e(this.javaClass.simpleName, " Exceptions : $t")
            }
        })
    }

    private fun getUser(): UserRequest{
        val requestUser = UserRequest()
        requestUser.username = username.text.toString()
        requestUser.password = password.text.toString()
        return requestUser
    }


}
