package com.PortalDesa.data.model.request

import com.fasterxml.jackson.annotation.JsonInclude

/**
 * Created by Sogumontar Hendra Simangunsong on 14/05/2020.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
class KeranjangRequest{
    var id: String? = null
    var idCustomer: String? = null
    var idProduk: String? = null
    var jumlah: Int? = 0
    var skuDesa: String? = null
    var status: Int? = 0
    var harga: Int? =0

}