package com.PortalDesa.data.model.response

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude

/**
 * Created by Sogumontar Hendra Simangunsong on 06/05/2020.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
class ProductResponse {

    var sku: String? = null
    var nama: String? = null
    var harga: String? = null
    var gambar: String? = null
    var deskripsi: String? = null
    var skuDesa: String? = null
    var status: String? = null
}