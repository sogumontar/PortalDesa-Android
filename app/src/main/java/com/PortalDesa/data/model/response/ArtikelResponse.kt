package com.PortalDesa.data.model.response

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude

/**
 * Created by Sogumontar Hendra Simangunsong on 31/05/2020.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
class ArtikelResponse {
    var id: String? = null
    var judul: String? = null
    var date: String? = null
    var jenis: String? = null
    var isi: String? = null
    var sumber: String? = null
    var penulis: String? = null
    var skuAdmin: String? = null
    var status: Int? = 0

}