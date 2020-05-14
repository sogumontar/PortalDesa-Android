package com.PortalDesa.data.ui.main.activity

import android.content.Intent
import android.os.Bundle
import com.PortalDesa.R
import com.PortalDesa.data.base.AppActivity
import com.PortalDesa.data.support.Flag
import kotlinx.android.synthetic.main.activity_detail_product.*

class DetailProductAcitivity : AppActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_product)

        val name = intent.getStringExtra(Flag.PRODUCT_NAME)
        tv_nama.text = name.toString()
        keranjang.setOnClickListener(){
            addToCart()
            goToKeranjang()
        }
//        initView()
    }

    fun addToCart(){

    }
    fun goToKeranjang(){
        intent = Intent(this, KeranjangActivity::class.java)
        startActivity(intent)
    }

//    private fun initView() {
//        initToolbar(R.id.toolbar)
//        tv_toolbar_title.text = getString(R.string.sign_up_tab_title_sign_up)
//    }
}
