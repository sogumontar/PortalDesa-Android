package com.PortalDesa.data.model.response

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude

/**
 * Created by Sogumontar Hendra Simangunsong on 08/05/2020.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
class DaftarAkunResponse {
    var created_at : String? =null
    var updated_at: String? =null
    var sku: String? =null
    var name:String? =null
    var alamat: String? =null
    var username: String? =null
    var email: String? =null
    var password: String? =null
    var roles: MutableList<role>?=null
    var status: Int? =0

    //for Role
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    class role(){
        var id :Int?=0
        var name :String?=null
    }
}