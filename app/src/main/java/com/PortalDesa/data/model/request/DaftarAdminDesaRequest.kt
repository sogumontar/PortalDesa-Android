package com.PortalDesa.data.model.request

import com.fasterxml.jackson.annotation.JsonInclude

/**
 * Created by Sogumontar Hendra Simangunsong on 19/05/2020.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
class DaftarAdminDesaRequest{
    var nama: String? = null
    var namaKepalaDesa: String? = null
    var jumlahPenduduk: Int? = 0
    var kecamatan: String? = null
    var gambar: String? = null
    var username: String? = null
    var email: String? = null
    var password: String? = null
    var confirmPassword: String? = null

}