package com.PortalDesa.data.ui.main.activity

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
import com.PortalDesa.data.model.request.TransaksiPenginapanRequest
import com.PortalDesa.data.model.response.DefaultResponse
import com.PortalDesa.data.model.response.PenginapanResponse
import com.PortalDesa.data.support.*
import com.PortalDesa.data.ui.main.activity.merchant.UpdatePenginapan
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_penginapan.*
import retrofit2.Response
import java.text.DateFormat
import java.util.*

class DetailPenginapanActivity : AppActivity() {

    private var penginapanResponse: PenginapanResponse? = null
    lateinit var preferences: Preferences
    var role: String? = ""
    var metode: String = ""
    var skuLogin: String? = ""
    lateinit var topSnackBar: TopSnackBar
    var skuFix: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_penginapan)
        preferences = Preferences(this)
        topSnackBar = TopSnackBar()
        role = preferences.getRoles()
        skuLogin = preferences.getSku()

        initData()
//        tv_nama.text = name.toString()
        radio_group.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val radio: RadioButton = findViewById(checkedId)
                metode = radio.text.toString()
            })
        btn_pesan.setOnClickListener {
            simpanAlamat()
        }
        produk_delete_btn.setOnClickListener {
            delete()
        }
        produk_update_btn.setOnClickListener {
            goToUpdateForm()
        }
    }

    fun initData() {
        showProgressDialog()
        if (Connectivity().isNetworkAvailable(this)) {
            val client = APIServiceGenerator().createService
            skuFix = intent.getStringExtra(Flag.PENGINAPAN_ID)
            val call = client.lihatPenginapanBySku(skuFix!!)
            call.enqueue(object : retrofit2.Callback<PenginapanResponse> {
                override fun onResponse(
                    call: retrofit2.Call<PenginapanResponse>,
                    response: Response<PenginapanResponse>
                ) {
                    val listProduk = response.body()
                    penginapanResponse = listProduk
                    displayProduct()
                    initView()
                    dismissProgressDialog()
                }

                override fun onFailure(call: retrofit2.Call<PenginapanResponse>, t: Throwable) {
                    dismissProgressDialog()
                    Log.i(this.javaClass.simpleName, " Requested API : " + call.request().body()!!)
                    Log.e(this.javaClass.simpleName, " Exceptions : $t")
                }
            })

        }
    }

    fun initView() {
        val sku=preferences.getSku()
        val skuMrch=penginapanResponse!!.skuMerchant
        if (role.equals("ROLE_MERCHANT") && sku.equals(skuMrch) ) {
            produk_delete_btn.visibility = View.VISIBLE
            produk_update_btn.visibility = View.VISIBLE
            btn_pesan.visibility = View.GONE
            date.visibility = View.GONE
            lama.visibility = View.GONE
            radio_group.visibility=View.GONE
        } else if (role.equals("ROLE_USER")) {
            produk_delete_btn.visibility = View.GONE
            produk_update_btn.visibility = View.GONE
            btn_pesan.visibility = View.VISIBLE
        } else {
            radio_group.visibility=View.GONE
            produk_delete_btn.visibility = View.GONE
            produk_update_btn.visibility = View.GONE
            btn_pesan.visibility = View.GONE
        }

    }

    fun displayProduct() {
        Picasso.get()
            .load("https://portal-desa.herokuapp.com" + penginapanResponse?.gambar)
            .into(img_icon)
        tv_alamat.setText("Alamat :  " + penginapanResponse!!.lokasi)
        tv_nama.setText(penginapanResponse?.nama)
        tv_harga.setText(Utils().numberToIDR(penginapanResponse!!.harga!!.toInt(), true))
        tv_desc.setText("Deskripsi : " +penginapanResponse?.deskripsi)
    }


    private fun showMessage(message: String) {
        topSnackBar.showError(this, findViewById(R.id.snackbar_container), message)
    }


    fun goToKeranjang() {
        intent = Intent(this, KeranjangActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun reload() {
        intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun goToUpdateForm() {
        intent = Intent(this, UpdatePenginapan::class.java)
        intent.putExtra(Flag.SKU_PENGINAPAN, skuFix)
        startActivity(intent)
        finish()
    }

    fun delete() {
        if (Connectivity().isNetworkAvailable(this)) {
            val client = APIServiceGenerator().createService
            val call = client.deletePenginapan(skuFix!!)
            call.enqueue(object : retrofit2.Callback<DefaultResponse> {
                override fun onResponse(
                    call: retrofit2.Call<DefaultResponse>,
                    response: Response<DefaultResponse>
                ) {
                    reload()
                }

                override fun onFailure(call: retrofit2.Call<DefaultResponse>, t: Throwable) {

                }
            })

        }

    }

    fun goToPemesananLangsung() {
        val today = Calendar.getInstance()
        val yearNow = Calendar.getInstance().get(Calendar.YEAR)
        val monthNow = Calendar.getInstance().get(Calendar.MONTH) + 1
        val dayNow = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        var days = 0
        var months = 0
        var years = 0

        val datt = datePicker1.init(
            today.get(Calendar.YEAR), today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH)

        ) { view, year, month, day ->
            days = day
            months = month
            years = year
            val msg = "You Selected: $day/$month/$year"
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

        }
        val test = datt.toString()
        val month = datePicker1.month + 1
        val msg = "You Selected: "
        val juml = et_jumlah.text
        if(yearNow > (datePicker1.year+1)){
            Toast.makeText(this, "Masa tersebut telah lewat, silahkan pilih tanggal yang akan datang", Toast.LENGTH_SHORT).show()
        }else if(monthNow > datePicker1.month){
            Toast.makeText(this, "Masa tersebut telah lewat, silahkan pilih tanggal yang akan datang", Toast.LENGTH_SHORT).show()
        }else if(dayNow > datePicker1.dayOfMonth){
            Toast.makeText(this, "Masa tersebut telah lewat, silahkan pilih tanggal yang akan datang", Toast.LENGTH_SHORT).show()
        }else if(et_jumlah.text.equals("")){
            Toast.makeText(this, "Masukkan data lama menginap", Toast.LENGTH_SHORT).show()
        }else {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }
    }

    fun radio_button_click(view: View) {
        val radio: RadioButton = findViewById(radio_group.checkedRadioButtonId)
        if (metode.toString().equals("ATM Mandiri")) {
            metode = "ATM Mandiri"
            linear_metode.visibility = View.VISIBLE

            linear_metode_bri.visibility = View.GONE
        } else {
            metode = "ATM Mandiri"
            linear_metode.visibility = View.GONE
            linear_metode_bri.visibility = View.VISIBLE
        }
    }

    fun simpanAlamat(){
        showProgressDialog()
        if (Connectivity().isNetworkAvailable(this)) {
            val client = APIServiceGenerator().createService
            val call = client.addTransaksiPenginapan(getRequest())
            call.enqueue(object : retrofit2.Callback<DefaultResponse> {
                override fun onResponse(
                    call: retrofit2.Call<DefaultResponse>,
                    response: Response<DefaultResponse>
                ) {
                    goToPesanan()
                    dismissProgressDialog()
                }

                override fun onFailure(call: retrofit2.Call<DefaultResponse>, t: Throwable) {
                    Log.i(this.javaClass.simpleName, " Requested API : " + call.request().body()!!)
                    Log.e(this.javaClass.simpleName, " Exceptions : $t")
                }
            })

        }
    }


    fun goToPesanan(){
        val intent = Intent(this, PesananActivity::class.java)
        startActivity(intent)
    }

    fun getRequest(): TransaksiPenginapanRequest {
        val today = Calendar.getInstance()
//        val datt = datePicker1.init(
//            today.get(Calendar.YEAR), today.get(Calendar.MONTH),
//            today.get(Calendar.DAY_OF_MONTH)
//
//        ) { view, year, month, day ->
//            val msg = "You Selected: $day/$month/$year"
//            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
//
//        }

        val test = datePicker1.year.toString() + ',' + datePicker1.month.toString() + ',' + datePicker1.dayOfMonth
        val transaksiPenginapanRequest = TransaksiPenginapanRequest()
        val idCustomer = skuLogin
        transaksiPenginapanRequest.skuProduk = skuFix
        transaksiPenginapanRequest.skuCustomer = skuLogin
        transaksiPenginapanRequest.harga = (Integer.parseInt(penginapanResponse?.harga.toString()) * Integer.parseInt(et_jumlah.text.toString()))
        transaksiPenginapanRequest.metode = metode
        transaksiPenginapanRequest.lamaMenginap = Integer.parseInt(et_jumlah.text.toString())
        transaksiPenginapanRequest.checkin = test.toString()
        return transaksiPenginapanRequest
    }


}
