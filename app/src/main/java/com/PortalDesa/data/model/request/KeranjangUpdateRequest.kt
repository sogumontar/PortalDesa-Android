package com.PortalDesa.data.model.request

import com.fasterxml.jackson.annotation.JsonInclude

/**
 * Created by Sogumontar Hendra Simangunsong on 21/05/2020.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
class KeranjangUpdateRequest{
    var id: String? = null
    var jumlah: Int? = 0
}