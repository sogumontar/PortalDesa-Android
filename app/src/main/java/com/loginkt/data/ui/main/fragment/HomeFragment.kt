package com.loginkt.data.ui.main.fragment

import android.content.Intent
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
import com.loginkt.data.model.response.KecamatanResponse
import com.loginkt.data.support.Connectivity
import com.loginkt.data.ui.main.activity.PenginapanActivity
import com.loginkt.data.ui.main.activity.ProductActivity
import com.loginkt.data.ui.main.adapter.PopularVilageAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private var listKecamatan : List<KecamatanResponse>? = null

    companion object {

        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    //3
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    fun initView(){
        if (Connectivity().isNetworkAvailable(activity!!)) {
            val client = APIServiceGenerator().createService
            val call = client.getKecamatanList()
            call.enqueue(object : Callback<List<KecamatanResponse>> {
                override fun onResponse(
                    call: retrofit2.Call<List<KecamatanResponse>>,
                    response: Response<List<KecamatanResponse>>
                ) {
                    val listKecamatanResponse = response.body()
                    listKecamatan = listKecamatanResponse
                    displayKecamatan()
                }

                override fun onFailure(
                    call: retrofit2.Call<List<KecamatanResponse>>,
                    t: Throwable
                ) {
                }
            })
        }

        btn_penginapan.setOnClickListener(){
            val intent = Intent(activity, PenginapanActivity::class.java)
            startActivity(intent)
        }
        btn_produk.setOnClickListener(){
            val intent = Intent(activity, ProductActivity::class.java)
            startActivity(intent)
        }
    }
    fun displayKecamatan(){
        if (listKecamatan != null && recycler_popular != null) {
            recycler_popular.setHasFixedSize(true)
            val menuListLayoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
            recycler_popular.setLayoutManager(menuListLayoutManager)
            val adapter = PopularVilageAdapter(listKecamatan!!)
            view_animator.setDisplayedChild(1)
            recycler_popular.setAdapter(adapter)

        }
    }
}