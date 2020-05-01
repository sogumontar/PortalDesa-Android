package com.loginkt.data.model.response

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
class UserResponse {

//    var code: Int = 400
//    var status: String? = null
//    var message: String? = null
//    var token: String? = null

    var accessToken : String? = null
    var tokenType : String? = null
    var role : String? = null
    var skuLog : String? = null
    var status : Int = 1
    var nickName : String? = null
    var email : String? = null

}