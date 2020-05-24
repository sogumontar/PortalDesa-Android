package com.PortalDesa.data.ui.main.fragment.admin

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Display
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
import com.PortalDesa.data.support.Connectivity
import com.PortalDesa.data.support.Preferences
import com.PortalDesa.data.ui.main.adapter.PesananAdapter
import com.PortalDesa.data.ui.main.adapter.admin.DaftarPesananSudahDibayarADapter
import kotlinx.android.synthetic.main.fragment_pernah_bayar.*
import retrofit2.Response

class SudahDibayarFragmentAdmin() : Fragment(){

    lateinit private var preferences : Preferences
    private var adapter: PesananAdapter? = null
    private var pesananResponseSudahBayar: List<PesananResponse>? = null

    private var productResponse: List<ProductResponse>? = null
    companion object{
        fun newInstance() : SudahDibayarFragmentAdmin {
            return newInstance()
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View?{
        return inflater.inflate(R.layout.fragment_pernah_bayar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferences = Preferences(activity as Context)
        initDataSudahBayar()

    }

    fun initDataSudahBayar(){
        if (Connectivity().isNetworkAvailable(activity as Context)) {
            val client = APIServiceGenerator().createService
            val call = client.getPesananAllSudahBayar()
            call.enqueue(object : retrofit2.Callback<List<PesananResponse>> {
                override fun onResponse(
                    call: retrofit2.Call<List<PesananResponse>>,
                    response: Response<List<PesananResponse>>
                ) {
                    val listData = response.body()
                    if(listData != null) {
                        displayData(listData)
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
            val adapter = DaftarPesananSudahDibayarADapter(activity as Context, list)
            view_animator.setDisplayedChild(1)
            recycler_view_pesanan.setAdapter(adapter)
        }

    }

}