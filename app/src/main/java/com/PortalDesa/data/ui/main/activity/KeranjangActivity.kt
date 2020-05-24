package com.PortalDesa.data.ui.main.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
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
import com.PortalDesa.data.support.Utils
import com.PortalDesa.data.ui.main.adapter.KeranjangAdapter
import com.PortalDesa.data.ui.main.fragment.BelumBayarFragment
import com.PortalDesa.data.ui.main.fragment.SudahBayarFragment
import kotlinx.android.synthetic.main.activity_pesanan.*
import kotlinx.android.synthetic.main.content_keranjang.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.toolbar.tv_toolbar_title
import retrofit2.Response
import java.util.*
import kotlin.concurrent.schedule

class KeranjangActivity : AppActivity(),  View.OnClickListener {
    private var customerResponse: CustomerResponse? = null
    private var keranjangResponse: List<KeranjangResponse>? = null
    private var productResponse: ProductResponse? = null
    private var totall: Int? = null
    var sku: String = ""
    var metode: String =""
    var alamat: String =""
    lateinit var preferences: Preferences
    var adapter : KeranjangAdapter? = null

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
        initView()
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
        showProgressDialog()
        val role = preferences.getRoles()
        val sku = preferences.getSku()

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
                        if(listProduk!!.size==0){
                            Toast.makeText(applicationContext,"Produk di Keranjang Belum ada",Toast.LENGTH_SHORT).show()
                            Timer("SettingUp", false).schedule(1000) {
                                goToProduct()
                            }
                        }else{
                            checkAlamat()
//                            initView()
                            displayProduct()

                        }

                    }

                    override fun onFailure(call: retrofit2.Call<List<KeranjangResponse>>, t: Throwable) {
                        dismissProgressDialog()
                        Log.i(this.javaClass.simpleName, " Requested API : " + call.request().body()!!)
                        Log.e(this.javaClass.simpleName, " Exceptions : $t")
                    }
                })

        }
    }


    fun goToProduct(){
        intent = Intent(this  , MainActivity::class.java)
        startActivity(intent)
    }
    fun displayProduct(){
        var hargaTotal: Int=0
        for(dat in keranjangResponse!!){
            hargaTotal+= (dat.harga!!*dat.jumlah!!)
        }
        totall=hargaTotal
        total.setText(Utils().numberToIDR(totall!!, true))
        if (keranjangResponse != null) {
            val produkListLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
            recycler_view_keranjang.setLayoutManager(produkListLayoutManager)

            adapter = KeranjangAdapter(this, keranjangResponse!!)
            recycler_view_keranjang.setAdapter(adapter)
        }
        dismissProgressDialog()
    }

    fun initView(){
        initToolbar(R.id.toolbar)
        tv_toolbar_title.text = getString(R.string.sign_up_tab_title_keranjang)

    }

    fun removeItem(position: Int) {
        keranjangResponse!!.drop(position)
//        adapter!!.updateList(keranjangResponse!!)
        recycler_view_keranjang.adapter!!.notifyDataSetChanged()
        dismissProgressDialog()
    }

    fun delete(value: String, pos : Int) {
        showProgressDialog()
        if (Connectivity().isNetworkAvailable(this)) {
            val client = APIServiceGenerator().createService
            val call = client.deleteCart(value)
            call.enqueue(object : retrofit2.Callback<DefaultResponse> {
                override fun onResponse(
                    call: retrofit2.Call<DefaultResponse>,
                    response: Response<DefaultResponse>
                ) {
                    removeItem(pos)
                }

                override fun onFailure(call: retrofit2.Call<DefaultResponse>, t: Throwable) {
                    Log.e(this.javaClass.simpleName, " Exceptions : $t")
                }
            })

        }
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
                    if(customerResponse!=null){
                        alamat=customerResponse!!.alamat!!
                        transaksi_alamat.setText(customerResponse!!.alamat.toString())
                        btn_transaksi_simpan.visibility=View.GONE
                        btn_transaksi_ubah.visibility=View.VISIBLE
                    }else{
                        alamat=""
                        transaksi_alamat.setText(alamat.toString())
                        btn_transaksi_simpan.visibility=View.VISIBLE
                        btn_transaksi_ubah.visibility=View.GONE
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
    private fun getAlamatRequest(): CustomerRequest{
        val customerRequest = CustomerRequest()
        customerRequest.id = UUID.randomUUID().toString()
        val alam=transaksi_alamat.text.toString()
        customerRequest.alamat = transaksi_alamat.text.toString()
        customerRequest.sku = preferences.getSku()
        return customerRequest
    }

    fun getDataForRequest():TransaksiRequest{
        val transaksiRequest = TransaksiRequest()
        var skuProduk: String = ""
        for(dat in keranjangResponse!!){
            skuProduk += dat.idProduk + ","
        }
        transaksiRequest.id=UUID.randomUUID().toString()
        transaksiRequest.skuProduk = skuProduk
        transaksiRequest.skuCustomer = preferences.getSku()
        transaksiRequest.alamat = transaksi_alamat.text.toString()
        transaksiRequest.harga = totall
        transaksiRequest.metode =metode
        transaksiRequest.resi = ""
        transaksiRequest.status =1
        return transaksiRequest
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
                    goToKeranjang()
                }

                override fun onFailure(call: retrofit2.Call<DefaultResponse>, t: Throwable) {
                    Log.i(this.javaClass.simpleName, " Requested API : " + call.request().body()!!)
                    Log.e(this.javaClass.simpleName, " Exceptions : $t")
                }
            })

        }
    }

    fun showDialogPB(check : Boolean){
        if(check){
            showProgressDialog()
        }else{
            dismissProgressDialog()
        }
    }

    fun simpanTransaksi(){
        showProgressDialog()
        if (Connectivity().isNetworkAvailable(this)) {
            val client = APIServiceGenerator().createService
            val call = client.addTransaction(getDataForRequest())
            call.enqueue(object : retrofit2.Callback<DefaultResponse> {
                override fun onResponse(
                    call: retrofit2.Call<DefaultResponse>,
                    response: Response<DefaultResponse>
                ) {
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
            simpanTransaksi()
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
