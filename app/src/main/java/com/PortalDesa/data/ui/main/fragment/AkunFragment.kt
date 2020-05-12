package com.PortalDesa.data.ui.main.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.PortalDesa.R
import com.PortalDesa.data.support.Preferences
import com.PortalDesa.data.ui.main.activity.ProfileActivity
import com.PortalDesa.data.ui.main.activity.SignInActivity
import com.PortalDesa.data.ui.main.activity.SignUpActivity
import kotlinx.android.synthetic.main.fragment_akun.*

class AkunFragment : Fragment(), View.OnClickListener {
    lateinit private var preferences : Preferences

    companion object {

        fun newInstance(): AkunFragment {
            return newInstance()
        }
    }
    //3
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val preferences =
            this.activity!!.getSharedPreferences("Role", Context.MODE_PRIVATE)
        if(preferences.equals("ROLE_ADMIN")){
            return inflater.inflate(R.layout.activity_akun_fragment_after_login, container, false)
        }else{
            return inflater.inflate(R.layout.fragment_akun, container, false)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferences = Preferences(activity as Context)
        initView()
    }

    private fun goToLogin(){
        val intent = Intent(activity, SignInActivity::class.java)
        startActivity(intent)
    }

    private fun goToRegister(){
        val intent = Intent(activity, SignUpActivity::class.java)
        startActivity(intent)
    }

    private fun goProfile(){
        val intent = Intent(activity, ProfileActivity::class.java)
        startActivity(intent)
    }

    private fun doLogout(){
        preferences.clearToken()
        val intent = Intent(activity, SignInActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        activity!!.finish()
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            btn_login.id-> goToLogin()
            btn_register.id-> goToRegister()
            btn_logout.id-> doLogout()
            btn_profile.id-> goProfile()
        }
    }

    fun initView(){
        val tok = preferences.getAccessToken()
        btn_login.setOnClickListener(this)
        btn_logout.setOnClickListener(this)
        btn_profile.setOnClickListener(this)
        btn_pesanan.setOnClickListener(this)
        if(!tok.equals("")){
            ln_signin.visibility = View.GONE
            btn_logout.visibility = View.VISIBLE
            btn_profile.visibility=View.VISIBLE
            btn_pesanan.visibility=View.VISIBLE
        }else{
            val intent = Intent(activity, SignInActivity::class.java)
            startActivity(intent)
        }
    }


}