package com.PortalDesa.data.apiService

interface ApiConfigs {
    companion object {
        const val SIGN_IN = "/auth/signin"
        const val SIGN_UP = "/auth/signup"
        const val LIST_KECAMATAN = "/kecamatan/"
        const val LIST_DESA_KECAMATAN = "/desa/kecamatan/{kecamatan}"

        const val DETAIL_PROFILE = "/auth/findSku/{sku}"
        const val UPDATE_DETAIL_PROFILE = "/auth/update/{sku}"

        //admin
        const val ADMIN_LIST_AKUN_MERCHANT = "/adminDev/account/merchant"
        const val ADMIN_LIST_AKUN_CUSTOMER = "/adminDev/account/customer"
        const val ADMIN_LIST_AKUN_ADMIN = "/adminDev/account/admin"

        const val CONTENT_TYPE = "application/json"
        const val CODE_SUCCESS = 200


        //Penginapan
        const val ROUTE_ADD_PENGINAPAN = "/penginapan/add"
        const val ROUTE_PENGINAPAN_ALL = "/penginapan/"
        const val ROUTE_PENGINAPAN_MERCHANT = "/penginapan/bySkuAdmin/{sku}"
        const val ROUTE_PENGINAPAN_DELETE_BY_SKU = "/penginapan/delete/{sku}"
        const val ROUTE_PENGINAPAN_BY_SKU = "/penginapan/{sku}"
        const val ROUTE_UPDATE_PENGINAPAN_BY_SKU = "/update/{sku}"

        //Produk
        const val ROUTE_ADD_PRODUK = "/produk/add"
        const val LIST_PRODUK = "/produk/"
        const val LIST_PRODUK_BY_SKU_ADMIN = "/produk/skuDesa/{sku}"
    }
}