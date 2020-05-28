package com.PortalDesa.data.ui.main.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.PortalDesa.R
import com.PortalDesa.data.apiService.APIServiceGenerator
import com.PortalDesa.data.model.response.TransaksiPenginapanResponse
import com.PortalDesa.data.support.Connectivity
import com.PortalDesa.data.support.Preferences
import com.PortalDesa.data.ui.main.adapter.PenginapanBelumBayarAdapter
import kotlinx.android.synthetic.main.fragment_penginapan_belum_bayar.*
import retrofit2.Response

/**
 * Created by Sogumontar Hendra Simangunsong on 27/05/2020.
 */
class PenginapanBelumBayarFragment() : Fragment(){

    lateinit private var preferences : Preferences
    private var adapter: PenginapanBelumBayarAdapter? = null
    private var pesananResponse: List<TransaksiPenginapanResponse>? = null

//    private var productResponse: List<ProductResponse>? = null
    companion object{
        fun newInstance() : PenginapanBelumBayarFragment{
            return newInstance()
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View?{
        return inflater.inflate(R.layout.fragment_belum_bayar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferences = Preferences(activity as Context)
        initData()

    }

    fun initData() {
        val sku = preferences.getSku()
        if (Connectivity().isNetworkAvailable(activity as Context)) {
            val client = APIServiceGenerator().createService
            val call = client.getPesananPenginapanBelumbayar(sku)
            call.enqueue(object : retrofit2.Callback<List<TransaksiPenginapanResponse>> {
                override fun onResponse(
                    call: retrofit2.Call<List<TransaksiPenginapanResponse>>,
                    response: Response<List<TransaksiPenginapanResponse>>
                ) {
                    val listProduk = response.body()
                    pesananResponse = listProduk
                    if(listProduk != null) {
                        displayData(listProduk)
                    }

                }

                override fun onFailure(call: retrofit2.Call<List<TransaksiPenginapanResponse>>, t: Throwable) {
                    Log.i(this.javaClass.simpleName, " Requested API : " + call.request().body()!!)
                    Log.e(this.javaClass.simpleName, " Exceptions : $t")
                }
            })

        }
    }

    fun displayData(list: List<TransaksiPenginapanResponse>){
        if (recycler_view_pesanan != null) {
            val menuListLayoutManager = LinearLayoutManager(activity as Context, RecyclerView.VERTICAL, false)
            recycler_view_pesanan.setLayoutManager(menuListLayoutManager)
            recycler_view_pesanan.setHasFixedSize(true)
            val adapter = PenginapanBelumBayarAdapter(activity as Context, list)
            view_animator.setDisplayedChild(1)
            recycler_view_pesanan.setAdapter(adapter)
        }

    }

}