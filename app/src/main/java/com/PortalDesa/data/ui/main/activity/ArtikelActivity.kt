package com.PortalDesa.data.ui.main.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.PortalDesa.R
import com.PortalDesa.data.base.AppActivity
import com.PortalDesa.data.support.Preferences
import com.PortalDesa.data.ui.main.fragment.ArtikelFragment
import com.PortalDesa.data.ui.main.fragment.BeritaFragment
import com.PortalDesa.data.ui.main.fragment.PengumumanFragment
import kotlinx.android.synthetic.main.activity_artikel.*

class ArtikelActivity : AppActivity(), View.OnClickListener  {
    lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        preferences = Preferences(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_artikel)
        btn_tab_1.setOnClickListener(this)
        btn_tab_2.setOnClickListener(this)
        btn_tab_3.setOnClickListener(this)
        tabSelected(1)
    }

    private fun tabSelected(position: Int) {
        if (position == 1) {
            btn_tab_1.setTextColor(ContextCompat.getColor(this, R.color.blue_primary))
            btn_tab_2.setTextColor(ContextCompat.getColor(this, R.color.soft_grey))
            btn_tab_3.setTextColor(ContextCompat.getColor(this, R.color.soft_grey))
            line_tab_1.setVisibility(View.VISIBLE)
            line_tab_2.setVisibility(View.GONE)
            line_tab_3.setVisibility(View.GONE)
            showFragmentAllowingStateLoss(ArtikelFragment(), R.id.fragment_container)
        } else if (position == 2) {
            btn_tab_1.setTextColor(ContextCompat.getColor(this, R.color.soft_grey))
            btn_tab_2.setTextColor(ContextCompat.getColor(this, R.color.blue_primary))
            btn_tab_3.setTextColor(ContextCompat.getColor(this, R.color.soft_grey))
            line_tab_1.setVisibility(View.GONE)
            line_tab_2.setVisibility(View.VISIBLE)
            line_tab_3.setVisibility(View.GONE)
            showFragmentAllowingStateLoss(BeritaFragment(), R.id.fragment_container)
        } else {
            btn_tab_1.setTextColor(ContextCompat.getColor(this, R.color.soft_grey))
            btn_tab_2.setTextColor(ContextCompat.getColor(this, R.color.soft_grey))
            btn_tab_3.setTextColor(ContextCompat.getColor(this, R.color.blue_primary))
            line_tab_1.setVisibility(View.GONE)
            line_tab_2.setVisibility(View.GONE)
            line_tab_3.setVisibility(View.VISIBLE)
            showFragmentAllowingStateLoss(PengumumanFragment(), R.id.fragment_container)
        }
    }

    override fun onClick(v: View?) {
        val id = v!!.id
        if (id == btn_tab_1.getId()) {
            tabSelected(1)
        } else if (id == btn_tab_2.getId()) {
            tabSelected(2)
        } else if (id == btn_tab_3.getId()) {
            tabSelected(3)
        }
    }
}
