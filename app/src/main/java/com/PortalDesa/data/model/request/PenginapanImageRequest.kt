package com.PortalDesa.data.model.request

import com.fasterxml.jackson.annotation.JsonInclude

/**
 * Created by Sogumontar Hendra Simangunsong on 12/05/2020.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
class PenginapanImageRequest{
    var nama: String? = null
    var gambar: String? = null

}