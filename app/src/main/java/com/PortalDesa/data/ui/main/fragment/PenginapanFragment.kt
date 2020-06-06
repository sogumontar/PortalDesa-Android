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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.PortalDesa.R
import com.PortalDesa.data.apiService.APIServiceGenerator
import com.PortalDesa.data.model.response.PenginapanResponse
import com.PortalDesa.data.support.Connectivity
import com.PortalDesa.data.support.Preferences
import com.PortalDesa.data.ui.main.activity.merchant.CreatePenginapanForm
import com.PortalDesa.data.ui.main.adapter.ListPenginapanAdapter
import kotlinx.android.synthetic.main.activity_penginapan.*
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Response
import java.util.ArrayList

/**
 * Created by Sogumontar Hendra Simangunsong on 06/06/2020.
 */

class PenginapanFragment : Fragment(), View.OnClickListener{

    lateinit private var preferences : Preferences
    private var penginapanResponse: List<PenginapanResponse>? = null
    private var adapter: ListPenginapanAdapter? = null
    companion object{
        fun newInstance() : ProductFragment{
            return newInstance()
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View?{
        return inflater.inflate(R.layout.activity_penginapan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        penginapan_recycler_view.setHasFixedSize(true)
        preferences = Preferences(activity as Context)
        initView()
        initData()
    }

    fun initData(){
        val role = preferences.getRoles()
        penginapan_button_create.setOnClickListener(this)
        val sku = preferences.getSku()
        if (Connectivity().isNetworkAvailable(activity!!)) {
            val client = APIServiceGenerator().createService
            if(preferences.getRoles().equals("ROLE_MERCHANT")) {
                penginapan_button_create.visibility = View.VISIBLE
            }
//            if(preferences.getRoles().equals("ROLE_MERCHANT")){
//                penginapan_button_create.visibility = View.VISIBLE
//                val call = client.lihatPenginapanAllByMerchant(sku)
//                call.enqueue(object : retrofit2.Callback<List<PenginapanResponse>> {
//                    override fun onResponse(
//                        call: retrofit2.Call<List<PenginapanResponse>>,
//                        response: Response<List<PenginapanResponse>>
//                    ) {
//                        val listProduk = response.body()
//                        penginapanResponse= listProduk
//                        displayProduct()
//                    }
//
//                    override fun onFailure(call: retrofit2.Call<List<PenginapanResponse>>, t: Throwable) {
//                        Log.i(this.javaClass.simpleName, " Requested API : " + call.request().body()!!)
//                        Log.e(this.javaClass.simpleName, " Exceptions : $t")
//                    }
//                })
//            }else{
                val call = client.lihatPenginapanAll()
                call.enqueue(object : retrofit2.Callback<List<PenginapanResponse>> {
                    override fun onResponse(
                        call: retrofit2.Call<List<PenginapanResponse>>,
                        response: Response<List<PenginapanResponse>>
                    ) {
                        val listProduk = response.body()
                        penginapanResponse= listProduk
                        displayProduct()
                    }

                    override fun onFailure(call: retrofit2.Call<List<PenginapanResponse>>, t: Throwable) {
                        Log.i(this.javaClass.simpleName, " Requested API : " + call.request().body()!!)
                        Log.e(this.javaClass.simpleName, " Exceptions : $t")
                    }
                })
//            }

        }
    }

    fun displayProduct(){
        if (penginapanResponse != null && penginapan_recycler_view != null) {
            val produkListLayoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
            penginapan_recycler_view.setLayoutManager(produkListLayoutManager)
            adapter = ListPenginapanAdapter(activity!!, penginapanResponse!!)
            penginapan_view_animator.setDisplayedChild(1)
            penginapan_recycler_view.setAdapter(adapter)

        }
    }


    fun initView(){
        tv_toolbar_title.text = getString(R.string.title_penginapan)

        et_search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }

            override fun onTextChanged(
                charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }

            override fun afterTextChanged(editable: Editable) {
                filter(editable.toString())
            }
        })
    }

    fun filter(text: String) {
        val dataProduk = ArrayList<PenginapanResponse>()

        //looping through existing elements
        for (s in penginapanResponse!!) {
            //if the existing elements contains the search input
            if (s.nama!!.toLowerCase().contains(text.toLowerCase())) {
                //adding the element to filtered list
                dataProduk.add(s)
            }
        }
        adapter!!.filterList(dataProduk)

    }


    fun goToForm(){
        val intent = Intent(activity, CreatePenginapanForm::class.java)
        startActivity(intent)
    }
    override fun onClick(v: View?) {
        when (v!!.id) {
            penginapan_button_create.id -> goToForm()
//            penginapan_btn_hapus.id ->
        }
    }
}