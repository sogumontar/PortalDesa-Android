package com.PortalDesa.data.model.request

import com.fasterxml.jackson.annotation.JsonInclude

/**
 * Created by Sogumontar Hendra Simangunsong on 13/07/2020.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
class KecamatanRequest{
    var nama: String? = null
    var gambar: String? = null

}