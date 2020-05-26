package com.PortalDesa.data.model.response

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude

/**
 * Created by Sogumontar Hendra Simangunsong on 26/05/2020.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
class DesaResponse {
    var sku: String? = null
    var nama: String? = null
    var namaKepalaDesa: String? = null
    var jumlahPenduduk: Int? = 0
    var kecamatan: String? = null
    var gambar: String? = null
    var status: Int? = 0
    var skuAdmin: String? = null
    var kec: String? = null
}