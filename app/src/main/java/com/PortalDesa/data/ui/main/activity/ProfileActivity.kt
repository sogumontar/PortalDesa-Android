package com.PortalDesa.data.ui.main.activity

import android.os.Bundle
import android.view.View
import com.PortalDesa.R
import com.PortalDesa.data.apiService.APIServiceGenerator
import com.PortalDesa.data.base.AppActivity
import com.PortalDesa.data.model.request.UsersUpdateRequest
import com.PortalDesa.data.model.response.ProfileResponse
import com.PortalDesa.data.support.Connectivity
import com.PortalDesa.data.support.Preferences
import kotlinx.android.synthetic.main.activity_profile.*
import retrofit2.Callback
import retrofit2.Response

class ProfileActivity : AppActivity(), View.OnClickListener {
    private var data: ProfileResponse? = null
    var sku: String = ""
    lateinit var preferences: Preferences
    override fun onCreate(savedInstanceState: Bundle?) {
        preferences = Preferences(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        btn_update.setOnClickListener(this)
        sku = preferences.getSku()
        getDetailProfile()
    }


    fun getDetailProfile() {
        showProgressDialog()
        if (Connectivity().isNetworkAvailable(this)) {
            val client = APIServiceGenerator().createService
            val call = client.getDetailProfile(sku)
            call.enqueue(object : Callback<ProfileResponse> {
                override fun onResponse(
                    call: retrofit2.Call<ProfileResponse>,
                    response: Response<ProfileResponse>
                ) {
                    val detail = response.body()
                    data = detail
                    displayData()
                    dismissProgressDialog()
                }

                override fun onFailure(
                    call: retrofit2.Call<ProfileResponse>,
                    t: Throwable
                ) {
                    dismissProgressDialog()
                }
            })
        }

    }

    fun updateDetailUsers(request: UsersUpdateRequest) {
        showProgressDialog()
        val client = APIServiceGenerator().createService
        val call = client.updateProfileBySku(sku, request)
        call.enqueue(object : Callback<UsersUpdateRequest> {
            override fun onResponse(
                call: retrofit2.Call<UsersUpdateRequest>,
                response: Response<UsersUpdateRequest>
            ) {
                val signupResponse = response.body()
                finish()
            }

            override fun onFailure(call: retrofit2.Call<UsersUpdateRequest>, t: Throwable) {
                dismissProgressDialog()
            }
        })

    }

    fun displayData() {
        et_alamat!!.setText(data?.alamat)
        et_email!!.setText(data?.email)
        et_name!!.setText(data?.name)
    }
    private fun getUser(): UsersUpdateRequest{
        val requestUser = UsersUpdateRequest()
        requestUser.name = et_name.text.toString()
        requestUser.alamat = et_alamat.text.toString()
        requestUser.email = et_email.text.toString()
        return requestUser
    }
    override fun onClick(v: View?) {
        when (v!!.id) {
            btn_update.id -> updateDetailUsers(getUser())
        }
    }
}
