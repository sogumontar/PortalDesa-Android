package com.PortalDesa.data.model.request

import com.fasterxml.jackson.annotation.JsonInclude

/**
 * Created by Sogumontar Hendra Simangunsong on 12/05/2020.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
class PenginapanRequest{

    var nama: String? = null
    var harga: Int? = 0
    var deskripsi: String? = null
    var jumlahKamar: Int? = 0
    var lokasi: String? = null
    var desa: String? = null
    var kecamatan: String? = null

}