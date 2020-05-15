package com.PortalDesa.data.ui.main.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.PortalDesa.R
import com.PortalDesa.data.apiService.APIServiceGenerator
import com.PortalDesa.data.model.response.ProductResponse
import com.PortalDesa.data.support.Connectivity
import com.PortalDesa.data.support.Preferences
import com.PortalDesa.data.ui.main.activity.merchant.CreatePenginapanForm
import com.PortalDesa.data.ui.main.activity.merchant.CreateProdukForm
import com.PortalDesa.data.ui.main.adapter.ListProductAdapter
import kotlinx.android.synthetic.main.fragment_produk.*
import kotlinx.android.synthetic.main.item_product.*
import retrofit2.Response

/**
 * Created by Sogumontar Hendra Simangunsong on 06/05/2020.
 */

class ProductFragment : Fragment(), View.OnClickListener{

    lateinit private var preferences : Preferences

    private var productResponse: List<ProductResponse>? = null
    companion object{
        fun newInstance() : ProductFragment{
            return newInstance()
        }
    }
    override
    fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View?{
        return inflater.inflate(R.layout.fragment_produk, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_view_produk.setHasFixedSize(true)
        preferences = Preferences(activity as Context)
        initData()
    }

    fun initData(){
        val role = preferences.getRoles()
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
                        Log.i(
                            this.javaClass.simpleName,
                            " Requested API : " + call.request().body()!!
                        )
                        Log.e(this.javaClass.simpleName, " Exceptions : $t")
                    }
                })
            }
        }
    }

    fun displayProduct(){
        if (productResponse != null && recycler_view_produk != null) {
            val mainMenuLayoutManager = GridLayoutManager(activity, 2)
            recycler_view_produk.setLayoutManager(mainMenuLayoutManager)
            val adapter = ListProductAdapter(activity!!, productResponse!!)
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