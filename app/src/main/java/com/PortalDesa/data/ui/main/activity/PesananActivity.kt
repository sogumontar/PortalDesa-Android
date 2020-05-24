package com.PortalDesa.data.ui.main.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.PortalDesa.R
import com.PortalDesa.data.apiService.APIServiceGenerator
import com.PortalDesa.data.base.AppActivity
import com.PortalDesa.data.model.response.PesananResponse
import com.PortalDesa.data.support.Connectivity
import com.PortalDesa.data.support.Preferences
import com.PortalDesa.data.ui.main.adapter.PesananAdapter
import com.PortalDesa.data.ui.main.adapter.PesananSudahBayarAdapter
import com.PortalDesa.data.ui.main.fragment.BelumBayarFragment
import com.PortalDesa.data.ui.main.fragment.SudahBayarFragment
import kotlinx.android.synthetic.main.activity_pesanan.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Response

class PesananActivity : AppActivity(), View.OnClickListener{
    lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        preferences = Preferences(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pesanan)
        initView()
        btn_tab_1.setOnClickListener(this)
        btn_tab_2.setOnClickListener(this)
        tabSelected(1)
    }

    private fun initView() {
        initToolbar(R.id.toolbar)
        tv_toolbar_title.text = "Pesanan"
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
            line_tab_1.setVisibility(View.VISIBLE)
            line_tab_2.setVisibility(View.GONE)
            showFragmentAllowingStateLoss(BelumBayarFragment(), R.id.fragment_container)
        } else {
            btn_tab_1.setTextColor(ContextCompat.getColor(this, R.color.soft_grey))
            btn_tab_2.setTextColor(ContextCompat.getColor(this, R.color.blue_primary))
            line_tab_1.setVisibility(View.GONE)
            line_tab_2.setVisibility(View.VISIBLE)
            showFragmentAllowingStateLoss(SudahBayarFragment(), R.id.fragment_container)
        }
    }


}
