package com.loginkt.data.service

import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by Sogumontar Hendra Simangunsong on 13/04/2020.
 */
interface  UsersService{

    @GET("")
    fun getUsers(@Path("username") username: String )

}