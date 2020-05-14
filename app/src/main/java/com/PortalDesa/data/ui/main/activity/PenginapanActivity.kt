package com.PortalDesa.data.ui.main.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.PortalDesa.R
import com.PortalDesa.data.apiService.APIServiceGenerator
import com.PortalDesa.data.base.AppActivity
import com.PortalDesa.data.model.response.PenginapanResponse
import com.PortalDesa.data.support.Connectivity
import com.PortalDesa.data.support.Preferences
import com.PortalDesa.data.ui.main.activity.merchant.CreatePenginapanForm
import com.PortalDesa.data.ui.main.activity.merchant.PenginapanForm
import com.PortalDesa.data.ui.main.adapter.ListPenginapanAdapter
import kotlinx.android.synthetic.main.activity_penginapan.*
import kotlinx.android.synthetic.main.item_penginapan.*
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Callback
import retrofit2.Response

class PenginapanActivity : AppActivity(), View.OnClickListener {
    lateinit private var preferences : Preferences

    private var penginapanResponse: List<PenginapanResponse>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_penginapan)

        penginapan_recycler_view.setHasFixedSize(true)
        val menuListLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        penginapan_recycler_view.setLayoutManager(menuListLayoutManager)
        penginapan_recycler_view.setNestedScrollingEnabled(false)
        preferences = Preferences(this )
        initData()
        initView()

    }

    fun initData(){
        val role = preferences.getRoles()
        penginapan_button_create.setOnClickListener(this)
        val sku = preferences.getSku()
        if (Connectivity().isNetworkAvailable(this)) {
            val client = APIServiceGenerator().createService
            if(preferences.getRoles().equals("ROLE_MERCHANT")){
                penginapan_button_create.visibility = View.VISIBLE
                val call = client.lihatPenginapanAllByMerchant(sku)
                call.enqueue(object : retrofit2.Callback<List<PenginapanResponse>> {
                    override fun onResponse(
                        call: retrofit2.Call<List<PenginapanResponse>>,
                        response: Response<List<PenginapanResponse>>
                    ) {
                        val listProduk = response.body()
                        penginapanResponse= listProduk
                        displayProduct()
                    }

                    override fun onFailure(call: retrofit2.Call<List<PenginapanResponse>>, t: Throwable) {
                        Log.i(this.javaClass.simpleName, " Requested API : " + call.request().body()!!)
                        Log.e(this.javaClass.simpleName, " Exceptions : $t")
                    }
                })
            }else{
                val call = client.lihatPenginapanAll()
                call.enqueue(object : retrofit2.Callback<List<PenginapanResponse>> {
                    override fun onResponse(
                        call: retrofit2.Call<List<PenginapanResponse>>,
                        response: Response<List<PenginapanResponse>>
                    ) {
                        val listProduk = response.body()
                        penginapanResponse= listProduk
                        displayProduct()
                    }

                    override fun onFailure(call: retrofit2.Call<List<PenginapanResponse>>, t: Throwable) {
                        Log.i(this.javaClass.simpleName, " Requested API : " + call.request().body()!!)
                        Log.e(this.javaClass.simpleName, " Exceptions : $t")
                    }
                })
            }

        }
    }

    fun displayProduct(){
        if (penginapanResponse != null && penginapan_recycler_view != null) {
            val produkListLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
            penginapan_recycler_view.setLayoutManager(produkListLayoutManager)
            val adapter = ListPenginapanAdapter(this, penginapanResponse!!)
            penginapan_view_animator.setDisplayedChild(1)
            penginapan_recycler_view.setAdapter(adapter)

        }
    }


    fun initView(){
        initToolbar(R.id.toolbar)
        tv_toolbar_title.text = getString(R.string.title_penginapan)

//        val adapter = PenginapanAdapter()
//        recycler_view.setAdapter(adapter)
    }
    fun goToForm(){
        val intent = Intent(this, CreatePenginapanForm::class.java)
        startActivity(intent)
    }
    override fun onClick(v: View?) {
        when (v!!.id) {
            penginapan_button_create.id -> goToForm()
//            penginapan_btn_hapus.id ->
        }
    }
}
