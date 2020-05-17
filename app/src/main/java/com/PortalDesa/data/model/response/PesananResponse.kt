package com.PortalDesa.data.model.response

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude

/**
 * Created by Sogumontar Hendra Simangunsong on 17/05/2020.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
class PesananResponse {
    var id: String? = null
    var skuProduk: String? = null
    var skuCustomer: String? = null
    var alamat: String? = null
    var harga: String? = null
    var metode: String? = null
    var resi: String? = null
    var status:  Int? =0
}