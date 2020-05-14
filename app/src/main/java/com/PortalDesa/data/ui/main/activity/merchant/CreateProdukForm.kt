package com.PortalDesa.data.ui.main.activity.merchant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.PortalDesa.R
import com.PortalDesa.data.apiService.APIServiceGenerator
import com.PortalDesa.data.base.AppActivity
import com.PortalDesa.data.model.request.ProdukRequest
import com.PortalDesa.data.support.Preferences
import com.PortalDesa.data.ui.main.fragment.ProductFragment
import kotlinx.android.synthetic.main.activity_create_produk_form.*
import retrofit2.Callback
import retrofit2.Response

class CreateProdukForm : AppActivity(), View.OnClickListener {
    var sku: String = ""
    var nickName: String =""
    lateinit var preferences: Preferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferences = Preferences(this)
        sku = preferences.getSku()
        nickName = preferences.getNamap()
        setContentView(R.layout.activity_create_produk_form)
        btn_produk_save.setOnClickListener(this)
    }

    fun save(request: ProdukRequest) {
        showProgressDialog()
        val client = APIServiceGenerator().createService
        val call = client.addProduk(request)
        call.enqueue(object : Callback<ProdukRequest> {
            override fun onResponse(
                call: retrofit2.Call<ProdukRequest>,
                response: Response<ProdukRequest>
            ) {
                goToProduk()
                finish()
            }

            override fun onFailure(call: retrofit2.Call<ProdukRequest>, t: Throwable) {
                dismissProgressDialog()
                goToProduk()
            }
        })

    }

    private fun getData(): ProdukRequest{
        val harga=produk_harga.text.toString()
        val requestUser = ProdukRequest()
        requestUser.nama = produk_nama.text.toString()
        requestUser.harga= Integer.parseInt(harga);
        requestUser.deskripsi= produk_deskripsi.text.toString()
        requestUser.skuDesa= sku;
        return requestUser
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            btn_produk_save.id -> save(getData())
        }
    }
    fun goToProduk(){
        val intent = Intent(this, ProductFragment::class.java)
        startActivity(intent)
    }
}
