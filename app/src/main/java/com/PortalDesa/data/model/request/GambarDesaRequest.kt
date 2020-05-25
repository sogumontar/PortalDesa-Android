package com.PortalDesa.data.model.request

import com.fasterxml.jackson.annotation.JsonInclude

/**
 * Created by Sogumontar Hendra Simangunsong on 25/05/2020.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
class GambarDesaRequest{
    var base64File: String? = null
    var skuDesa: String? = null
}