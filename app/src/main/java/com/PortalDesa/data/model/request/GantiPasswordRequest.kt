package com.PortalDesa.data.model.request

import com.fasterxml.jackson.annotation.JsonInclude

/**
 * Created by Sogumontar Hendra Simangunsong on 07/06/2020.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
class GantiPasswordRequest{
    var currentPassword: String? = null
    var newPassword: String? = null
}