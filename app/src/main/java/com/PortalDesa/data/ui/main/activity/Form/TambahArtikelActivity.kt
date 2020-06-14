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
import com.PortalDesa.data.support.FormValidation
import com.PortalDesa.data.support.Preferences
import com.PortalDesa.data.support.TopSnackBar
import com.PortalDesa.data.ui.main.activity.ArtikelActivity
import kotlinx.android.synthetic.main.activity_tambah_artikel.*
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Response

class TambahArtikelActivity : AppActivity(), View.OnClickListener {
    var jenis =
        arrayOf("Artikel", "Berita", "Pengumuman")
    lateinit var topSnackBar: TopSnackBar
    lateinit var preferences: Preferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_artikel)
        jenisVal.setOnClickListener(this)
        btn_simpan_artikel.setOnClickListener(this)
        preferences = Preferences(this)
        initView()
        topSnackBar = TopSnackBar()
        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                jenisVal.setText(spinner.selectedItem.toString())
            }

        }
    }

    fun initView() {
        initToolbar(R.id.toolbar)
        tv_toolbar_title.text = "Tambah Artikel"
    }

    private fun showDataKecamatan() {
        hideKeyboard(this)
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item, jenis
        )
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
        if (checkField()) {
            showProgressDialog()
            if (Connectivity().isNetworkAvailable(this)) {
                val client = APIServiceGenerator().createService
                val sku = preferences.getSku()
                val call = client.createArtikel(sku, getData())
                call.enqueue(object : retrofit2.Callback<DefaultResponse> {
                    override fun onResponse(
                        call: retrofit2.Call<DefaultResponse>,
                        response: Response<DefaultResponse>
                    ) {
                        goToArtikel()
                    }

                    override fun onFailure(call: retrofit2.Call<DefaultResponse>, t: Throwable) {
                        Log.i(
                            this.javaClass.simpleName,
                            " Requested API : " + call.request().body()!!
                        )
                        Log.e(this.javaClass.simpleName, " Exceptions : $t")
                    }
                })
            }
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

    private fun checkField(): Boolean {
        var check = true
        if (!FormValidation().required(judul.getText().toString())) {
            showMessage("Judul tidak boleh kosong")
            check = false
        } else if (!FormValidation().required(jenisVal.getText().toString())) {
            showMessage("Jenis tidak boleh kosong")
            check = false
        } else if (!FormValidation().required(isi.getText().toString())) {
            showMessage("Isi tidak boleh kosong")
            check = false
        } else if (!FormValidation().required(sumber.getText().toString())) {
            showMessage("Sumber tidak boleh kosong")
            check = false
        } else if (!FormValidation().required(penulis.getText().toString())) {
            showMessage("Penulis tidak boleh kosong")
            check = false
        }

        return check
    }


    private fun showMessage(message: String) {
        topSnackBar.showError(this, findViewById(R.id.snackbar_container), message)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            jenisVal.id -> showDataKecamatan()
            btn_simpan_artikel.id -> simpan()
        }
    }

}
