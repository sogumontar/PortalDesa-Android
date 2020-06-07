package com.PortalDesa.data.ui.main.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.PortalDesa.R
import com.PortalDesa.data.apiService.APIServiceGenerator
import com.PortalDesa.data.model.response.KecamatanResponse
import com.PortalDesa.data.model.response.ProductResponse
import com.PortalDesa.data.support.Connectivity
import com.PortalDesa.data.support.Utils
import com.PortalDesa.data.ui.main.activity.KecamatanActivity
import com.PortalDesa.data.ui.main.activity.PenginapanActivity
import com.PortalDesa.data.ui.main.activity.ProductActivity
import com.PortalDesa.data.ui.main.activity.merchant.PenginapanForm
import com.PortalDesa.data.ui.main.adapter.PopularVilageAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private var listKecamatan : List<KecamatanResponse>? = null
    private var productResponse : ProductResponse? = null
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

        if (Connectivity().isNetworkAvailable(activity!!)) {
            val client = APIServiceGenerator().createService
            val call = client.getPopularProduct()
            call.enqueue(object : Callback<ProductResponse> {
                override fun onResponse(
                    call: retrofit2.Call<ProductResponse>,
                    response: Response<ProductResponse>
                ) {
                    val listKecamatanResponse = response.body()
                    productResponse = listKecamatanResponse
                    displayPopularProduct()
                }

                override fun onFailure(
                    call: retrofit2.Call<ProductResponse>,
                    t: Throwable
                ) {
                }
            })
        }

//
//        btn_penginapan.setOnClickListener(){
//            val intent = Intent(activity, PenginapanActivity::class.java)
//            startActivity(intent)
//        }
//        btn_produk.setOnClickListener(){
//            val intent = Intent(activity, ProductActivity::class.java)
//            startActivity(intent)
//        }
//        check.setOnClickListener(){
//            val intent = Intent(activity, PenginapanForm::class.java)
//            startActivity(intent)
//        }
        tv_more.setOnClickListener(View.OnClickListener {
            val intent = Intent(activity, KecamatanActivity::class.java)
            startActivity(intent)
        })
    }
    fun displayKecamatan(){
        if (listKecamatan != null && recycler_popular != null) {
            recycler_popular.setHasFixedSize(true)
            val menuListLayoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
            recycler_popular.setLayoutManager(menuListLayoutManager)
            val adapter = PopularVilageAdapter(activity!!, listKecamatan!!, true)
            view_animator.setDisplayedChild(1)
            recycler_popular.setAdapter(adapter)

        }
    }

    fun displayPopularProduct(){
        if(img_icon!=null) {
            Picasso.get()
                .load("https://portal-desa.herokuapp.com" + productResponse?.gambar)
                .into(img_icon)
            tv_nama.setText(productResponse?.nama)
            tv_harga.setText(
                Utils().numberToIDR(productResponse!!.harga!!.toInt(), true)
            )
            desc.setText(productResponse?.deskripsi)
            jumlah.setText(productResponse?.jumlahPembelian)
        }
    }
}