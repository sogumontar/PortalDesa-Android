package com.PortalDesa.data.model.request

import com.fasterxml.jackson.annotation.JsonInclude

/**
 * Created by Sogumontar Hendra Simangunsong on 26/05/2020.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
class UpdateDesaRequest{
    var nama: String? = null
    var namaKepalaDesa: String? = null
    var jumlahPenduduk: Int? = 0
    var kecamatan: String? = null
}