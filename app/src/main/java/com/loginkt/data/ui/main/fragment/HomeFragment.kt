package com.loginkt.data.ui.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.loginkt.R
import com.loginkt.data.ui.main.adapter.PopularVilageAdapter
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    companion object {

        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    //3
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_popular.setHasFixedSize(true)
        val menuListLayoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        recycler_popular.setLayoutManager(menuListLayoutManager)
        recycler_popular.setNestedScrollingEnabled(false)
        initView()
    }

    fun initView(){
        val adapter = PopularVilageAdapter()
        recycler_popular.setAdapter(adapter)
    }
}