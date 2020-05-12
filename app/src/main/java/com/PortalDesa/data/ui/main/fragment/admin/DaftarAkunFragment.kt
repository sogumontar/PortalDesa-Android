package com.PortalDesa.data.ui.main.fragment.admin

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.PortalDesa.R
import com.PortalDesa.data.apiService.APIServiceGenerator
import com.PortalDesa.data.model.response.DaftarAkunResponse
import com.PortalDesa.data.ui.main.adapter.admin.DaftarAkunAdapter
import kotlinx.android.synthetic.main.activity_daftar_akun_fragment.*
import retrofit2.Response

class DaftarAkunFragment : Fragment() {
    companion object {

        fun newInstance(): DaftarAkunFragment {
            return DaftarAkunFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_daftar_akun_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_daftar_akun.setHasFixedSize(true)
        val daftarAkunLayoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        recycler_daftar_akun.setLayoutManager(daftarAkunLayoutManager)
        recycler_daftar_akun.setNestedScrollingEnabled(false)
        initView()
    }
    fun initView(){
        val client = APIServiceGenerator().createService
        val call = client.getDaftarAkunAdminList()
        call.enqueue(object : retrofit2.Callback<List<DaftarAkunResponse>> {
            override fun onResponse(
                call: retrofit2.Call<List<DaftarAkunResponse>>,
            response: Response<List<DaftarAkunResponse>>
            ) {
                val listKecamatan = response.body()
                val adapter = DaftarAkunAdapter(listKecamatan!!)
                recycler_daftar_akun.setAdapter(adapter)
            }

            override fun onFailure(call: retrofit2.Call<List<DaftarAkunResponse>>, t: Throwable) {
                Log.i(this.javaClass.simpleName, " Requested API : " + call.request().body()!!)
                Log.e(this.javaClass.simpleName, " Exceptions : $t")
            }
        })

    }
}
