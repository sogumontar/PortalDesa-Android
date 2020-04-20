package com.loginkt.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Sogumontar Hendra Simangunsong on 13/04/2020.
 */
class Users {
    @SerializedName("email")
    @Expose
    var email: String? = null

    @SerializedName("nickname")
    @Expose
    var nickname: String? = null

    @SerializedName("username")
    @Expose
    var username: String? = null

    @SerializedName("password")
    @Expose
    var password: String? = null

    @SerializedName("confirmPassword")
    @Expose
    var confirmPassword: String? = null


}