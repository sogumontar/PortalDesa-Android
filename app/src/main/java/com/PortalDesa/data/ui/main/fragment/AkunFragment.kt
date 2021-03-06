package com.PortalDesa.data.ui.main.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.PortalDesa.R
import com.PortalDesa.data.support.Flag
import com.PortalDesa.data.support.Preferences
import com.PortalDesa.data.ui.main.activity.*
import com.PortalDesa.data.ui.main.activity.Form.TambahArtikelActivity
import kotlinx.android.synthetic.main.fragment_akun.*
import kotlinx.android.synthetic.main.toolbar.*

class AkunFragment : Fragment(), View.OnClickListener {
    lateinit private var preferences: Preferences

    companion object {

        fun newInstance(): AkunFragment {
            return newInstance()
        }
    }

    //3
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        preferences = Preferences(activity as Context)
            return inflater.inflate(R.layout.fragment_akun, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferences = Preferences(activity as Context)
        initView()
    }
    fun goToPenginapan(){
        val intent = Intent(activity, ListPenginapanPerMerchantActivity::class.java)
        intent.putExtra(Flag.SKU_MERCHANT,preferences.getSku())
        startActivity(intent)
    }
    fun goToProduk(){
        val intent = Intent(activity, ListProdukPerMerchantActivity::class.java)
        intent.putExtra(Flag.SKU_Penginapan_MERCHANT,preferences.getSku() )
        startActivity(intent)
    }

    private fun goToLogin() {
        val intent = Intent(activity, SignInActivity::class.java)
        startActivity(intent)
    }

    private fun goToRegister() {
        val intent = Intent(activity, SignUpActivity::class.java)
        startActivity(intent)
    }

    private fun goProfile() {
        val intent = Intent(activity, ProfileActivity::class.java)
        startActivity(intent)
    }

    private fun goToKeranjang() {
        val intent = Intent(activity, KeranjangActivity::class.java)
        startActivity(intent)
    }

    private fun doLogout() {
        preferences.clearToken()
        preferences.clearAll()
        val intent = Intent(activity, SignInActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        activity!!.finish()
    }

    fun goToPesanan() {
        val intent = Intent(activity, PesananActivity::class.java)
        startActivity(intent)
    }

    fun goArtikel() {
        val intent = Intent(activity, ArtikelActivity::class.java)
        startActivity(intent)
    }

    fun goArtikelForm() {
        val intent = Intent(activity, TambahArtikelActivity::class.java)
        startActivity(intent)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            btn_login.id -> goToLogin()
            btn_register.id -> goToRegister()
            btn_logout.id -> doLogout()
            ln_profile.id -> goProfile()
            ln_keranjang.id -> goToKeranjang()
            ln_pesanan.id -> goToPesanan()
            ln_artikel.id -> goArtikel()
            ln_tambah_artikel.id -> goArtikelForm()
            ln_penginapan_saya.id -> goToPenginapan()
            ln_produk_saya.id -> goToProduk()
        }
    }

    fun initView() {
        tv_toolbar_title.text = "Profil"
        btn_login.setOnClickListener(this)
        btn_register.setOnClickListener(this)
        btn_logout.setOnClickListener(this)
        ln_profile.setOnClickListener(this)
        ln_keranjang.setOnClickListener(this)
        ln_pesanan.setOnClickListener(this)
        ln_artikel.setOnClickListener(this)
        ln_tambah_artikel.setOnClickListener(this)
        ln_penginapan_saya.setOnClickListener(this)
        ln_produk_saya.setOnClickListener(this)
        if(!preferences.getAccessToken().equals("")) {
            ln_data_akun.visibility = View.VISIBLE
            tv_name.text = preferences.getUserDetail()!!.nickName
            tv_email.text = preferences.getUserDetail()!!.email
            if (preferences.getRoles().equals("ROLE_USER")) {
                ln_signin.visibility = View.GONE
                btn_logout.visibility = View.VISIBLE
                ln_profile.visibility = View.VISIBLE
                ln_keranjang.visibility = View.VISIBLE
                ln_pesanan.visibility = View.VISIBLE
                ln_produk_saya.visibility=View.GONE
                ln_penginapan_saya.visibility=View.GONE
            } else if (preferences.getRoles().equals("ROLE_MERCHANT")) {
                ln_signin.visibility = View.GONE
                btn_logout.visibility = View.VISIBLE
                ln_profile.visibility = View.VISIBLE
                ln_keranjang.visibility = View.VISIBLE
                ln_pesanan.visibility = View.VISIBLE
                ln_tambah_artikel.visibility = View.VISIBLE
            }
        }else{
            ln_data_akun.visibility = View.GONE
        }
    }


}