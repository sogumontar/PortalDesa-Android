package com.loginkt.data.ui.main.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.loginkt.R
import com.loginkt.data.support.Preferences
import com.loginkt.data.ui.main.activity.LoginActivity
import com.loginkt.data.ui.main.activity.RegisterActivity
import com.loginkt.data.ui.main.adapter.ProfileAdapter
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
        val intent = Intent(activity, LoginActivity::class.java)
        startActivity(intent)
    }

    private fun goToRegister(){
        val intent = Intent(activity, RegisterActivity::class.java)
        startActivity(intent)
    }

    private fun doLogout(){
        preferences.clearToken()
        val intent = Intent(activity, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        activity!!.finish()
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            btn_login.id-> goToLogin()
            btn_register.id-> goToRegister()
            btn_logout.id-> doLogout()
        }
    }

    fun initView(){
        val tok = preferences.getAccessToken()
        btn_login.setOnClickListener(this)
        btn_register.setOnClickListener(this)
        btn_logout.setOnClickListener(this)
        if(!tok.equals("")){
            ln_signin.visibility = View.GONE
            btn_logout.visibility = View.VISIBLE
        }
    }


}