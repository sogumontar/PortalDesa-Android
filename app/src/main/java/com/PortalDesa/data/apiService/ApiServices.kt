package com.PortalDesa.data.apiService

import com.PortalDesa.data.model.request.PenginapanRequest
import com.PortalDesa.data.model.request.SignupRequest
import com.PortalDesa.data.model.request.UserRequest
import com.PortalDesa.data.model.request.UsersUpdateRequest
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

    //List Product
    @Headers(
        "Content-Type:" + ApiConfigs.CONTENT_TYPE)
    @GET(ApiConfigs.LIST_PRODUK)
    fun getProductList(): Call<List<ProductResponse>>

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

    @Headers(
        "Content-Type:" + ApiConfigs.ROUTE_ADD_PENGINAPAN)
    @POST(ApiConfigs.UPDATE_DETAIL_PROFILE)
    fun addPenginapan(@Body request : PenginapanRequest): Call<PenginapanRequest>









}