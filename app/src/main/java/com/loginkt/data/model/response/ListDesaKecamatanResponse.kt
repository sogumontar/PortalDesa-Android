package com.loginkt.data.model.response

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
class ListDesaKecamatanResponse {
    var sku: String? = null
    var nama: String? = null
    var jumlahPenduduk: Int = 0
    var kecamatan: String? = null
    var gambar: String? = null
    var kec: String? = null
}