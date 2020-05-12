package com.PortalDesa.data.model.response

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
class KecamatanResponse {
    var sku: String? = null
    var fotoCamat: String? = null
    var gambarKecamatan: String? = null
    var nama: String? = null
    var namaCamat: String? = null
}