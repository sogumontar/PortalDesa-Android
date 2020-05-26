package com.PortalDesa.data.ui.main.fragment.admin

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
import com.PortalDesa.data.model.response.PesananResponse
import com.PortalDesa.data.model.response.ProductResponse
import com.PortalDesa.data.model.response.TransaksiPenginapanResponse
import com.PortalDesa.data.support.Connectivity
import com.PortalDesa.data.support.Preferences
import com.PortalDesa.data.ui.main.adapter.PesananAdapter
import com.PortalDesa.data.ui.main.adapter.admin.DaftarPesananBelumDibayarAdapter
import com.PortalDesa.data.ui.main.adapter.admin.DaftarPesananPenginapanBelumDibayar
import kotlinx.android.synthetic.main.activity_daftar_pesanan2.*
import kotlinx.android.synthetic.main.fragment_belum_bayar.*
import retrofit2.Response

class BelumDibayarFragmentAdmin() : Fragment(){

    lateinit private var preferences : Preferences
    private var adapter: PesananAdapter? = null
    private var pesananResponse: List<PesananResponse>? = null

    private var productResponse: List<ProductResponse>? = null
    companion object{
        fun newInstance() : BelumDibayarFragmentAdmin{
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
//        initDataPenginapan()

    }

    fun initData(){
        if (Connectivity().isNetworkAvailable(activity as Context)) {
            val client = APIServiceGenerator().createService
            val call = client.getPesananAll()
            call.enqueue(object : retrofit2.Callback<List<PesananResponse>> {
                override fun onResponse(
                    call: retrofit2.Call<List<PesananResponse>>,
                    response: Response<List<PesananResponse>>
                ) {
                    val listKecamatan = response.body()
                    if(listKecamatan!=null) {
                        displayData(listKecamatan)
                    }
                }

                override fun onFailure(
                    call: retrofit2.Call<List<PesananResponse>>,
                    t: Throwable
                ) {
                    Log.i(this.javaClass.simpleName, " Requested API : " + call.request().body()!!)
                    Log.e(this.javaClass.simpleName, " Exceptions : $t")
                }
            })
        }

    }

    fun displayData(list: List<PesananResponse>){
        if (recycler_view_pesanan != null) {
            val menuListLayoutManager = LinearLayoutManager(activity as Context, RecyclerView.VERTICAL, false)
            recycler_view_pesanan.setLayoutManager(menuListLayoutManager)
            recycler_view_pesanan.setHasFixedSize(true)
            val adapter = DaftarPesananBelumDibayarAdapter(activity as Context, list)
            view_animator.setDisplayedChild(1)
            recycler_view_pesanan.setAdapter(adapter)
        }

    }

//    fun initDataPenginapan(){
//        if (Connectivity().isNetworkAvailable(activity as Context)) {
//            val client = APIServiceGenerator().createService
//            val call = client.getPesananPenginapanAll()
//            call.enqueue(object : retrofit2.Callback<List<TransaksiPenginapanResponse>> {
//                override fun onResponse(
//                    call: retrofit2.Call<List<TransaksiPenginapanResponse>>,
//                    response: Response<List<TransaksiPenginapanResponse>>
//                ) {
//                    val listKecamatan = response.body()
//                    if(listKecamatan!=null) {
//                        displayDataPenginapan(listKecamatan)
//                    }
//                }
//
//                override fun onFailure(
//                    call: retrofit2.Call<List<TransaksiPenginapanResponse>>,
//                    t: Throwable
//                ) {
//                    Log.i(this.javaClass.simpleName, " Requested API : " + call.request().body()!!)
//                    Log.e(this.javaClass.simpleName, " Exceptions : $t")
//                }
//            })
//        }
//
//    }
//
//    fun displayDataPenginapan(list: List<TransaksiPenginapanResponse>){
//        if (recycler_view_pesanan != null) {
//            val menuListLayoutManager = LinearLayoutManager(activity as Context, RecyclerView.VERTICAL, false)
//            recycler_view_penginapan.setLayoutManager(menuListLayoutManager)
//            recycler_view_penginapan.setHasFixedSize(true)
//            val adapter = DaftarPesananPenginapanBelumDibayar(activity as Context, list)
//            view_animator.setDisplayedChild(1)
//            recycler_view_penginapan.setAdapter(adapter)
//        }
//
//    }

}