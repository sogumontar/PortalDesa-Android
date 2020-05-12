package com.PortalDesa.data.support

class FormValidation {

    fun required(value: String): Boolean {
        if (value.trim().length > 0) {
            return true
        }
        return false

    }
}