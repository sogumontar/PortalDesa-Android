package com.PortalDesa.data.model.request

import com.fasterxml.jackson.annotation.JsonInclude

/**
 * Created by Sogumontar Hendra Simangunsong on 15/05/2020.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
class CustomerRequest{
    var id: String? = null
    var sku: String? = null
    var alamat: String? = null

}