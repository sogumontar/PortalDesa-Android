package com.loginkt.data.model.response

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
class UserResponse {

    var code: Int = 400
    var status: String? = null
    var message: String? = null
    var token: String? = null
}