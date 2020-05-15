package com.PortalDesa.data.apiService

import com.PortalDesa.data.model.request.*
import com.PortalDesa.data.model.response.*
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by Sogumontar Hendra Simangunsong on 13/04/2020.
 */
interface  ApiServices{

    //Sign in
    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE)
    @POST(ApiConfigs.SIGN_IN)
    fun doSignIn(@Body userRequest: UserRequest ): Call<UserResponse>

    //Sign up
    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE)
    @POST(ApiConfigs.SIGN_UP)
    fun doSignup(@Body signupRequest: SignupRequest): Call<SignupResponse>



    //List Kecamatan
    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE)
    @GET(ApiConfigs.LIST_KECAMATAN)
    fun getKecamatanList(): Call<List<KecamatanResponse>>

    //List Kecamatan
    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE)
    @GET(ApiConfigs.LIST_DESA_KECAMATAN)
    fun getDesaByKecamatan(@Path("kecamatan") kecamatan : String): Call<List<ListDesaKecamatanResponse>>

    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE)
    @GET(ApiConfigs.ADMIN_LIST_AKUN_ADMIN)
    fun getDaftarAkunAdminList(): Call<List<DaftarAkunResponse>>

    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE)
    @GET(ApiConfigs.ADMIN_LIST_AKUN_MERCHANT)
    fun getDaftarAkunMerchantList(): Call<List<DaftarAkunResponse>>

    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE)
    @GET(ApiConfigs.ADMIN_LIST_AKUN_CUSTOMER)
    fun getDaftarAkunCustomerList(): Call<List<DaftarAkunResponse>>

    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE)
    @GET(ApiConfigs.DETAIL_PROFILE)
    fun getDetailProfile(@Path("sku") sku : String): Call<ProfileResponse>

    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE)
    @PUT(ApiConfigs.UPDATE_DETAIL_PROFILE)
    fun updateProfileBySku(@Path("sku") sku : String, @Body usersUpdateRequest : UsersUpdateRequest): Call<UsersUpdateRequest>

    
    //Penginapan
    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE)
    @POST(ApiConfigs.ROUTE_ADD_PENGINAPAN)
    fun addPenginapan(@Body request : PenginapanRequest): Call<PenginapanRequest>

    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE)
    @GET(ApiConfigs.ROUTE_PENGINAPAN_ALL)
    fun lihatPenginapanAll(): Call<List<PenginapanResponse>>
    @POST(ApiConfigs.ROUTE_ADD_PENGINAPAN_GAMBAR)
    fun addPenginapanimage(@Body request : PenginapanImageRequest): Call<PenginapanImageResponse>


    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE)
    @GET(ApiConfigs.ROUTE_PENGINAPAN_MERCHANT)
    fun lihatPenginapanAllByMerchant(@Path("sku") sku : String): Call<List<PenginapanResponse>>

    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE)
    @GET(ApiConfigs.ROUTE_PENGINAPAN_BY_SKU)
    fun lihatPenginapanBySku(@Path("sku") sku : String): Call<PenginapanResponse>

    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE)
    @PUT(ApiConfigs.ROUTE_PENGINAPAN_DELETE_BY_SKU)
    fun deletePenginapan(@Path("sku") sku : String): Call<DefaultResponse>

    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE)
    @PUT(ApiConfigs.ROUTE_UPDATE_PENGINAPAN_BY_SKU)
    fun updatePenginapan(@Path("sku") sku : String, @Body penginapanRequest: PenginapanRequest): Call<DefaultResponse>



    //Produk
    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE)
    @GET(ApiConfigs.LIST_PRODUK)
    fun getProductList(): Call<List<ProductResponse>>

    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE)
    @GET(ApiConfigs.LIST_PRODUK_BY_SKU_ADMIN)
    fun getProductBySkuAdmin(@Path("sku")sku : String): Call<List<ProductResponse>>

    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE)
    @GET(ApiConfigs.LIST_PRODUK_BY_SKU_PRODUK)
    fun getProductBySku(@Path("sku")sku : String): Call<ProductResponse>

    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE)
    @POST(ApiConfigs.ROUTE_ADD_PRODUK)
    fun addProduk(@Body request : ProdukRequest): Call<ProdukRequest>

    //Keranjang
    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE)
    @GET(ApiConfigs.LIST_CART_CUSTOMER)
    fun cartCustomer(@Path("sku")sku : String): Call<List<KeranjangResponse>>

}