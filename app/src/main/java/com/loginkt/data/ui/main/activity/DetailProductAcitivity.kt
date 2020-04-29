package com.loginkt.data.ui.main.activity

import android.content.Intent
import android.os.Bundle
import com.loginkt.R
import com.loginkt.data.base.AppActivity
import kotlinx.android.synthetic.main.activity_register_form.*
import kotlinx.android.synthetic.main.toolbar.*

class DetailProductAcitivity : AppActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_product)

//        daftar.setOnClickListener(){
//            intent = Intent(this, RegisterActivity::class.java)
//            startActivity(intent)
//        }
//        initView()
    }

//    private fun initView() {
//        initToolbar(R.id.toolbar)
//        tv_toolbar_title.text = getString(R.string.sign_up_tab_title_sign_up)
//    }
}
