package com.PortalDesa.data.ui.main.fragment

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.PortalDesa.R
import com.PortalDesa.data.base.AppActivity
import com.PortalDesa.data.support.Preferences
import com.PortalDesa.data.ui.main.activity.KeranjangActivity
import kotlinx.android.synthetic.main.activity_akun_fragment_after_login.*

class AkunFragmentAfterLogin : AppActivity(), View.OnClickListener {
    lateinit private var preferences : Preferences
    override fun onCreate(savedInstanceState: Bundle?) {
        preferences = Preferences(this as Context)
        super.onCreate(savedInstanceState)
        initView()
        setContentView(R.layout.activity_akun_fragment_after_login)
    }
    fun initView(){
        if(preferences.getRoles().equals("ROLE_MERCHANT")){
            btn_keranjang.visibility=View.GONE
            btn_register.visibility=View.GONE
        }else{
            btn_produk.visibility=View.GONE
            btn_penginapan.visibility=View.GONE
        }

    }

    fun goToKeranjang(){
        val intent = Intent(this, KeranjangActivity::class.java)
        startActivity(intent)
    }
    override fun onClick(v: View?) {
        when(v!!.id){
            btn_keranjang.id->goToKeranjang()
        }
    }


}
