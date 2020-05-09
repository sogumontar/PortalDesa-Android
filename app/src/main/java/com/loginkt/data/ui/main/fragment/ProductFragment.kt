package com.loginkt.data.ui.main.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.loginkt.R
import com.loginkt.data.apiService.APIServiceGenerator
import com.loginkt.data.model.response.KecamatanResponse
import com.loginkt.data.model.response.ProductResponse
import com.loginkt.data.support.Connectivity
import com.loginkt.data.ui.main.activity.PenginapanActivity
import com.loginkt.data.ui.main.activity.ProductActivity
import com.loginkt.data.ui.main.adapter.ListProductAdapter
import com.loginkt.data.ui.main.adapter.PopularVilageAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_produk.*
import retrofit2.Response
import java.util.zip.Inflater
import javax.security.auth.callback.Callback

/**
 * Created by Sogumontar Hendra Simangunsong on 06/05/2020.
 */

class ProductFragment : Fragment(){

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
        val produkListLayoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        recycler_view_produk.setLayoutManager(produkListLayoutManager)
        initData()
    }

    fun initData(){
        if (Connectivity().isNetworkAvailable(activity!!)) {
            val client = APIServiceGenerator().createService
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

                override fun onFailure(call: retrofit2.Call<List<ProductResponse>>, t: Throwable) {
                    Log.i(this.javaClass.simpleName, " Requested API : " + call.request().body()!!)
                    Log.e(this.javaClass.simpleName, " Exceptions : $t")
                }
            })
        }
    }

    fun displayProduct(){
        if (productResponse != null) {
            val adapter = ListProductAdapter(productResponse!!)
            recycler_view_produk.setAdapter(adapter)
        }
    }
}