package com.PortalDesa.data.ui.main.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.PortalDesa.R
import com.PortalDesa.data.base.AppActivity
import com.PortalDesa.data.support.Preferences
import com.PortalDesa.data.ui.main.activity.admin.MainActivityAdmin
import com.PortalDesa.data.ui.main.fragment.AkunFragment
import com.PortalDesa.data.ui.main.fragment.HomeFragment
import com.PortalDesa.data.ui.main.fragment.PenginapanFragment
import com.PortalDesa.data.ui.main.fragment.ProductFragment
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppActivity(), View.OnClickListener {
    private var mTabPosition = 0
    lateinit var preferences: Preferences
    override fun onCreate(savedInstanceState: Bundle?) {
        preferences = Preferences(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tabSelected(0, "Beranda")
        tab_home.setOnClickListener(this)
        tab_product.setOnClickListener(this)
        tab_penginapan.setOnClickListener(this)
        tab_akun.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        val id = v.id
        if (id == tab_home.getId()) {
            tabSelected(0, "Beranda")
        } else if (id == tab_product.getId()) {
            tabSelected(1, "Product")
        } else if (id == tab_penginapan.getId()) {
            tabSelected(2, "Penginapan")
        }
        else if (id == tab_akun.getId()) {
            tabSelected(3, "Akun")
        }
    }

    fun tabSelected(position: Int, type: String) {
        if (!TextUtils.isEmpty(type)) {

            when (type) {
                "Beranda" -> displayFragment(HomeFragment(), R.id.fragment_container)
                "Product" -> displayFragment(ProductFragment(), R.id.fragment_container)
                "Penginapan" -> displayFragment(PenginapanFragment(), R.id.fragment_container)
                "Akun" -> displayFragment(AkunFragment(), R.id.fragment_container)

                else -> {
                }
            }

            updateTabView(position)
            mTabPosition = position
        }
    }

    private fun updateTabView(position: Int) {
        tab_home.setBackground(ContextCompat.getDrawable(this, R.color.blue_primary))
        tab_product.setBackground(ContextCompat.getDrawable(this, R.color.blue_primary))
        tab_penginapan.setBackground(ContextCompat.getDrawable(this, R.color.blue_primary))
        tab_akun.setBackground(ContextCompat.getDrawable(this, R.color.blue_primary))
        when (position) {
            0 -> tab_home.setBackground(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.button_orange_selector
                )
            )
            1 -> tab_product.setBackground(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.button_orange_selector
                )
            )
            2 -> tab_penginapan.setBackground(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.button_orange_selector
                )
            )
            3 -> tab_akun.setBackground(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.button_orange_selector
                )
            )
            else -> tab_home.setBackground(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.button_orange_selector
                )
            )
        }

    }


    /**
     * Display Fragment
     *
     * @param fragment
     * @param fragmentResourceID
     */
    fun displayFragment(fragment: Fragment, fragmentResourceID: Int) {
        try {
            showFragment(fragment, fragmentResourceID)
        } catch (e: IllegalStateException) {
            e.printStackTrace()

            showFragmentAllowingStateLoss(fragment, fragmentResourceID)
        }

    }

}
