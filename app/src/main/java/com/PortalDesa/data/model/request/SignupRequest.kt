package com.PortalDesa.data.model.request

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
class SignupRequest{

    var name: String? = null
    var alamat: String? = null
    var username: String? = null
    var email: String? = null
    var password: String? = null
    var confirmPassword: String? = null
    var role: String? = null
}