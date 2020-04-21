package com.loginkt.data.apiService

import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by Sogumontar Hendra Simangunsong on 13/04/2020.
 */
interface  ApiServices{

    @GET(ApiKeys.SIGN_IN)
    fun getUsers(@Path("username") username: String )

}