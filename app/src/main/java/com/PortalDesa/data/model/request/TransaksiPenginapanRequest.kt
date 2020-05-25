package com.PortalDesa.data.model.request

import com.fasterxml.jackson.annotation.JsonInclude

/**
 * Created by Sogumontar Hendra Simangunsong on 25/05/2020.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
class TransaksiPenginapanRequest{

    var skuProduk: String? = null
    var skuCustomer: String? = null
    var harga: Int? = 0
    var metode: String? = null
    var lamaMenginap: Int? = 0
    var checkin: String? = null
}