package com.loginkt.data.apiService

interface ApiConfigs {
    companion object {
        const val SIGN_IN = "/auth/signin"
        const val SIGN_UP = "/auth/signup"
        const val LIST_KECAMATAN = "/kecamatan/"
        const val LIST_PRODUK = "/produk/"

        const val CONTENT_TYPE = "application/json"
        const val CODE_SUCCESS = 200

    }
}