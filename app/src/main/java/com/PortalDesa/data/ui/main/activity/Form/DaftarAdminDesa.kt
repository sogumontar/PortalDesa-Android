package com.PortalDesa.data.ui.main.activity.Form

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.PortalDesa.R
import com.PortalDesa.data.apiService.APIServiceGenerator
import com.PortalDesa.data.base.AppActivity
import com.PortalDesa.data.model.request.DaftarAdminDesaRequest
import com.PortalDesa.data.model.response.DefaultResponse
import com.PortalDesa.data.model.response.KecamatanResponse
import com.PortalDesa.data.support.Connectivity
import com.PortalDesa.data.ui.main.activity.admin.MainActivityAdmin
import kotlinx.android.synthetic.main.activity_daftar_admin_desa.*
import kotlinx.android.synthetic.main.toolbar.*
import retrofit2.Callback
import retrofit2.Response


class DaftarAdminDesa : AppActivity(), View.OnClickListener {
    private var listKecamatan: List<KecamatanResponse>? = null
    private var listNamaKecamatan: ArrayList<String> = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_daftar_admin_desa)
        btn_admin_daftar_merchant.setOnClickListener(this)
        et_kecamatan.setOnClickListener(this)
        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    et_kecamatan.setText(spinner.selectedItem.toString())
            }

        }

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
    fun initAdapter() {
        hideKeyboard(this)
        if(listNamaKecamatan.size>0){
            showDataKecamatan(listNamaKecamatan)
        }else {
            showProgressDialog()
            if (Connectivity().isNetworkAvailable(this)) {
                val client = APIServiceGenerator().createService
                val call = client.getKecamatanList()
                call.enqueue(object : Callback<List<KecamatanResponse>> {
                    override fun onResponse(
                        call: retrofit2.Call<List<KecamatanResponse>>,
                        response: Response<List<KecamatanResponse>>
                    ) {
                        val listKecamatanResponse = response.body()

                        for (list in 1..listKecamatanResponse!!.size) {
                            listNamaKecamatan.add(listKecamatanResponse.get(list - 1).nama.toString())
                        }
                        showDataKecamatan(listNamaKecamatan)
                    }

                    override fun onFailure(
                        call: retrofit2.Call<List<KecamatanResponse>>,
                        t: Throwable
                    ) {
                        dismissProgressDialog()

                    }
                })
            }
        }
    }

    private fun showDataKecamatan(list : ArrayList<String>){

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item, list)
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

    private fun initView() {
        initToolbar(R.id.toolbar)
        tv_toolbar_title.text = "Daftar Admin Desa"
    }

    fun simpan() {
        showProgressDialog()
        if (Connectivity().isNetworkAvailable(this)) {
            val client = APIServiceGenerator().createService
            val call = client.createDataMerchant(getData())
            call.enqueue(object : retrofit2.Callback<DefaultResponse> {
                override fun onResponse(
                    call: retrofit2.Call<DefaultResponse>,
                    response: Response<DefaultResponse>
                ) {
                    goToMainActivity()
                }

                override fun onFailure(call: retrofit2.Call<DefaultResponse>, t: Throwable) {
                    Log.i(this.javaClass.simpleName, " Requested API : " + call.request().body()!!)
                    Log.e(this.javaClass.simpleName, " Exceptions : $t")
                }
            })
        }
    }

    fun goToMainActivity() {
        intent = Intent(this, MainActivityAdmin::class.java)
        startActivity(intent)
    }

    fun getData(): DaftarAdminDesaRequest {
        val daftarAdminDesaRequest = DaftarAdminDesaRequest()
        daftarAdminDesaRequest.nama = namaDesa.text.toString()
//        daftarAdminDesaRequest.kecamatan = kecamatan.text.toString()
        daftarAdminDesaRequest.kecamatan = et_kecamatan.text.toString()
        daftarAdminDesaRequest.username = username.text.toString()
        daftarAdminDesaRequest.email = email.text.toString()
        daftarAdminDesaRequest.password = password.text.toString()
        daftarAdminDesaRequest.confirmPassword = confirmPassword.text.toString()
        return daftarAdminDesaRequest
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            btn_admin_daftar_merchant.id -> simpan()
            et_kecamatan.id -> initAdapter()
        }
    }
}
