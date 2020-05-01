package com.loginkt.data.ui.main.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.loginkt.R
import com.loginkt.data.base.AppActivity
import kotlinx.android.synthetic.main.activity_register_form.*
import kotlinx.android.synthetic.main.toolbar.*

class RegisterActivity : AppActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_form)
        initView()
    }

    private fun initView() {
        initToolbar(R.id.toolbar)
        tv_toolbar_title.text = getString(R.string.sign_up_tab_title_sign_up)
    }

    private fun goToHome(){
        intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    override fun onClick(v: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        when(v!!.id){
            daftar.id -> goToHome()
        }
    }
}
