package com.PortalDesa.data.model.request

import com.fasterxml.jackson.annotation.JsonInclude

/**
 * Created by Sogumontar Hendra Simangunsong on 13/05/2020.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
class ProdukRequest{
    var nama: String? = null
    var harga: Int? = 0
    var deskripsi: String? = null
    var skuDesa: String? = null

}

