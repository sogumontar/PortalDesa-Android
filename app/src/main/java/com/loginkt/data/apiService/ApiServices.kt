package com.loginkt.data.apiService

import com.loginkt.data.model.request.UserRequest
import com.loginkt.data.model.response.UserResponse
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by Sogumontar Hendra Simangunsong on 13/04/2020.
 */
interface  ApiServices{

    @GET(ApiConfigs.SIGN_IN)
    fun getUsers(@Path("username") username: String )

    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE)
    @POST(ApiConfigs.SIGN_IN)
    fun doSignIn(@Body insuranceInquiryRequest: UserRequest): Call<UserResponse>



}