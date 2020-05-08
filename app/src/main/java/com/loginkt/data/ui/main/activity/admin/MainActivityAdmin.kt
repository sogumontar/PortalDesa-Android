package com.loginkt.data.ui.main.activity.admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.loginkt.R
import com.loginkt.data.base.AppActivity
import com.loginkt.data.ui.main.fragment.admin.DaftarAkunFragment
import com.loginkt.data.ui.main.fragment.admin.DaftarDesa
import com.loginkt.data.ui.main.fragment.admin.DaftarPesanan
import kotlinx.android.synthetic.main.content_main_admin.*

class MainActivityAdmin : AppActivity(), View.OnClickListener {
    private var mTabPosition = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_admin)
        tabSelected(2, "Akun")
    }

    override fun onClick(v: View) {
        val id = v.id
//        if (id == admin_tab_pesanan.getId()) {
//            tabSelected(0, "Pesanan")
//        } else if (id == admin_tab_desa.getId()) {
//            tabSelected(1, "Desa")
//        } else if (id == admin_tab_akun.getId()) {
            tabSelected(2, "Akun")
//        }
    }
    fun tabSelected(position: Int, type: String) {
        if (!TextUtils.isEmpty(type)) {

            when (type) {
//                "Pesanan" -> displayFragment(DaftarPesanan(), R.id.admin_fragment_container)
//                "Desa" -> displayFragment(DaftarDesa(), R.id.admin_fragment_container)
                "Akun" -> displayFragment(DaftarAkunFragment(), R.id.admin_fragment_container)

                else -> {
                }
            }

            updateTabView(position)
            mTabPosition = position
        }
    }
    private fun updateTabView(position: Int) {
//        admin_tab_pesanan.setBackground(ContextCompat.getDrawable(this, R.color.blue_primary))
//        admin_tab_desa.setBackground(ContextCompat.getDrawable(this, R.color.blue_primary))
        admin_tab_akun.setBackground(ContextCompat.getDrawable(this, R.color.blue_primary))
        when (position) {
//            0 -> admin_tab_pesanan.setBackground(
//                ContextCompat.getDrawable(
//                    this,
//                    R.drawable.button_orange_selector
//                )
//            )
//            1 -> admin_tab_desa.setBackground(
//                ContextCompat.getDrawable(
//                    this,
//                    R.drawable.button_orange_selector
//                )
//            )
            2 -> admin_tab_akun.setBackground(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.button_orange_selector
                )
            )
            else -> admin_tab_akun.setBackground(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.button_orange_selector
                )
            )
        }


    }

    fun displayFragment(fragment: Fragment, fragmentResourceID: Int) {
        try {
            showFragment(fragment, fragmentResourceID)
        } catch (e: IllegalStateException) {
            e.printStackTrace()

            showFragmentAllowingStateLoss(fragment, fragmentResourceID)
        }

    }

}
