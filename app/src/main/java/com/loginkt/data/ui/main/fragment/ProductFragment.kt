package com.loginkt.data.ui.main.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.loginkt.R
import com.loginkt.data.apiService.APIServiceGenerator
import com.loginkt.data.model.response.ProductResponse
import com.loginkt.data.support.Connectivity
import com.loginkt.data.ui.main.adapter.ListProductAdapter
import kotlinx.android.synthetic.main.fragment_produk.*
import retrofit2.Response

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
        if (productResponse != null && recycler_view_produk != null) {
            val produkListLayoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
            recycler_view_produk.setLayoutManager(produkListLayoutManager)
            val adapter = ListProductAdapter(productResponse!!)
            view_animator.setDisplayedChild(1)
            recycler_view_produk.setAdapter(adapter)

        }
    }
}