package com.loginkt.data.apiService

interface ApiConfigs {
    companion object {
        const val SIGN_IN = "/auth/signin"
        const val SIGN_UP = "/auth/signup"
        const val LIST_KECAMATAN = "/kecamatan/"
        const val LIST_DESA_KECAMATAN = "/desa/kecamatan/{kecamatan}"
        const val LIST_PRODUK = "/produk/"


        //admin
        const val ADMIN_LIST_AKUN_MERCHANT = "/adminDev/account/merchant"
        const val ADMIN_LIST_AKUN_CUSTOMER = "/adminDev/account/customer"
        const val ADMIN_LIST_AKUN_ADMIN = "/adminDev/account/admin"

        const val CONTENT_TYPE = "application/json"
        const val CODE_SUCCESS = 200

    }
}