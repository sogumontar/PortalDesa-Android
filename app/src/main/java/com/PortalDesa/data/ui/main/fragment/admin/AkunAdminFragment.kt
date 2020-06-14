package com.PortalDesa.data.ui.main.fragment.admin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.PortalDesa.R
import com.PortalDesa.data.support.Preferences
import com.PortalDesa.data.ui.main.activity.ArtikelActivity
import com.PortalDesa.data.ui.main.activity.Form.DaftarAdminDesa
import com.PortalDesa.data.ui.main.activity.ProfileActivity
import com.PortalDesa.data.ui.main.activity.SignInActivity
import com.PortalDesa.data.ui.main.activity.admin.DaftarAkunActivity
import com.PortalDesa.data.ui.main.activity.admin.DaftarPesananActivity
import kotlinx.android.synthetic.main.activity_daftar_akun_fragment.*

class AkunAdminFragment : Fragment(), View.OnClickListener {
    lateinit private var preferences: Preferences

    companion object {

        fun newInstance(): AkunAdminFragment {
            return AkunAdminFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val preferences = this.activity!!.getSharedPreferences("Role", Context.MODE_PRIVATE)
        return inflater.inflate(R.layout.activity_daftar_akun_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val daftarAkunLayoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        preferences = Preferences(activity as Context)
        initView()
    }
    fun initView() {
        val tok = preferences.getAccessToken()
        admin_btn_logout.setOnClickListener(this)
        ln_daftar_akun.setOnClickListener(this)
        ln_daftar_desa.setOnClickListener(this)
        ln_pesanan.setOnClickListener(this)
        ln_artikel.setOnClickListener(this)
        ln_profil.setOnClickListener(this)
    }
    fun goToDaftarAkun() {
        val intent = Intent(activity, DaftarAkunActivity::class.java)
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
    fun goToDaftarkanMerchant(){
        val intent = Intent(activity, DaftarAdminDesa::class.java)
        startActivity(intent)
    }

    fun goToDaftarPesanan(){
        val intent = Intent(activity, DaftarPesananActivity::class.java)
        startActivity(intent)
    }

    fun goToArtikel(){
        val intent = Intent(activity, ArtikelActivity::class.java)
        startActivity(intent)
    }

    fun gotoProfile(){
        val intent = Intent(activity, ProfileActivity::class.java)
        startActivity(intent)
    }
    override fun onClick(v: View?) {
        when (v!!.id) {
            ln_daftar_akun.id -> goToDaftarAkun()
            admin_btn_logout.id -> doLogout()
            ln_daftar_desa.id -> goToDaftarkanMerchant()
            ln_pesanan.id -> goToDaftarPesanan()
            ln_artikel.id -> goToArtikel()
            ln_profil.id -> gotoProfile()
        }
    }

}
