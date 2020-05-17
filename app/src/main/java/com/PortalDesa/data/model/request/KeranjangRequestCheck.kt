package com.PortalDesa.data.model.request

import com.fasterxml.jackson.annotation.JsonInclude

/**
 * Created by Sogumontar Hendra Simangunsong on 15/05/2020.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
class KeranjangRequestCheck{
    var skuCustomer: String? = null
    var idProduk: String? = null
}