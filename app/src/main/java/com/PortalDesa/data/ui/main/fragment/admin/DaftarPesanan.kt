package com.PortalDesa.data.ui.main.fragment.admin

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.PortalDesa.R
import com.PortalDesa.data.ui.main.activity.admin.MainActivityAdmin
import kotlinx.android.synthetic.main.activity_daftar_pesanan.btn_tab_1
import kotlinx.android.synthetic.main.activity_daftar_pesanan.btn_tab_2
import kotlinx.android.synthetic.main.activity_daftar_pesanan.line_tab_1
import kotlinx.android.synthetic.main.activity_daftar_pesanan.line_tab_2
import kotlinx.android.synthetic.main.activity_pesanan.*

class DaftarPesanan : Fragment(), View.OnClickListener{


    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_daftar_pesanan, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_tab_1.setOnClickListener(this)
        btn_tab_2.setOnClickListener(this)
        tabSelected(1)
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
            btn_tab_1.setTextColor(ContextCompat.getColor(activity!!, R.color.blue_primary))
            btn_tab_2.setTextColor(ContextCompat.getColor(activity!!, R.color.soft_grey))
            line_tab_1.setVisibility(View.VISIBLE)
            line_tab_2.setVisibility(View.GONE)
            (context as MainActivityAdmin).showFragmentAllowingStateLoss(BelumDibayarFragmentAdmin(), R.id.fragment_container)
        } else {
            btn_tab_1.setTextColor(ContextCompat.getColor(activity!!, R.color.soft_grey))
            btn_tab_2.setTextColor(ContextCompat.getColor(activity!!, R.color.blue_primary))
            line_tab_1.setVisibility(View.GONE)
            line_tab_2.setVisibility(View.VISIBLE)
            (context as MainActivityAdmin).showFragmentAllowingStateLoss(SudahDibayarFragmentAdmin(), R.id.fragment_container)
        }
    }
}
