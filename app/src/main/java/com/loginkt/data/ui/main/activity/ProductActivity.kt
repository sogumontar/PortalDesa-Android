package com.loginkt.data.ui.main.activity

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.loginkt.R
import com.loginkt.data.base.AppActivity
import com.loginkt.data.ui.main.adapter.PenginapanAdapter
import com.loginkt.data.ui.main.adapter.ProductAdapter
import kotlinx.android.synthetic.main.activity_product.*
import kotlinx.android.synthetic.main.toolbar.*

class ProductActivity : AppActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        val mainMenuLayoutManager = GridLayoutManager(this, 2)
        recycler_view.setLayoutManager(mainMenuLayoutManager)
        recycler_view.setNestedScrollingEnabled(false)
        initView()
    }

    fun initView(){
        initToolbar(R.id.toolbar)
        tv_toolbar_title.text = getString(R.string.title_product)
        val adapter = ProductAdapter(this)
        recycler_view.setAdapter(adapter)

    }
}