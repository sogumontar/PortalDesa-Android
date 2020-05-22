package com.PortalDesa.data.ui.main.activity.Form

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.PortalDesa.R
import com.PortalDesa.data.apiService.APIServiceGenerator
import com.PortalDesa.data.base.AppActivity
import com.PortalDesa.data.model.request.CustomerRequest
import com.PortalDesa.data.model.request.TransaksiRequest
import com.PortalDesa.data.model.response.CustomerResponse
import com.PortalDesa.data.model.response.DefaultResponse
import com.PortalDesa.data.model.response.ProductResponse
import com.PortalDesa.data.support.Connectivity
import com.PortalDesa.data.support.Flag
import com.PortalDesa.data.support.Preferences
import com.PortalDesa.data.support.Utils
import com.PortalDesa.data.ui.main.activity.PesananActivity
import kotlinx.android.synthetic.main.activity_pemesanan_langsung.*
import retrofit2.Response
import java.util.*

class PemesananLangsung : AppActivity(), View.OnClickListener {
    private var productResponse: ProductResponse? = null
    lateinit var preferences: Preferences
    private var customerResponse: CustomerResponse? = null
    var metode: String =""
    var alamat: String =""
    override fun onCreate(savedInstanceState: Bundle?) {
        preferences = Preferences(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pemesanan_langsung)
        pesanan_langsung_btn_decr.setOnClickListener(this)
        pesanan_langsung_btn_incr.setOnClickListener(this)
        btn_transaksi_simpan.setOnClickListener(this)
        btn_transaksi_ubah.setOnClickListener(this)
        btn_pesan.setOnClickListener(this)
        initData()
        radio_group.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val radio: RadioButton = findViewById(checkedId)
                metode=radio.text.toString()
            })
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

    fun initData() {
        showProgressDialog()
        if (Connectivity().isNetworkAvailable(this)) {
            val client = APIServiceGenerator().createService
            val skus = intent.getStringExtra(Flag.SKU_PESANAN_PRODUK)
            val call = client.getProductBySku(skus)
            call.enqueue(object : retrofit2.Callback<ProductResponse> {
                override fun onResponse(
                    call: retrofit2.Call<ProductResponse>,
                    response: Response<ProductResponse>
                ) {
                    val listProduk = response.body()
                    productResponse = listProduk
                    displayProduct()
                    dismissProgressDialog()
                    checkAlamat()
                }

                override fun onFailure(call: retrofit2.Call<ProductResponse>, t: Throwable) {
                    dismissProgressDialog()
                    Log.i(this.javaClass.simpleName, " Requested API : " + call.request().body()!!)
                    Log.e(this.javaClass.simpleName, " Exceptions : $t")
                }
            })

        }
    }
    private fun getAlamatRequest(): CustomerRequest {
        val customerRequest = CustomerRequest()
        customerRequest.id = UUID.randomUUID().toString()
        val alam=transaksi_alamat.text.toString()
        customerRequest.alamat = transaksi_alamat.text.toString()
        customerRequest.sku = preferences.getSku()
        return customerRequest
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
                    reload()
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
                    reload()
                }

                override fun onFailure(call: retrofit2.Call<DefaultResponse>, t: Throwable) {
                    Log.i(this.javaClass.simpleName, " Requested API : " + call.request().body()!!)
                    Log.e(this.javaClass.simpleName, " Exceptions : $t")
                }
            })

        }
    }

    fun reload(){
        intent = Intent(this  , this::class.java)
        startActivity(intent)
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

    fun displayProduct() {
        pesanan_langsung_nama.setText(productResponse?.nama)
        pesanan_langsung_harga.setText(Utils().numberToIDR(productResponse!!.harga!!.toInt(), true))
        pesanan_langsung_jumlah.setText(intent.getStringExtra(Flag.JUMLAH_PESANAN_PRODUK))
    }

    fun btn_decr() {
        if (pesanan_langsung_jumlah.text.equals("1")) {
            Toast.makeText(this, "Minimum pesanan adalah 1", Toast.LENGTH_SHORT).show()
        } else {
            pesanan_langsung_jumlah.setText((Integer.parseInt(pesanan_langsung_jumlah.text.toString()) - 1).toString())
        }
    }

    fun btn_incr() {
        pesanan_langsung_jumlah.setText((Integer.parseInt(pesanan_langsung_jumlah.text.toString()) + 1).toString())

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
                    goToPesanan()
                }

                override fun onFailure(call: retrofit2.Call<DefaultResponse>, t: Throwable) {
                    Log.i(this.javaClass.simpleName, " Requested API : " + call.request().body()!!)
                    Log.e(this.javaClass.simpleName, " Exceptions : $t")
                }
            })

        }
    }
    fun goToPesanan() {
        val intent = Intent(this, PesananActivity::class.java)
        startActivity(intent)
    }

    fun getDataForRequest(): TransaksiRequest {
        val transaksiRequest = TransaksiRequest()

        transaksiRequest.id=UUID.randomUUID().toString()
        transaksiRequest.skuProduk = intent.getStringExtra(Flag.SKU_PESANAN_PRODUK)
        transaksiRequest.skuCustomer = preferences.getSku()
        transaksiRequest.alamat = transaksi_alamat.text.toString()
        transaksiRequest.harga = productResponse!!.harga!!.toInt()
        transaksiRequest.metode =metode
        transaksiRequest.resi = ""
        transaksiRequest.status =1
        return transaksiRequest
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            pesanan_langsung_btn_incr.id -> btn_incr()
            pesanan_langsung_btn_decr.id -> btn_decr()
            btn_transaksi_ubah.id -> ubahAlamat()
            btn_pesan.id -> check()
        }
    }
}
