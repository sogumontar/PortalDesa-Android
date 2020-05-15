package com.PortalDesa.data.model.request

import com.fasterxml.jackson.annotation.JsonInclude

/**
 * Created by Sogumontar Hendra Simangunsong on 15/05/2020.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
class TransaksiRequest{
    var id: String? = null
    var skuProduk: String? = null
    var skuCustomer: String? = null
    var alamat: String? = null
    var harga: Int? = 0
    var metode: String? = null
    var resi: String? = null
    var status: Int? = 0
}