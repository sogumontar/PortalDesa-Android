package com.PortalDesa.data.ui.main.activity.Form

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.PortalDesa.R
import com.PortalDesa.data.apiService.APIServiceGenerator
import com.PortalDesa.data.base.AppActivity
import com.PortalDesa.data.model.request.ArtikelRequest
import com.PortalDesa.data.model.response.DefaultResponse
import com.PortalDesa.data.support.Connectivity
import com.PortalDesa.data.support.Preferences
import com.PortalDesa.data.ui.main.activity.ArtikelActivity
import kotlinx.android.synthetic.main.activity_tambah_artikel.*
import retrofit2.Response

class TambahArtikelActivity : AppActivity(), View.OnClickListener  {
    var jenis =
        arrayOf("Artikel", "Berita", "Pengumuman")

    lateinit var preferences: Preferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_artikel)
        jenisVal.setOnClickListener(this)
        btn_simpan_artikel.setOnClickListener(this)
        preferences = Preferences(this)
        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                jenisVal.setText(spinner.selectedItem.toString())
            }

        }
    }

    private fun showDataKecamatan(){
        hideKeyboard(this)
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item, jenis)
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Apply the adapter to the spinner
        spinner.adapter = adapter
        try {
            spinner.performClick()
        } catch (e: Exception) {
        }
        dismissProgressDialog()
    }
    fun hideKeyboard(activity: Activity) {
        val imm: InputMethodManager =
            activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view = activity.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun simpan() {
        showProgressDialog()
        if (Connectivity().isNetworkAvailable(this)) {
            val client = APIServiceGenerator().createService
            val sku = preferences.getSku()
            val call = client.createArtikel(sku,getData())
            call.enqueue(object : retrofit2.Callback<DefaultResponse> {
                override fun onResponse(
                    call: retrofit2.Call<DefaultResponse>,
                    response: Response<DefaultResponse>
                ) {
                    goToArtikel()
                }

                override fun onFailure(call: retrofit2.Call<DefaultResponse>, t: Throwable) {
                    Log.i(this.javaClass.simpleName, " Requested API : " + call.request().body()!!)
                    Log.e(this.javaClass.simpleName, " Exceptions : $t")
                }
            })
        }
    }

    fun goToArtikel() {
        intent = Intent(this, ArtikelActivity::class.java)
        startActivity(intent)
    }

    fun getData(): ArtikelRequest {
        val artikelRequest = ArtikelRequest()
        artikelRequest.judul = judul.text.toString()
        artikelRequest.isi = isi.text.toString()
        artikelRequest.jenis = jenisVal.text.toString()
        artikelRequest.penulis = penulis.text.toString()
        artikelRequest.sumber = sumber.text.toString()
        return artikelRequest
    }


    override fun onClick(v: View?) {
        when (v!!.id) {
            jenisVal.id -> showDataKecamatan()
            btn_simpan_artikel.id -> simpan()
        }
    }

}
