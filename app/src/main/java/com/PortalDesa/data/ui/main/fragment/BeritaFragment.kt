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
import com.PortalDesa.data.model.response.ArtikelResponse
import com.PortalDesa.data.support.Connectivity
import com.PortalDesa.data.support.Preferences
import com.PortalDesa.data.ui.main.adapter.ArtikelAdapter
import com.PortalDesa.data.ui.main.adapter.BeritaAdapter
import kotlinx.android.synthetic.main.fragment_artikel.*
import retrofit2.Response

/**
 * Created by Sogumontar Hendra Simangunsong on 31/05/2020.
 */

class BeritaFragment : Fragment() {


    lateinit private var preferences: Preferences
    private var adapter: ArtikelAdapter? = null
    private var pesananResponse: List<ArtikelResponse>? = null


    companion object {
        fun newInstance(): BelumBayarFragment {
            return newInstance()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_artikel, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferences = Preferences(activity as Context)
        initData()

    }

    fun initData() {
        val sku = preferences.getSku()
        val role = preferences.getRoles()
        if(role.equals("ROLE_MERCHANT")) {
            if (Connectivity().isNetworkAvailable(activity as Context)) {
                val client = APIServiceGenerator().createService
                val call = client.getBeritaAllBySku(sku)
                call.enqueue(object : retrofit2.Callback<List<ArtikelResponse>> {
                    override fun onResponse(
                        call: retrofit2.Call<List<ArtikelResponse>>,
                        response: Response<List<ArtikelResponse>>
                    ) {
                        val listProduk = response.body()
                        pesananResponse = listProduk
                        if (listProduk != null) {
                            displayData(listProduk)
                        }

                    }

                    override fun onFailure(
                        call: retrofit2.Call<List<ArtikelResponse>>,
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
        }else{
            if (Connectivity().isNetworkAvailable(activity as Context)) {
                val client = APIServiceGenerator().createService
                val call = client.getBeritaAll()
                call.enqueue(object : retrofit2.Callback<List<ArtikelResponse>> {
                    override fun onResponse(
                        call: retrofit2.Call<List<ArtikelResponse>>,
                        response: Response<List<ArtikelResponse>>
                    ) {
                        val listProduk = response.body()
                        pesananResponse = listProduk
                        if (listProduk != null) {
                            displayData(listProduk)
                        }

                    }

                    override fun onFailure(
                        call: retrofit2.Call<List<ArtikelResponse>>,
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

    fun displayData(list: List<ArtikelResponse>) {
        if (recycler_view_artikel != null) {
            val menuListLayoutManager =
                LinearLayoutManager(activity as Context, RecyclerView.VERTICAL, false)
            recycler_view_artikel.setLayoutManager(menuListLayoutManager)
            recycler_view_artikel.setHasFixedSize(true)
            val adapter = BeritaAdapter(activity as Context, list)
            view_animator.setDisplayedChild(1)
            recycler_view_artikel.setAdapter(adapter)
        }

    }

}