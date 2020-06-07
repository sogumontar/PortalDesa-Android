package com.PortalDesa.data.ui.main.activity

import android.content.Intent
import android.os.Bundle
import com.PortalDesa.R
import com.PortalDesa.data.base.AppActivity
import com.PortalDesa.data.support.Preferences
import com.PortalDesa.data.ui.main.activity.admin.MainActivityAdmin
import kotlinx.android.synthetic.main.content_main.*

class SplashActivity: AppActivity() {
    lateinit var preferences : Preferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        preferences = Preferences(this)

        if (preferences.getRoles().equals("ROLE_ADMIN")) {
            goToAdminMainActivity()
        }else{
            goToMainActivity()
        }
    }

    private fun goToAdminMainActivity() {
        val intent = Intent(this, MainActivityAdmin::class.java)
        startActivity(intent)
    }

    private fun goToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}