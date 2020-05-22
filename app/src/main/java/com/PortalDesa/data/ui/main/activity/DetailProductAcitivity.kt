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
import com.PortalDesa.data.support.*
import com.PortalDesa.data.ui.main.activity.Form.PemesananLangsung
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_product.*
import kotlinx.android.synthetic.main.activity_register_form.*
import retrofit2.Response
import java.util.*
import kotlin.concurrent.schedule

class DetailProductAcitivity : AppActivity() {
    private var productResponse: ProductResponse? = null
    lateinit var preferences: Preferences
    var role: String? = ""
    var skuLogin: String? = ""
    lateinit var topSnackBar: TopSnackBar
    var skuFix: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_product)
        preferences = Preferences(this)
        topSnackBar = TopSnackBar()
        role = preferences.getRoles()
        skuLogin = preferences.getSku()
        initView()
        initData();
//        tv_nama.text = name.toString()
        btn_keranjang.setOnClickListener() {
            addToCart()
        }
        btn_pesan.setOnClickListener {
            goToPemesananLangsung()
        }
        produk_delete_btn.setOnClickListener {
            delete()
        }
    }

    fun initData() {
        showProgressDialog()
        if (Connectivity().isNetworkAvailable(this)) {
            val client = APIServiceGenerator().createService
            skuFix = intent.getStringExtra(Flag.PRODUCT_NAME)
            val call = client.getProductBySku(intent.getStringExtra(Flag.PRODUCT_NAME))
            call.enqueue(object : retrofit2.Callback<ProductResponse> {
                override fun onResponse(
                    call: retrofit2.Call<ProductResponse>,
                    response: Response<ProductResponse>
                ) {
                    val listProduk = response.body()
                    productResponse = listProduk
                    displayProduct()
                    dismissProgressDialog()
                }

                override fun onFailure(call: retrofit2.Call<ProductResponse>, t: Throwable) {
                    dismissProgressDialog()
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
            btn_pesan.visibility = View.GONE
            btn_keranjang.visibility = View.GONE
        } else if (role.equals("ROLE_USER")) {
            produk_delete_btn.visibility = View.GONE
            produk_update_btn.visibility = View.GONE
            btn_pesan.visibility = View.VISIBLE
            btn_keranjang.visibility = View.VISIBLE
        } else {
            produk_delete_btn.visibility = View.GONE
            produk_update_btn.visibility = View.GONE
            btn_pesan.visibility = View.GONE
            btn_keranjang.visibility = View.GONE
        }
    }

    fun displayProduct() {
        Picasso.get()
            .load("https://portal-desa.herokuapp.com" + productResponse?.gambar)
            .into(img_icon)
        tv_nama.setText(productResponse?.nama)
        tv_harga.setText(Utils().numberToIDR(productResponse!!.harga!!.toInt(), true))
        tv_desc.setText(productResponse?.deskripsi)
    }

    fun alert() {
        Toast.makeText(this, "Produk sudah ada di dalam keranjang", Toast.LENGTH_SHORT).show()
        Timer("SettingUp", false).schedule(1000) {
            goToKeranjang()
        }

    }

    private fun showMessage(message: String) {
        topSnackBar.showError(this, findViewById(R.id.snackbar_container), message)
    }

    fun addToCart() {
        if (!FormValidation().required(et_jumlah.getText().toString())) {
            Toast.makeText(this, "Jumlah tidak boleh kosong", Toast.LENGTH_SHORT).show()
        } else {
            showProgressDialog()
            if (Connectivity().isNetworkAvailable(this)) {
                val client = APIServiceGenerator().createService
                val call = client.checkCart(getRequestCheck())
                call.enqueue(object : retrofit2.Callback<DefaultResponse> {
                    override fun onResponse(
                        call: retrofit2.Call<DefaultResponse>,
                        response: Response<DefaultResponse>
                    ) {
                        val cek = response.body()
                        if (cek?.status == 0) {
                            doCart()
                        } else {
                            alert()
                            doCart()
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
        keranjangRequest.jumlah = Integer.parseInt(et_jumlah.text.toString())
        keranjangRequest.skuDesa = productResponse?.skuDesa
        keranjangRequest.status = 1
        keranjangRequest.harga = Integer.parseInt(productResponse?.harga)

        return keranjangRequest
    }

    fun goToKeranjang() {
        intent = Intent(this, KeranjangActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun reload() {
        intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun delete() {
        if (Connectivity().isNetworkAvailable(this)) {
            val client = APIServiceGenerator().createService
            val call = client.deleteProduk(productResponse?.sku!!)
            call.enqueue(object : retrofit2.Callback<DefaultResponse> {
                override fun onResponse(
                    call: retrofit2.Call<DefaultResponse>,
                    response: Response<DefaultResponse>
                ) {
                    reload()
                }

                override fun onFailure(call: retrofit2.Call<DefaultResponse>, t: Throwable) {

                }
            })

        }

    }

    fun goToPemesananLangsung() {
        if (!FormValidation().required(et_jumlah.getText().toString())) {
            Toast.makeText(this, "Jumlah tidak boleh kosong", Toast.LENGTH_SHORT).show()
        } else {
            val intent = Intent(this, PemesananLangsung::class.java)
            intent.putExtra(Flag.SKU_PESANAN_PRODUK, skuFix)
            intent.putExtra(Flag.JUMLAH_PESANAN_PRODUK, et_jumlah.getText().toString())
            this.startActivity(intent)
        }
    }

//    private fun initView() {
//        initToolbar(R.id.toolbar)
//        tv_toolbar_title.text = getString(R.string.sign_up_tab_title_sign_up)
//    }
}
