package com.PortalDesa.data.ui.main.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.PortalDesa.R
import com.PortalDesa.data.base.AppActivity
import com.PortalDesa.data.model.response.KeranjangResponse
import com.PortalDesa.data.model.response.ProfileResponse
import com.PortalDesa.data.support.Preferences
import com.PortalDesa.data.ui.main.adapter.KeranjangAdapter
import kotlinx.android.synthetic.main.activity_keranjang.*

class KeranjangActivity : AppActivity() {

    private var penginapanResponse: List<KeranjangResponse>? = null
    var sku: String = ""
    lateinit var preferences: Preferences
    override fun onCreate(savedInstanceState: Bundle?) {
        preferences = Preferences(this)
        super.onCreate(savedInstanceState)
        val mainMenuLayoutManager = GridLayoutManager(this, 2)
        recycler_view_keranjang.setHasFixedSize(true)
        val menuListLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recycler_view_keranjang.setLayoutManager(menuListLayoutManager)
        recycler_view_keranjang.setNestedScrollingEnabled(false)
        setContentView(R.layout.activity_keranjang)
        initView()
    }
    fun initView(){
        initToolbar(R.id.toolbar)
        val adapter = KeranjangAdapter(this)
        recycler_view_keranjang.setAdapter(adapter)

    }


}
