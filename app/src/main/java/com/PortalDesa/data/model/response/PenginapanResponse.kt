package com.PortalDesa.data.model.response

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude

/**
 * Created by Sogumontar Hendra Simangunsong on 13/05/2020.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
class PenginapanResponse {

    var sku: String? = null
    var nama: String? = null
    var harga: Int? = 0
    var deskripsi: String? = null
    var jumlahKamar: Int?=0
    var lokasi: String?= null
    var gambar: String? = null
    var desa: String?=null
    var kecamatan: String?=null
    var status: String? = null
    var skuMerchant: String? = null

}