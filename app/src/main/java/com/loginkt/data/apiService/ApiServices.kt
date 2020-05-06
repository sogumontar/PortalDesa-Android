package com.loginkt.data.apiService

import com.loginkt.data.model.request.SignupRequest
import com.loginkt.data.model.request.UserRequest
import com.loginkt.data.model.response.KecamatanResponse
import com.loginkt.data.model.response.SignupResponse
import com.loginkt.data.model.response.UserResponse
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by Sogumontar Hendra Simangunsong on 13/04/2020.
 */
interface  ApiServices{

    //Sign in
    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE)
    @POST(ApiConfigs.SIGN_IN)
    fun doSignIn(@Body userRequest: UserRequest ): Call<UserResponse>

    //Sign up
    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE)
    @POST(ApiConfigs.SIGN_UP)
    fun doSignup(@Body signupRequest: SignupRequest): Call<SignupResponse>

    //List Product
    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE)
    @GET(ApiConfigs.LIST_PRODUCT)
    fun getProductList(): Call<SignupResponse>

    //List Product
    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE)
    @GET(ApiConfigs.LIST_KECAMATAN)
    fun getKecamatanList(): Call<List<KecamatanResponse>>



}