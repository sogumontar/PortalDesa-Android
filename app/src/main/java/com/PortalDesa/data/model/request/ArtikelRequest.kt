package com.PortalDesa.data.model.request

import com.fasterxml.jackson.annotation.JsonInclude

/**
 * Created by Sogumontar Hendra Simangunsong on 31/05/2020.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
class ArtikelRequest{
    var judul: String? = null
    var jenis: String? = null
    var isi: String? = null
    var sumber: String? = null
    var penulis: String? = null
}