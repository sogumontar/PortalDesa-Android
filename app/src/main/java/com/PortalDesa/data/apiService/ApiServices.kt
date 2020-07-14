package com.PortalDesa.data.apiService

import com.PortalDesa.data.model.request.*
import com.PortalDesa.data.model.response.*
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by Sogumontar Hendra Simangunsong on 13/04/2020.
 */
interface ApiServices {

    //Admin
    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE
    )
    @POST(ApiConfigs.ADD_DATA_MERCHANT)
    fun createDataMerchant(@Body userRequest: DaftarAdminDesaRequest): Call<DefaultResponse>


    //Desa
    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE
    )
    @PUT(ApiConfigs.ADD_DESA_PICTURE)
    fun addDesaimage(@Body request: GambarDesaRequest): Call<PenginapanImageResponse>


    //Account
    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE
    )
    @GET(ApiConfigs.SUSPEND_ACCOUNT)
    fun suspendAccount(@Path("sku") sku: String): Call<DefaultResponse>

    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE
    )
    @GET(ApiConfigs.ACTIVATE_ACCOUNT)
    fun activateAccount(@Path("sku") sku: String): Call<DefaultResponse>


    //Sign in
    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE
    )
    @POST(ApiConfigs.SIGN_IN)
    fun doSignIn(@Body userRequest: UserRequest): Call<UserResponse>

    //Sign up
    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE
    )
    @POST(ApiConfigs.SIGN_UP)
    fun doSignup(@Body signupRequest: SignupRequest): Call<SignupResponse>


    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE
    )
    @POST(ApiConfigs.CHECK_CODE)
    fun checkVerificationCode(@Body verificationCodeRequest: VerificationCodeRequest): Call<DefaultResponse>


    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE
    )
    @PUT(ApiConfigs.GANTI_PASSWORD)
    fun gantiPassword(@Path("sku") sku: String,@Body gantiPasswordRequest: GantiPasswordRequest): Call<DefaultResponse>


    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE
    )
    @GET(ApiConfigs.LUPA_PASSWORD)
    fun lupaPassword(@Path("username") username: String): Call<DefaultResponse>


    //List Kecamatan
    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE
    )
    @GET(ApiConfigs.LIST_KECAMATAN)
    fun getKecamatanList(): Call<List<KecamatanResponse>>

    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE
    )
    @POST(ApiConfigs.LIST_KECAMATAN_ADD)
    fun addKecamatan(@Body request: KecamatanRequest): Call<DefaultResponse>

    //List Kecamatan
    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE
    )
    @GET(ApiConfigs.LIST_DESA_KECAMATAN)
    fun getDesaByKecamatan(@Path("kecamatan") kecamatan: String): Call<List<ListDesaKecamatanResponse>>

    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE
    )
    @GET(ApiConfigs.ADMIN_LIST_AKUN_ADMIN)
    fun getDaftarAkunAdminList(): Call<List<DaftarAkunResponse>>

    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE
    )
    @GET(ApiConfigs.ADMIN_LIST_AKUN_MERCHANT)
    fun getDaftarAkunMerchantList(): Call<List<DaftarAkunResponse>>

    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE
    )
    @GET(ApiConfigs.ADMIN_LIST_AKUN_CUSTOMER)
    fun getDaftarAkunCustomerList(): Call<List<DaftarAkunResponse>>

    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE
    )
    @GET(ApiConfigs.DETAIL_PROFILE)
    fun getDetailProfile(@Path("sku") sku: String): Call<ProfileResponse>

    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE
    )
    @PUT(ApiConfigs.UPDATE_DETAIL_PROFILE)
    fun updateProfileBySku(
        @Path("sku") sku: String,
        @Body usersUpdateRequest: UsersUpdateRequest
    ): Call<UsersUpdateRequest>


    //Penginapan
    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE
    )
    @POST(ApiConfigs.ROUTE_ADD_PENGINAPAN)
    fun addPenginapan(@Body request: PenginapanRequest): Call<PenginapanRequest>

    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE
    )
    @GET(ApiConfigs.ROUTE_PENGINAPAN_ALL)
    fun lihatPenginapanAll(): Call<List<PenginapanResponse>>

    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE
    )
    @POST(ApiConfigs.ROUTE_ADD_PENGINAPAN_GAMBAR)
    fun addPenginapanimage(@Body request: PenginapanImageRequest): Call<PenginapanImageResponse>

    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE
    )
    @POST(ApiConfigs.ROUTE_ADD_PENGINAPAN_UPDATE_GAMBAR)
    fun updatePenginapanimage(@Body request: PenginapanImageRequest): Call<DefaultResponse>


    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE
    )
    @GET(ApiConfigs.ROUTE_PENGINAPAN_MERCHANT)
    fun lihatPenginapanAllByMerchant(@Path("sku") sku: String): Call<List<PenginapanResponse>>

    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE
    )
    @GET(ApiConfigs.ROUTE_PENGINAPAN_BY_SKU)
    fun lihatPenginapanBySku(@Path("sku") sku: String): Call<PenginapanResponse>

    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE
    )
    @PUT(ApiConfigs.ROUTE_PENGINAPAN_DELETE_BY_SKU)
    fun deletePenginapan(@Path("sku") sku: String): Call<DefaultResponse>

    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE
    )
    @PUT(ApiConfigs.ROUTE_UPDATE_PENGINAPAN_BY_SKU)
    fun updatePenginapan(
        @Path("sku") sku: String,
        @Body penginapanRequest: PenginapanRequest
    ): Call<DefaultResponse>


    //Produk

    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE
    )
    @GET(ApiConfigs.LIST_PRODUK)
    fun getProductList(): Call<List<ProductResponse>>

    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE
    )
    @GET(ApiConfigs.LIST_PRODUK_ASC)
    fun getProductListASC(): Call<List<ProductResponse>>

    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE
    )
    @GET(ApiConfigs.LIST_PRODUK_DESC)
    fun getProductListDESC(): Call<List<ProductResponse>>

    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE
    )
    @GET(ApiConfigs.ROUTE_POPULAR_PRODUK)
    fun getPopularProduct(): Call<ProductResponse>


    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE
    )
    @GET(ApiConfigs.LIST_PRODUK_BY_SKU_ADMIN)
    fun getProductBySkuAdmin(@Path("sku") sku: String): Call<List<ProductResponse>>

    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE
    )
    @GET(ApiConfigs.LIST_PRODUK_BY_SKU_PRODUK)
    fun getProductBySku(@Path("sku") sku: String): Call<ProductResponse>

    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE
    )
    @POST(ApiConfigs.ROUTE_ADD_PRODUK)
    fun addProduk(@Body request: ProdukRequest): Call<ProdukRequest>

    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE
    )
    @PUT(ApiConfigs.DELETE_PRODUK_BY_SKU_PRODUK)
    fun deleteProduk(@Path("sku") sku: String): Call<DefaultResponse>

    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE
    )
    @POST(ApiConfigs.ROUTE_ADD_PRODUCT_GAMBAR)
    fun addProdukimage(@Body request: PenginapanImageRequest): Call<PenginapanImageResponse>

    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE
    )
    @PUT(ApiConfigs.ROUTE_UPDATE_PRODUCT)
    fun updateProduk(
        @Path("sku") sku: String,
        @Body produkRequest: ProdukRequest
    ): Call<DefaultResponse>

    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE
    )
    @POST(ApiConfigs.ROUTE_UPDATE_PRODUCT_GAMBAR)
    fun updateprodukImage(@Body request: PenginapanImageRequest): Call<DefaultResponse>


    //Keranjang
    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE
    )
    @GET(ApiConfigs.LIST_CART_CUSTOMER)
    fun cartCustomer(@Path("sku") sku: String): Call<List<KeranjangResponse>>

    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE
    )
    @POST(ApiConfigs.ADD_TO_CART_CUSTOMER)
    fun addToCart(@Body request: KeranjangRequest): Call<DefaultResponse>

    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE
    )
    @DELETE(ApiConfigs.DELETE_TO_CART_CUSTOMER)
    fun deleteCart(@Path("id") id: String): Call<DefaultResponse>

    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE
    )
    @POST(ApiConfigs.CHECK_CART_CUSTOMER)
    fun checkCart(@Body request: KeranjangRequestCheck): Call<DefaultResponse>

    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE
    )
    @PUT(ApiConfigs.UPDATE_CART_CUSTOMER)
    fun updateCart(@Body request: KeranjangUpdateRequest): Call<DefaultResponse>



    //Artikel
    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE
    )
    @GET(ApiConfigs.ROUTE_ARTIKEL_GET_ALL)
    fun getArtikel(): Call<List<ArtikelResponse>>

    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE
    )
    @GET(ApiConfigs.ROUTE_ARTIKELS_GET_ALL)
    fun getArtikelAll(): Call<List<ArtikelResponse>>

    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE
    )
    @GET(ApiConfigs.ROUTE_BERITA_GET_ALL)
    fun getBeritaAll(): Call<List<ArtikelResponse>>

    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE
    )
    @GET(ApiConfigs.ROUTE_PENGUMUMAN_GET_ALL)
    fun getPengumumanAll(): Call<List<ArtikelResponse>>

    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE
    )
    @DELETE(ApiConfigs.ROUTE_DELETE_ARTIKEL)
    fun deleteArtikel(@Path("id")id:String): Call<DefaultResponse>

    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE
    )
    @GET(ApiConfigs.DETAIL_ARTIKEL)
    fun getDetailArtikel(@Path("id") sku: String): Call<ArtikelResponse>

    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE
    )
    @GET(ApiConfigs.ROUTE_ARTIKEL_GET_ALL_ARTIKEL_BY_SKU)
    fun getArtikelAllBySku(@Path("sku")sku:String): Call<List<ArtikelResponse>>

    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE
    )
    @GET(ApiConfigs.ROUTE_ARTIKEL_GET_ALL_BERITA_BY_SKU)
    fun getBeritaAllBySku(@Path("sku")sku:String): Call<List<ArtikelResponse>>

    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE
    )
    @GET(ApiConfigs.ROUTE_ARTIKEL_GET_ALL_BY_SKU)
    fun getPengumumanAllBySku(@Path("sku")sku:String): Call<List<ArtikelResponse>>

    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE
    )
    @POST(ApiConfigs.ROUTE_ARTIKEL_ADD)
    fun createArtikel(@Path("sku") sku:String , @Body artikelRequest: ArtikelRequest): Call<DefaultResponse>

    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE
    )
    @PUT(ApiConfigs.ROUTE_ARTIKEL_UPDATE)
    fun updateArtikel(@Path("id") sku:String , @Body artikelRequest: ArtikelRequest): Call<DefaultResponse>



    //Pesanan
    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE
    )
    @GET(ApiConfigs.LIST_PESANAN_CUSTOMER)
    fun getPesanan(@Path("sku") sku: String): Call<List<PesananResponse>>

    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE
    )
    @GET(ApiConfigs.LIST_PESANAN_PENGINAPAN_CUSTOMER)
    fun getPesananPenginapanBelumbayar(@Path("sku") sku: String): Call<List<TransaksiPenginapanResponse>>

    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE
    )
    @GET(ApiConfigs.LIST_PESANAN_PENGINAPAN_SUDAH_BAYAR_CUSTOMER)
    fun getPesananPenginapanSudahBayar(@Path("sku") sku: String): Call<List<TransaksiPenginapanResponse>>

    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE
    )
    @GET(ApiConfigs.LIST_PESANAN_CUSTOMER_SUDAH_BAYAR)
    fun getPesananSudahBayar(@Path("sku") sku: String): Call<List<PesananResponse>>

    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE
    )
    @GET(ApiConfigs.LIST_PESANAN_ALL_BELUM_BAYAR)
    fun getPesananAll(): Call<List<PesananResponse>>

    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE
    )
    @GET(ApiConfigs.LIST_PESANAN_PENGINAPAN_ALL_BELUM_BAYAR)
    fun getPesananPenginapanAll(): Call<List<TransaksiPenginapanResponse>>

    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE
    )
    @GET(ApiConfigs.LIST_PESANAN_PENGINAPAN_ALL_SUDAH_BAYAR)
    fun getPesananPenginapanSudahBayarAll(): Call<List<TransaksiPenginapanResponse>>

    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE
    )
    @GET(ApiConfigs.LIST_PESANAN_ALL_SUDAH_BAYAR)
    fun getPesananAllSudahBayar(): Call<List<PesananResponse>>

    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE
    )
    @GET(ApiConfigs.CANCEL_PESANAN)
    fun cancelPesanan(@Path("sku") sku: String): Call<DefaultResponse>


    //Transaksi
    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE
    )
    @POST(ApiConfigs.TRANSAKSI_ADD)
    fun addTransaction(@Body request: TransaksiRequest): Call<DefaultResponse>

    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE
    )
    @GET(ApiConfigs.ROUTE_TRANSAKSI_CANCEL_PESANAN)
    fun cancelPesananPenginapan(@Path("sku") sku: String): Call<DefaultResponse>

    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE
    )
    @GET(ApiConfigs.TOLAK_TRANSAKSI_PRODUK)
    fun tolakPesanan(@Path("idPesanan") sku: String): Call<DefaultResponse>

    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE
    )
    @GET(ApiConfigs.TERIMA_TRANSAKSI_PRODUK)
    fun terimaPesanan(@Path("idPesanan") sku: String): Call<DefaultResponse>

    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE
    )
    @GET(ApiConfigs.TERIMA_TRANSAKSI_PENGINAPAN)
    fun terimaPenginapanPesanan(@Path("idPesanan") sku: String): Call<DefaultResponse>

    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE
    )
    @GET(ApiConfigs.TOLAK_TRANSAKSI_PENGINAPAN)
    fun tolakPenginapanPesanan(@Path("idPesanan") sku: String): Call<DefaultResponse>



    //Customer
    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE
    )
    @GET(ApiConfigs.ROUTE_GET_ALAMAT_CUSTOMER)
    fun getAlamatCustomer(@Path("sku") sku: String): Call<CustomerResponse>

    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE
    )
    @POST(ApiConfigs.ROUTE_SAVE_ALAMAT_CUSTOMER)
    fun saveAlamatCustomer(@Body request: CustomerRequest): Call<DefaultResponse>

    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE
    )
    @PUT(ApiConfigs.ROUTE_UPDATE_ALAMAT_CUSTOMER)
    fun updateAlamatCustomer(
        @Path("sku") sku: String,
        @Body request: CustomerRequest
    ): Call<DefaultResponse>

    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE
    )
    @PUT(ApiConfigs.TRANSAKSI_PAYMENT)
    fun bayar( @Path("idPesanan") sku: String,@Body request: TransaksiRequest): Call<DefaultResponse>

    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE
    )

    @PUT(ApiConfigs.TRANSAKSI_PENGINAPAN_PAYMENT)
    fun bayarPenginapan( @Path("idPesanan") sku: String,@Body request: TransaksiRequest): Call<DefaultResponse>

    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE
    )
    @POST(ApiConfigs.ADD_TRANSAKSI_PENGINAPAN)
    fun addTransaksiPenginapan(@Body request: TransaksiPenginapanRequest): Call<DefaultResponse>


    //Desa
    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE
    )
    @GET(ApiConfigs.ROUTE_GET_DESA_BY_NAMA)
    fun getDesaByNama(@Path("nama") nama: String): Call<DesaResponse>

    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE
    )
    @PUT(ApiConfigs.ROUTE_UPDATE_DESA_BY_SKU)
    fun updateDesaBySku(
        @Path("sku") sku: String,
        @Body request: UpdateDesaRequest
    ): Call<DefaultResponse>
}