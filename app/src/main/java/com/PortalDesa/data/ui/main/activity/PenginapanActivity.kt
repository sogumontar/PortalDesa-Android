package com.PortalDesa.data.ui.main.activity

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.PortalDesa.R
import com.PortalDesa.data.base.AppActivity
import kotlinx.android.synthetic.main.activity_penginapan.*
import kotlinx.android.synthetic.main.toolbar.*

class PenginapanActivity : AppActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_penginapan)

        recycler_view.setHasFixedSize(true)
        val menuListLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recycler_view.setLayoutManager(menuListLayoutManager)
        recycler_view.setNestedScrollingEnabled(false)
        initView()

    }
    fun initView(){
        initToolbar(R.id.toolbar)
        tv_toolbar_title.text = getString(R.string.title_penginapan)
//        val adapter = PenginapanAdapter()
//        recycler_view.setAdapter(adapter)
    }
}
