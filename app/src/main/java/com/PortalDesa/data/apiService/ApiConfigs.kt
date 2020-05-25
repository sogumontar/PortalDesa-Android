package com.PortalDesa.data.apiService

interface ApiConfigs {
    companion object {

        //Account
        const val SUSPEND_ACCOUNT= "/adminDev/account/suspend/{sku}"
        const val ACTIVATE_ACCOUNT= "/adminDev/account/activate/{sku}"

        //Auth
        const val SIGN_IN = "/auth/signin"
        const val SIGN_UP = "/auth/signup"
        const val LIST_KECAMATAN = "/kecamatan/"
        const val LIST_DESA_KECAMATAN = "/desa/kecamatan/{kecamatan}"
        const val ADD_DESA_PICTURE = "/desa/add/picture"

        const val DETAIL_PROFILE = "/auth/findSku/{sku}"
        const val UPDATE_DETAIL_PROFILE = "/auth/update/{sku}"

        //admin
        const val ADMIN_LIST_AKUN_MERCHANT = "/adminDev/account/merchant"
        const val ADMIN_LIST_AKUN_CUSTOMER = "/adminDev/account/customer"
        const val ADMIN_LIST_AKUN_ADMIN = "/adminDev/account/admin"
        const val ADD_DATA_MERCHANT = "/adminDev/add"

        const val CONTENT_TYPE = "application/json"
        const val CODE_SUCCESS = 200


        //Penginapan
        const val ROUTE_ADD_PENGINAPAN = "/penginapan/add"
        const val ROUTE_PENGINAPAN_ALL = "/penginapan/"
        const val ROUTE_PENGINAPAN_MERCHANT = "/penginapan/bySkuAdmin/{sku}"
        const val ROUTE_PENGINAPAN_DELETE_BY_SKU = "/penginapan/delete/{sku}"
        const val ROUTE_PENGINAPAN_BY_SKU = "/penginapan/{sku}"
        const val ROUTE_UPDATE_PENGINAPAN_BY_SKU = "/penginapan/update/{sku}"
        const val ROUTE_ADD_PENGINAPAN_GAMBAR = "/penginapan/penginapan/add/gambar"
        const val ROUTE_ADD_PENGINAPAN_UPDATE_GAMBAR = "/penginapan/penginapan/update/gambar"


        //Produk
        const val ROUTE_ADD_PRODUK = "/produk/add"
        const val LIST_PRODUK = "/produk/"
        const val LIST_PRODUK_BY_SKU_ADMIN = "/produk/skuDesa/{sku}"
        const val LIST_PRODUK_BY_SKU_PRODUK = "/produk/sku/{sku}"
        const val DELETE_PRODUK_BY_SKU_PRODUK = "/produk/delete/{sku}"
        const val ROUTE_ADD_PRODUCT_GAMBAR = "/produk/add/gambar"
        const val ROUTE_UPDATE_PRODUCT_GAMBAR = "/produk/update/gambar"
        const val ROUTE_UPDATE_PRODUCT = "/produk/update/{sku}"

        //Pesanan
        const val LIST_PESANAN_CUSTOMER = "/transaksi/sku/pesan/{sku}"
        const val LIST_PESANAN_CUSTOMER_SUDAH_BAYAR = "/transaksi/sku/bayar/{sku}"
        const val LIST_PESANAN_ALL_BELUM_BAYAR = "/transaksi/pesanan"
        const val LIST_PESANAN_ALL_SUDAH_BAYAR = "/transaksi/pesanan/sudah"
        const val CANCEL_PESANAN = "/transaksi/pesanan/cancel/{sku}"


        //Cart
        const val LIST_CART_CUSTOMER = "/keranjang/customer/{sku}"
        const val ADD_TO_CART_CUSTOMER = "/keranjang/save"
        const val DELETE_TO_CART_CUSTOMER = "/keranjang/delete/{id}"
        const val CHECK_CART_CUSTOMER = "/keranjang/check"
        const val UPDATE_CART_CUSTOMER = "/keranjang/update"

        //Transaksi
        const val TRANSAKSI_ADD= "/transaksi/add"
        const val TRANSAKSI_PAYMENT= "/transaksi/bayar/{idPesanan}"

        //Customer
        const val ROUTE_GET_ALAMAT_CUSTOMER= "/customer/{sku}"
        const val ROUTE_SAVE_ALAMAT_CUSTOMER= "/customer/add"
        const val ROUTE_UPDATE_ALAMAT_CUSTOMER= "/customer/update/{sku}"
    }
}