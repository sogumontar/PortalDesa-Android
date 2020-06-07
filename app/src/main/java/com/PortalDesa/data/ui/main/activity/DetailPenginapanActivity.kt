package com.PortalDesa.data.ui.main.activity

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
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
import java.util.*

class DetailPenginapanActivity : AppActivity() {

    private var penginapanResponse: PenginapanResponse? = null
    lateinit var preferences: Preferences
    var role: String? = ""
    var metode: String = ""
    var skuLogin: String? = ""
    lateinit var topSnackBar: TopSnackBar
    var skuFix: String? = ""

    private val mYear = Calendar.getInstance()[Calendar.YEAR]
    private val mMonth = Calendar.getInstance()[Calendar.MONTH]
    private val mDay = Calendar.getInstance()[Calendar.DAY_OF_MONTH]

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
            val test : String = et_jumlah.text.toString()
            if(test==null || test==""){
                Toast.makeText(this, "Lama menginap tidak boleh kosong", Toast.LENGTH_LONG).show()
            } else if(metode.equals("")){
                Toast.makeText(this, "Metode pembayaran harus dipili", Toast.LENGTH_LONG).show()
            }else {
                simpanAlamat()
            }
        }
        produk_delete_btn.setOnClickListener {
            delete()
        }
        produk_update_btn.setOnClickListener {
            goToUpdateForm()
        }
        et_tanggal_in.setOnClickListener {
            createDialogDateIn()
        }
    }

    fun initData() {

        var today = Date()
        val sdf = SimpleDateFormat("dd/MM/yyyy ")
        val currentDate = sdf.format(Date())
        val day: Int = Integer.valueOf(Utils().formatDate(currentDate, "dd", "dd/MM/yyyy"))
        val month: Int = Integer.valueOf(Utils().formatDate(currentDate, "MM", "dd/MM/yyyy")) - 1
        val year: Int = Integer.valueOf(Utils().formatDate(currentDate, "yyyy", "dd/MM/yyyy"))
        et_tanggal_in.setText(""+day+"/"+month+"/"+year)
        et_jumlah.setText("1")
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
            et_tanggal_in.visibility = View.GONE
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

    private fun createDialogDateIn() {
        val listener =
            OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                val date: String
                val dateOut: String
                val month: String
                date = dayOfMonth.toString()
                dateOut = (dayOfMonth+1).toString()
                month = (monthOfYear + 1).toString()
                et_tanggal_in.setText("$date/$month/$year")

            }
        val currentDate: String = et_tanggal_in.getText().toString()
        var dpDialog = DatePickerDialog(this, listener, mYear, mMonth, mDay)
        if (currentDate != "") {
            val day: Int =
                Integer.valueOf(Utils().formatDate(currentDate, "dd", "dd/MM/yyyy"))
            val month: Int =
                Integer.valueOf(Utils().formatDate(currentDate, "MM", "dd/MM/yyyy")) - 1
            val year: Int =
                Integer.valueOf(Utils().formatDate(currentDate, "yyyy", "dd/MM/yyyy"))
            dpDialog = DatePickerDialog(this, listener, year, month, day)
        }
        dpDialog.datePicker.minDate = System.currentTimeMillis() - 1000
        dpDialog.show()
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
        intent.putExtra(Flag.ID_PESANAN, 3)
        startActivity(intent)
        finish()
    }

    fun getRequest(): TransaksiPenginapanRequest {
        val transaksiPenginapanRequest = TransaksiPenginapanRequest()
        transaksiPenginapanRequest.skuProduk = skuFix
        transaksiPenginapanRequest.skuCustomer = skuLogin
        transaksiPenginapanRequest.harga = (Integer.parseInt(penginapanResponse?.harga.toString()) * Integer.parseInt(et_jumlah.text.toString()))
        transaksiPenginapanRequest.metode = metode
        transaksiPenginapanRequest.lamaMenginap = Integer.parseInt(et_jumlah.text.toString())
        transaksiPenginapanRequest.checkin = et_tanggal_in.toString()
        return transaksiPenginapanRequest
    }


}
