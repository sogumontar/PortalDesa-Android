package com.PortalDesa.data.model.response

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude

/**
 * Created by Sogumontar Hendra Simangunsong on 14/05/2020.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
class KeranjangResponse {
    var id: String? = null
    var idCustomer: String? = null
    var idProduk: String? = null
    var jumlah: Int? = 0
    var skuDesa: String? = null
    var status: Int? = null
}