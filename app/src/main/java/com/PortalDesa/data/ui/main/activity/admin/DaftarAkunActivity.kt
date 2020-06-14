package com.PortalDesa.data.ui.main.activity.admin

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.PortalDesa.R
import com.PortalDesa.data.base.AppActivity
import com.PortalDesa.data.ui.main.fragment.DaftarAkunFragment
import com.PortalDesa.data.ui.main.fragment.admin.DaftarAkunAdminFragment
import kotlinx.android.synthetic.main.activity_pesanan.*
import kotlinx.android.synthetic.main.toolbar.*

    class DaftarAkunActivity : AppActivity(),  View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daftar_akun_list)
        initView()
        btn_tab_1.setOnClickListener(this)
        btn_tab_2.setOnClickListener(this)
        tabSelected(1)

    }

        private fun initView() {

            initToolbar(R.id.toolbar)
            tv_toolbar_title.text = "Daftar Akun"
        }

        override fun onClick(v: View) {
            val id = v.id
            if (id == btn_tab_1.getId()) {
                tabSelected(1)
            } else if (id == btn_tab_2.getId()) {
                tabSelected(2)
            }
        }

        private fun tabSelected(position: Int) {
            if (position == 1) {
                btn_tab_1.setTextColor(ContextCompat.getColor(this, R.color.blue_primary))
                btn_tab_2.setTextColor(ContextCompat.getColor(this, R.color.soft_grey))
                btn_tab_1.setText("Daftar Akun Customer")
                btn_tab_2.setText("Daftar Akun Admin Desa")
                line_tab_1.setVisibility(View.VISIBLE)
                line_tab_2.setVisibility(View.GONE)
                showFragmentAllowingStateLoss(DaftarAkunFragment(), R.id.fragment_container)
            } else {
                btn_tab_1.setTextColor(ContextCompat.getColor(this, R.color.soft_grey))
                btn_tab_2.setTextColor(ContextCompat.getColor(this, R.color.blue_primary))
                btn_tab_1.setText("Daftar Akun Customer")
                btn_tab_2.setText("Daftar Akun Admin Desa")
                line_tab_1.setVisibility(View.GONE)
                line_tab_2.setVisibility(View.VISIBLE)
                showFragmentAllowingStateLoss(DaftarAkunAdminFragment(), R.id.fragment_container)
            }
        }

}
