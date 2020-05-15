package com.PortalDesa.data.ui.main.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.PortalDesa.R
import com.PortalDesa.data.apiService.APIServiceGenerator
import com.PortalDesa.data.base.AppActivity
import com.PortalDesa.data.model.response.ProductResponse
import com.PortalDesa.data.support.Connectivity
import com.PortalDesa.data.support.Flag
import com.PortalDesa.data.support.Preferences
import kotlinx.android.synthetic.main.activity_detail_product.*
import retrofit2.Response

class DetailProductAcitivity : AppActivity() {
    private var productResponse:ProductResponse? = null
    lateinit var preferences: Preferences
    var role: String?=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_product)
        preferences = Preferences(this)
        role=preferences.getRoles()
        val name = intent.getStringExtra(Flag.PRODUCT_NAME)
        initView()
        initData();
//        tv_nama.text = name.toString()
        keranjang.setOnClickListener() {
            addToCart()
            goToKeranjang()
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
                    productResponse= listProduk
                    displayProduct()
                }

                override fun onFailure(call: retrofit2.Call<ProductResponse>, t: Throwable) {
                    Log.i(this.javaClass.simpleName, " Requested API : " + call.request().body()!!)
                    Log.e(this.javaClass.simpleName, " Exceptions : $t")
                }
            })

        }
    }
    fun initView(){
        if(role.equals("ROLE_MERCHANT")){
            produk_delete_btn.visibility= View.VISIBLE
            produk_update_btn.visibility= View.VISIBLE
            pesan.visibility=View.GONE
            keranjang.visibility=View.GONE
        }else if(role.equals("ROLE_CUSTOMER")){
            produk_delete_btn.visibility= View.GONE
            produk_update_btn.visibility= View.GONE
            pesan.visibility=View.VISIBLE
            keranjang.visibility=View.VISIBLE
        }else{
            produk_delete_btn.visibility= View.GONE
            produk_update_btn.visibility= View.GONE
            pesan.visibility=View.GONE
            keranjang.visibility=View.GONE
        }
    }

    fun displayProduct(){
        tv_nama.setText(productResponse?.nama)
        tv_harga.setText("Rp."+productResponse?.harga)
        tv_desc.setText(productResponse?.deskripsi)
    }
    fun addToCart() {

    }

    fun goToKeranjang() {
        intent = Intent(this, KeranjangActivity::class.java)
        startActivity(intent)
    }

//    private fun initView() {
//        initToolbar(R.id.toolbar)
//        tv_toolbar_title.text = getString(R.string.sign_up_tab_title_sign_up)
//    }
}
