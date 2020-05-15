package com.PortalDesa.data.ui.main.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.PortalDesa.R
import com.PortalDesa.data.apiService.APIServiceGenerator
import com.PortalDesa.data.base.AppActivity
import com.PortalDesa.data.model.request.CustomerRequest
import com.PortalDesa.data.model.request.TransaksiRequest
import com.PortalDesa.data.model.response.CustomerResponse
import com.PortalDesa.data.model.response.DefaultResponse
import com.PortalDesa.data.model.response.KeranjangResponse
import com.PortalDesa.data.model.response.ProductResponse
import com.PortalDesa.data.support.Connectivity
import com.PortalDesa.data.support.Preferences
import com.PortalDesa.data.ui.main.adapter.KeranjangAdapter
import kotlinx.android.synthetic.main.activity_keranjang.*
import retrofit2.Response
import java.util.*

class KeranjangActivity : AppActivity(),  View.OnClickListener {
    private var customerResponse: CustomerResponse? = null
    private var keranjangResponse: List<KeranjangResponse>? = null
    private var productResponse: ProductResponse? = null
    var sku: String = ""
    var metode: String =""
    var alamat: String =""
    lateinit var preferences: Preferences
    override fun onCreate(savedInstanceState: Bundle?) {
        preferences = Preferences(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_keranjang)
        recycler_view_keranjang.setHasFixedSize(true)
        btn_pesan.setOnClickListener(this)
        btn_transaksi_simpan.setOnClickListener(this)
        btn_transaksi_ubah.setOnClickListener(this)
        val menuListLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recycler_view_keranjang.setLayoutManager(menuListLayoutManager)
        recycler_view_keranjang.setNestedScrollingEnabled(false)
        radio_group.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val radio: RadioButton = findViewById(checkedId)
                metode=radio.text.toString()
            })
        initData()

    }
    fun radio_button_click(view: View){
        val radio: RadioButton = findViewById(radio_group.checkedRadioButtonId)
        if(metode.toString().equals("ATM Mandiri")){
            metode= "ATM Mandiri"
            linear_metode.visibility=View.VISIBLE

            linear_metode_bri.visibility=View.GONE
        }else{
            metode= "ATM Mandiri"
            linear_metode.visibility=View.GONE
            linear_metode_bri.visibility=View.VISIBLE
        }
    }
    fun initData(){
        val role = preferences.getRoles()
        val sku = preferences.getSku()
        checkAlamat()
        if (Connectivity().isNetworkAvailable(this)) {
            val client = APIServiceGenerator().createService
                val call = client.cartCustomer(sku)
                call.enqueue(object : retrofit2.Callback<List<KeranjangResponse>> {
                    override fun onResponse(
                        call: retrofit2.Call<List<KeranjangResponse>>,
                        response: Response<List<KeranjangResponse>>
                    ) {
                        val listProduk = response.body()
                        keranjangResponse= listProduk
                        initView()
                        displayProduct()
                    }

                    override fun onFailure(call: retrofit2.Call<List<KeranjangResponse>>, t: Throwable) {
                        Log.i(this.javaClass.simpleName, " Requested API : " + call.request().body()!!)
                        Log.e(this.javaClass.simpleName, " Exceptions : $t")
                    }
                })

        }
    }
    fun displayProduct(){
        var hargaTotal: Int=0
        for(dat in keranjangResponse!!){
            hargaTotal+= (dat.harga!!*dat.jumlah!!)
        }
        total.setText(hargaTotal.toString())
        if (keranjangResponse != null && recycler_view_keranjang!= null) {
            val produkListLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
            recycler_view_keranjang.setLayoutManager(produkListLayoutManager)

            val adapter = KeranjangAdapter(this, keranjangResponse!!)
            recycler_view_keranjang.setAdapter(adapter)
        }
    }

    fun initView(){
        initToolbar(R.id.toolbar)
        val adapter = KeranjangAdapter(this,keranjangResponse!!)
        recycler_view_keranjang.setAdapter(adapter)
    }

    fun getDataForRequest():TransaksiRequest{
        val transaksiRequest = TransaksiRequest()
        var skuProduk: String = ""
        for(dat in keranjangResponse!!){
            skuProduk += dat.idProduk + ","
        }
        transaksiRequest.id=UUID.randomUUID().toString()
//        transaksiRequest.alamat
        transaksiRequest.skuProduk = skuProduk
        return transaksiRequest
    }
    fun checkAlamat(){
        if (Connectivity().isNetworkAvailable(this)) {
            val client = APIServiceGenerator().createService
            val call = client.getAlamatCustomer(preferences.getSku())
            call.enqueue(object : retrofit2.Callback<CustomerResponse> {
                override fun onResponse(
                    call: retrofit2.Call<CustomerResponse>,
                    response: Response<CustomerResponse>
                ) {
                    val listProduk = response.body()
                    customerResponse= listProduk
                    alamat=customerResponse!!.alamat!!
                    transaksi_alamat.setText(customerResponse!!.alamat.toString())
                    if(customerResponse!!.alamat.toString().equals("")){
                        btn_transaksi_simpan.visibility=View.VISIBLE
                        btn_transaksi_ubah.visibility=View.GONE
                    }else{
                        btn_transaksi_simpan.visibility=View.GONE
                        btn_transaksi_ubah.visibility=View.VISIBLE
                    }
                }

                override fun onFailure(call: retrofit2.Call<CustomerResponse>, t: Throwable) {
                    Log.i(this.javaClass.simpleName, " Requested API : " + call.request().body()!!)
                    Log.e(this.javaClass.simpleName, " Exceptions : $t")
                }
            })

        }
    }
    fun goToKeranjang(){
        val intent = Intent(this, KeranjangActivity::class.java)
        startActivity(intent)
    }
    fun getAlamatRequest(): CustomerRequest{
        val alamatRequest = CustomerRequest()
        alamatRequest.id = UUID.randomUUID().toString()
        alamatRequest.alamat = transaksi_alamat.text.toString()
        alamatRequest.sku = preferences.getSku()
        return alamatRequest
    }

    fun ubahAlamat(){
        if (Connectivity().isNetworkAvailable(this)) {
            val client = APIServiceGenerator().createService
            val call = client.updateAlamatCustomer(preferences.getSku(),getAlamatRequest())
            call.enqueue(object : retrofit2.Callback<DefaultResponse> {
                override fun onResponse(
                    call: retrofit2.Call<DefaultResponse>,
                    response: Response<DefaultResponse>
                ) {
                    val listProduk = response.body()
                    goToKeranjang()
                }

                override fun onFailure(call: retrofit2.Call<DefaultResponse>, t: Throwable) {
                    Log.i(this.javaClass.simpleName, " Requested API : " + call.request().body()!!)
                    Log.e(this.javaClass.simpleName, " Exceptions : $t")
                }
            })

        }
    }
    fun simpanAlamat(){
        if (Connectivity().isNetworkAvailable(this)) {
            val client = APIServiceGenerator().createService
            val call = client.saveAlamatCustomer(getAlamatRequest())
            call.enqueue(object : retrofit2.Callback<DefaultResponse> {
                override fun onResponse(
                    call: retrofit2.Call<DefaultResponse>,
                    response: Response<DefaultResponse>
                ) {
                    val listProduk = response.body()
                    goToKeranjang()
                }

                override fun onFailure(call: retrofit2.Call<DefaultResponse>, t: Throwable) {
                    Log.i(this.javaClass.simpleName, " Requested API : " + call.request().body()!!)
                    Log.e(this.javaClass.simpleName, " Exceptions : $t")
                }
            })

        }
    }

    fun check(){
        if(metode.toString().equals("")){
            Toast.makeText(this,"Pilih Metode Pembayaran",Toast.LENGTH_SHORT).show()
        }else if(alamat.toString().equals("")){
            btn_transaksi_simpan.visibility= View.VISIBLE
            btn_transaksi_ubah.visibility= View.GONE
            Toast.makeText(this,"Masukkan Alamat Pengiriman",Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this,metode,Toast.LENGTH_SHORT).show()
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            btn_pesan.id ->check()
            btn_transaksi_simpan.id ->simpanAlamat()
            btn_transaksi_ubah.id -> ubahAlamat()
        }
    }


}
