package com.PortalDesa.data.model.response

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude

/**
 * Created by Sogumontar Hendra Simangunsong on 06/05/2020.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
class PenginapanImageResponse {
    var message: String? = null
}