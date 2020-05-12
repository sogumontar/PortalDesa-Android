package com.PortalDesa.data.model.request

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
class UserRequest{

    var username: String? = null
    var password: String? = null
}