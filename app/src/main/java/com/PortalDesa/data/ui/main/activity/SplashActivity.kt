package com.PortalDesa.data.ui.main.activity

import android.R.id.home
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.PortalDesa.R
import com.PortalDesa.data.base.AppActivity
import com.PortalDesa.data.support.Preferences
import com.PortalDesa.data.ui.main.activity.admin.MainActivityAdmin


class SplashActivity: AppActivity() {
    lateinit var preferences : Preferences
    override fun onCreate(savedInstanceState: Bundle?) {
        Handler().postDelayed(Runnable { //setelah loading maka akan langsung berpindah ke home activity
            if (preferences.getRoles().equals("ROLE_ADMIN")) {
                goToAdminMainActivity()
            }else{
                goToMainActivity()
            }
            finish()
        }, 3000)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        preferences = Preferences(this)

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