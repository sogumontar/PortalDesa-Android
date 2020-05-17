package com.PortalDesa.data.ui.main.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.PortalDesa.R
import com.PortalDesa.data.apiService.APIServiceGenerator
import com.PortalDesa.data.base.AppActivity
import com.PortalDesa.data.model.request.KeranjangRequest
import com.PortalDesa.data.model.request.KeranjangRequestCheck
import com.PortalDesa.data.model.response.DefaultResponse
import com.PortalDesa.data.model.response.ProductResponse
import com.PortalDesa.data.support.Connectivity
import com.PortalDesa.data.support.Flag
import com.PortalDesa.data.support.Preferences
import kotlinx.android.synthetic.main.activity_detail_product.*
import retrofit2.Response
import java.util.*
import kotlin.concurrent.schedule

class DetailProductAcitivity : AppActivity() {
    private var productResponse: ProductResponse? = null
    lateinit var preferences: Preferences
    var role: String? = ""
    var skuLogin: String? = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_product)
        preferences = Preferences(this)
        role = preferences.getRoles()
        skuLogin = preferences.getSku()
        val name = intent.getStringExtra(Flag.PRODUCT_NAME)
        initView()
        initData();
//        tv_nama.text = name.toString()
        produk_delete_btn.setOnClickListener(){
            showProgressDialog()
            deleteProduk()
        }

        keranjang.setOnClickListener() {
            showProgressDialog()
            addToCart()
        }
    }

    fun deleteProduk(){
        if (Connectivity().isNetworkAvailable(this)) {
            val client = APIServiceGenerator().createService
            val call = client.deleteProduk(intent.getStringExtra(Flag.PRODUCT_NAME))
            call.enqueue(object : retrofit2.Callback<DefaultResponse> {
                override fun onResponse(
                    call: retrofit2.Call<DefaultResponse>,
                    response: Response<DefaultResponse>
                ) {
                    val listProduk = response.body()
                    goToHome()
                }

                override fun onFailure(call: retrofit2.Call<DefaultResponse>, t: Throwable) {
                    Log.i(this.javaClass.simpleName, " Requested API : " + call.request().body()!!)
                    Log.e(this.javaClass.simpleName, " Exceptions : $t")
                }
            })

        }
    }
    fun initData() {
        if (Connectivity().isNetworkAvailable(this)) {
            val client = APIServiceGenerator().createService
            val call = client.getProductBySku(intent.getStringExtra(Flag.PRODUCT_NAME))
            call.enqueue(object : retrofit2.Callback<ProductResponse> {
                override fun onResponse(
                    call: retrofit2.Call<ProductResponse>,
                    response: Response<ProductResponse>
                ) {
                    val listProduk = response.body()
                    productResponse = listProduk
                    displayProduct()
                }

                override fun onFailure(call: retrofit2.Call<ProductResponse>, t: Throwable) {
                    Log.i(this.javaClass.simpleName, " Requested API : " + call.request().body()!!)
                    Log.e(this.javaClass.simpleName, " Exceptions : $t")
                }
            })

        }
    }

    fun initView() {
        if (role.equals("ROLE_MERCHANT")) {
            produk_delete_btn.visibility = View.VISIBLE
            produk_update_btn.visibility = View.VISIBLE
            lin_jumlah.visibility=View.GONE
            pesan.visibility = View.GONE
            keranjang.visibility = View.GONE
        } else if (role.equals("ROLE_USER")) {
            lin_jumlah.visibility=View.VISIBLE
            produk_delete_btn.visibility = View.GONE
            produk_update_btn.visibility = View.GONE
            pesan.visibility = View.VISIBLE
            keranjang.visibility = View.VISIBLE
        } else {
            produk_delete_btn.visibility = View.GONE
            produk_update_btn.visibility = View.GONE
            pesan.visibility = View.GONE
            keranjang.visibility = View.GONE
        }
    }

    fun displayProduct() {
        tv_nama.setText(productResponse?.nama)
        tv_harga.setText("Rp." + productResponse?.harga)
        tv_desc.setText(productResponse?.deskripsi)
    }

    fun alert() {
        Toast.makeText(this, "Produk sudah ada di dalam keranjang", Toast.LENGTH_SHORT).show()
        Timer("SettingUp", false).schedule(1000) {
            goToKeranjang()
        }

    }

    fun addToCart() {
        val a = Jumlah.text.toString()
        if (a.equals("")) {
            Toast.makeText(this, "Masukkan Jumlah Pesanan", Toast.LENGTH_SHORT).show()
        } else {
            if (Connectivity().isNetworkAvailable(this)) {
                val client = APIServiceGenerator().createService
                val call = client.checkCart(getRequestCheck())
                call.enqueue(object : retrofit2.Callback<DefaultResponse> {
                    override fun onResponse(
                        call: retrofit2.Call<DefaultResponse>,
                        response: Response<DefaultResponse>
                    ) {
                        val cek = response.body()
                        val status = cek!!.status
                        val messages = response.body()?.message
                        if (cek?.status == 0) {
                            doCart()
                        } else {
                            alert()
                        }
                    }

                    override fun onFailure(call: retrofit2.Call<DefaultResponse>, t: Throwable) {
                        Log.i(
                            this.javaClass.simpleName,
                            " Requested API : " + call.request().body()!!
                        )
                        Log.e(this.javaClass.simpleName, " Exceptions : $t")
                    }
                })

            }

        }
    }

    fun doCart() {
        if (Connectivity().isNetworkAvailable(this)) {
            val client = APIServiceGenerator().createService
            val call = client.addToCart(getRequest())
            call.enqueue(object : retrofit2.Callback<DefaultResponse> {
                override fun onResponse(
                    call: retrofit2.Call<DefaultResponse>,
                    response: Response<DefaultResponse>
                ) {
                    goToKeranjang()
                }

                override fun onFailure(call: retrofit2.Call<DefaultResponse>, t: Throwable) {
                    Log.i(
                        this.javaClass.simpleName,
                        " Requested API : " + call.request().body()!!
                    )
                    Log.e(this.javaClass.simpleName, " Exceptions : $t")
                }
            })

        }
    }

    fun getRequestCheck(): KeranjangRequestCheck {
        val keranjangRequest = KeranjangRequestCheck()
        val idProduk = productResponse?.sku
        val idCustomer = skuLogin
        keranjangRequest.skuCustomer = skuLogin
        keranjangRequest.idProduk = idProduk
        return keranjangRequest
    }

    fun getRequest(): KeranjangRequest {
        val keranjangRequest = KeranjangRequest()
        val idProduk = productResponse?.sku
        val idCustomer = skuLogin
        keranjangRequest.id = productResponse?.sku
        keranjangRequest.idCustomer = skuLogin
        keranjangRequest.idProduk = idProduk
        keranjangRequest.jumlah = Integer.parseInt(Jumlah.text.toString())
        keranjangRequest.skuDesa = productResponse?.skuDesa
        keranjangRequest.status = 1
        keranjangRequest.harga = Integer.parseInt(productResponse?.harga)

        return keranjangRequest
    }

    fun goToKeranjang() {
        intent = Intent(this, KeranjangActivity::class.java)
        startActivity(intent)
    }

    fun goToHome() {
        intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
//    private fun initView() {
//        initToolbar(R.id.toolbar)
//        tv_toolbar_title.text = getString(R.string.sign_up_tab_title_sign_up)
//    }
}
