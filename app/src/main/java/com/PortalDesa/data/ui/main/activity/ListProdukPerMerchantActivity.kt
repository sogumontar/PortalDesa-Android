package com.PortalDesa.data.ui.main.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.PortalDesa.R
import com.PortalDesa.data.apiService.APIServiceGenerator
import com.PortalDesa.data.base.AppActivity
import com.PortalDesa.data.model.response.ProductResponse
import com.PortalDesa.data.support.Connectivity
import com.PortalDesa.data.support.Flag
import com.PortalDesa.data.support.Preferences
import com.PortalDesa.data.ui.main.activity.merchant.CreateProdukForm
import com.PortalDesa.data.ui.main.adapter.ListProductAdapter
import kotlinx.android.synthetic.main.activity_list_produk_per_merchant.*
import retrofit2.Response

class ListProdukPerMerchantActivity : AppActivity(), View.OnClickListener {
    lateinit private var preferences : Preferences
    private var adapter: ListProductAdapter? = null

    private var productResponse: List<ProductResponse>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_produk_per_merchant)
        val menuListLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recycler_view_produk.setLayoutManager(menuListLayoutManager)
        recycler_view_produk.setNestedScrollingEnabled(false)
        recycler_view_produk.setHasFixedSize(true)
        preferences = Preferences(this)
        initData()
    }
    fun initData() {
        val role = preferences.getRoles()
        et_search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }

            override fun onTextChanged(
                charSequence: CharSequence, i: Int, i1: Int, i2: Int
            ) {
            }

            override fun afterTextChanged(editable: Editable) {
                filter(editable.toString())
            }
        })

        button_create_produk.setOnClickListener(this)

        val sku = preferences.getSku()
        if (Connectivity().isNetworkAvailable(this)) {
            val client = APIServiceGenerator().createService
            val sku = intent.getStringExtra(Flag.SKU_Penginapan_MERCHANT)
            val call = client.getProductBySkuAdmin(sku)
            call.enqueue(object : retrofit2.Callback<List<ProductResponse>> {
                override fun onResponse(
                    call: retrofit2.Call<List<ProductResponse>>,
                    response: Response<List<ProductResponse>>
                ) {
                    val listProduk = response.body()
                    productResponse = listProduk
                    displayProduct()
                }

                override fun onFailure(
                    call: retrofit2.Call<List<ProductResponse>>,
                    t: Throwable
                ) {
                    Log.i(
                        this.javaClass.simpleName,
                        " Requested API : " + call.request().body()!!
                    )
                    Log.e(this.javaClass.simpleName, " Exceptions : $t")
                }
            })
        }
    }

    fun filter(text: String) {
        val dataProduk = ArrayList<ProductResponse>()

        //looping through existing elements
        for (s in productResponse!!) {
            //if the existing elements contains the search input
            if (s.nama!!.toLowerCase().contains(text.toLowerCase())) {
                //adding the element to filtered list
                dataProduk.add(s)
            }
        }
        adapter!!.filterList(dataProduk)

    }

    fun displayProduct(){
        if(preferences.getRoles().equals("ROLE_MERCHANT")){
            button_create_produk.visibility=View.VISIBLE
        }
        if (productResponse != null && recycler_view_produk != null) {
            val mainMenuLayoutManager = GridLayoutManager(this, 2)
            recycler_view_produk.setLayoutManager(mainMenuLayoutManager)
            adapter = ListProductAdapter(this, productResponse!!)
            view_animator.setDisplayedChild(1)
            recycler_view_produk.setAdapter(adapter)

        }
    }
    fun goToForm(){
        val intent = Intent(this, CreateProdukForm::class.java)
        startActivity(intent)
    }

    override fun onClick(v: View?) {
        when(v!!.id) {
            button_create_produk.id -> goToForm()
        }
    }
}
