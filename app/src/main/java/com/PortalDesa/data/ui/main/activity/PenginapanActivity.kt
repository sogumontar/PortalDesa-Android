package com.PortalDesa.data.ui.main.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.PortalDesa.R
import com.PortalDesa.data.apiService.APIServiceGenerator
import com.PortalDesa.data.base.AppActivity
import com.PortalDesa.data.model.response.PenginapanResponse
import com.PortalDesa.data.model.response.ProductResponse
import com.PortalDesa.data.support.Connectivity
import com.PortalDesa.data.support.Preferences
import com.PortalDesa.data.ui.main.activity.merchant.CreatePenginapanForm
import com.PortalDesa.data.ui.main.adapter.ListPenginapanAdapter
import com.PortalDesa.data.ui.main.adapter.ListProductAdapter
import kotlinx.android.synthetic.main.activity_penginapan.*
import kotlinx.android.synthetic.main.activity_penginapan.et_search
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class PenginapanActivity : AppActivity(), View.OnClickListener {
    lateinit private var preferences : Preferences
    private var penginapanResponse: List<PenginapanResponse>? = null
    private var adapter: ListPenginapanAdapter? = null

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
            adapter = ListPenginapanAdapter(this, penginapanResponse!!)
            penginapan_view_animator.setDisplayedChild(1)
            penginapan_recycler_view.setAdapter(adapter)

        }
    }


    fun initView(){
        initToolbar(R.id.toolbar)
        tv_toolbar_title.text = getString(R.string.title_penginapan)

        et_search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }

            override fun onTextChanged(
                charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }

            override fun afterTextChanged(editable: Editable) {
                filter(editable.toString())
            }
        })
    }

    fun filter(text: String) {
        val dataProduk = ArrayList<PenginapanResponse>()

        //looping through existing elements
        for (s in penginapanResponse!!) {
            //if the existing elements contains the search input
            if (s.nama!!.toLowerCase().contains(text.toLowerCase())) {
                //adding the element to filtered list
                dataProduk.add(s)
            }
        }
        adapter!!.filterList(dataProduk)

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
