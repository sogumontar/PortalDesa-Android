package com.PortalDesa.data.ui.main.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.get
import com.PortalDesa.R
import com.PortalDesa.data.apiService.APIServiceGenerator
import com.PortalDesa.data.base.AppActivity
import com.PortalDesa.data.model.response.DefaultResponse
import com.PortalDesa.data.model.response.PenginapanResponse
import com.PortalDesa.data.support.*
import com.PortalDesa.data.ui.main.activity.merchant.UpdatePenginapan
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_penginapan.*
import retrofit2.Response
import java.time.Month
import java.util.*

class DetailPenginapanActivity : AppActivity() {

    private var penginapanResponse: PenginapanResponse? = null
    lateinit var preferences: Preferences
    var role: String? = ""
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
        initView()
        initData();
//        tv_nama.text = name.toString()

        btn_pesan.setOnClickListener {
            goToPemesananLangsung()
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
        if (role.equals("ROLE_MERCHANT")) {
            produk_delete_btn.visibility = View.VISIBLE
            produk_update_btn.visibility = View.VISIBLE
            btn_pesan.visibility = View.GONE
            date.visibility= View.GONE
            lama.visibility=View.GONE
        } else if (role.equals("ROLE_USER")) {
            produk_delete_btn.visibility = View.GONE
            produk_update_btn.visibility = View.GONE
            btn_pesan.visibility = View.VISIBLE
        } else {
            produk_delete_btn.visibility = View.GONE
            produk_update_btn.visibility = View.GONE
            btn_pesan.visibility = View.GONE
        }
    }

    fun displayProduct() {
        Picasso.get()
            .load("https://portal-desa.herokuapp.com" + penginapanResponse?.gambar)
            .into(img_icon)
        tv_nama.setText(penginapanResponse?.nama)
        tv_harga.setText(Utils().numberToIDR(penginapanResponse!!.harga!!.toInt(), true))
        tv_desc.setText(penginapanResponse?.deskripsi)
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
    fun goToUpdateForm(){
        intent = Intent(this, UpdatePenginapan::class.java)
        intent.putExtra(Flag.SKU_PENGINAPAN,skuFix)
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
        val yearNow =Calendar.getInstance().get(Calendar.YEAR)
        val monthNow =Calendar.getInstance().get(Calendar.MONTH)+1
        val dayNow =Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        var days=0
        var months=0
        var years=0

        datePicker1.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH)

        ) { view, year, month, day ->
                days = day
                months = month
                years = year
            val msg = "You Selected: $day/$month/$year"
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

        }
        val month = datePicker1.month + 1
        val msg = "You Selected: "
        val juml = et_jumlah.text
        if(yearNow > years){
            Toast.makeText(this, "Masa tersebut telah lewat, silahkan pilih tanggal yang akan datang", Toast.LENGTH_SHORT).show()
        }else if(monthNow > months){
            Toast.makeText(this, "Masa tersebut telah lewat, silahkan pilih tanggal yang akan datang", Toast.LENGTH_SHORT).show()
        }else if(dayNow > days){
            Toast.makeText(this, "Masa tersebut telah lewat, silahkan pilih tanggal yang akan datang", Toast.LENGTH_SHORT).show()
        }else if(et_jumlah.text.equals("")){
            Toast.makeText(this, "Masukkan data lama menginap", Toast.LENGTH_SHORT).show()
        }else {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }

    }

}
