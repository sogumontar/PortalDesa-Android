package com.loginkt.data.model.request

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
class UserRequest{

    var username: String = ""
    var password: String = ""
}