package com.loginkt.data.ui.main.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.loginkt.R
import com.loginkt.data.ui.main.activity.LoginActivity
import com.loginkt.data.ui.main.activity.RegisterActivity
import com.loginkt.data.ui.main.adapter.PopularVilageAdapter
import com.loginkt.data.ui.main.adapter.ProfileAdapter
import kotlinx.android.synthetic.main.fragment_akun.*
import kotlinx.android.synthetic.main.fragment_home.*

class AkunFragment : Fragment() {

    companion object {

        fun newInstance(): AkunFragment {
            return newInstance()
        }
    }

    //3
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_akun, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ln_login.setOnClickListener() {
        val intent = Intent(activity!!, LoginActivity::class.java)
        startActivity(intent)
        }

        ln_register.setOnClickListener() {
        val intent = Intent(activity!!, RegisterActivity::class.java)
        startActivity(intent)
        }
        recycler_view.setHasFixedSize(true)
        val menuListLayoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        recycler_view.setLayoutManager(menuListLayoutManager)
        recycler_view.setNestedScrollingEnabled(false)
        initView()
    }

    fun initView(){
        val adapter = ProfileAdapter()
        recycler_view.setAdapter(adapter)
    }


}