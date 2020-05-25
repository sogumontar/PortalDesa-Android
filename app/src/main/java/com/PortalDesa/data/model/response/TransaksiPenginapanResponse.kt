package com.PortalDesa.data.model.response

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude

/**
 * Created by Sogumontar Hendra Simangunsong on 26/05/2020.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
class TransaksiPenginapanResponse {
    var id: String? = null
    var skuProduk: String? = null
    var skuCustomer: String? = null
    var harga: Int? = 0
    var lamaMenginap: Int? = 0
    var metode: String? = null
    var checkin: String? = null
    var resi: String? = null
    var status:  Int? =0
    var kodePemesanan:  String? =null
}