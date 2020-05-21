package com.PortalDesa.data.ui.main.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.PortalDesa.R
import com.PortalDesa.data.apiService.APIServiceGenerator
import com.PortalDesa.data.model.response.ProductResponse
import com.PortalDesa.data.support.Connectivity
import com.PortalDesa.data.support.Preferences
import com.PortalDesa.data.ui.main.activity.merchant.CreateProdukForm
import com.PortalDesa.data.ui.main.adapter.ListProductAdapter
import com.PortalDesa.data.ui.main.adapter.ProductAdapter
import kotlinx.android.synthetic.main.fragment_produk.*
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Response
import java.util.*

/**
 * Created by Sogumontar Hendra Simangunsong on 06/05/2020.
 */

class ProductFragment : Fragment(), View.OnClickListener{

    lateinit private var preferences : Preferences
    private var adapter: ListProductAdapter? = null

    private var productResponse: List<ProductResponse>? = null
    companion object{
        fun newInstance() : ProductFragment{
            return newInstance()
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View?{
        return inflater.inflate(R.layout.fragment_produk, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_view_produk.setHasFixedSize(true)
        preferences = Preferences(activity as Context)
        initView()
        initData()
    }

    fun initView(){
        tv_toolbar_title.text = "Produk Desa"
    }

    fun initData(){
        val role = preferences.getRoles()

        et_search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                charSequence: CharSequence,
                i: Int,
                i1: Int,
                i2: Int
            ) {
            }

            override fun onTextChanged(
                charSequence: CharSequence,
                i: Int,
                i1: Int,
                i2: Int
            ) {
            }

            override fun afterTextChanged(editable: Editable) {
                filter(editable.toString())
            }
        })

        button_create_produk.setOnClickListener(this)
//        if(role.equals("ROLE_MERCHANT")){
//            button_create_produk.visibility = View.VISIBLE
//            produk_btn_hapus.visibility = View.VISIBLE
//            produk_btn_update.visibility=View.VISIBLE
//            produk_btn_cart.visibility=View.GONE
//            produk_btn_pesan.visibility=View.GONE
//        }else if(role.equals("ROLE_USER")){
//            button_create_produk.visibility = View.GONE
//            produk_btn_hapus.visibility = View.GONE
//            produk_btn_update.visibility=View.GONE
//            produk_btn_cart.visibility=View.VISIBLE
//            produk_btn_pesan.visibility=View.VISIBLE
//        }
        val sku = preferences.getSku()
        if (Connectivity().isNetworkAvailable(activity!!)) {
            val client = APIServiceGenerator().createService
            if(role.equals("ROLE_MERCHANT")) {
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
            }else{
                val call = client.getProductList()
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
                        Log.e(this.javaClass.simpleName, " Exceptions : $t")
                    }
                })
            }
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
//        if (bank.getData().size() === 0) {
//            tv_not_found.setVisibility(View.VISIBLE)
//            recyclerView.setVisibility(View.GONE)
//        } else {
//            tv_not_found.setVisibility(View.GONE)
//            recyclerView.setVisibility(View.VISIBLE)
//        }
    }

    fun displayProduct(){
        if (productResponse != null && recycler_view_produk != null) {
            val mainMenuLayoutManager = GridLayoutManager(activity, 2)
            recycler_view_produk.setLayoutManager(mainMenuLayoutManager)
            adapter = ListProductAdapter(activity!!, productResponse!!)
            view_animator.setDisplayedChild(1)
            recycler_view_produk.setAdapter(adapter)

        }
    }

    fun goToForm(){
        val intent = Intent(activity, CreateProdukForm::class.java)
        startActivity(intent)
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            button_create_produk.id-> goToForm()
        }
    }
}