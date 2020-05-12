package com.PortalDesa.data.support

import android.content.Context
import android.content.SharedPreferences
import android.provider.ContactsContract

class Preferences(mContext: Context) {

    val TAG = "Preferences"

    private lateinit var mContext: Context

    lateinit var mSharedPreferences: SharedPreferences

    val TOKEN = "token"

    val SKU  = "Unknown"

    val ROLES = ""

    val NAMA = ""

    init {
        mSharedPreferences = mContext.getSharedPreferences("portal_preference", 0)
    }
    fun setName(nama: String?) {
        val e = mSharedPreferences.edit()
        e.putString(NAMA, nama)
        e.apply()
    }fun getNama(): String {
        val namas = mSharedPreferences.getString(NAMA, "")
        return namas!!
    }

    fun setROLES(roles: String?) {
        val e = mSharedPreferences.edit()
        e.putString(ROLES, roles)
        e.apply()
    }fun getRoles(): String {
        val roles = mSharedPreferences.getString(ROLES, "")
        return roles!!
    }

    fun setSku(sku: String?) {
        val e = mSharedPreferences.edit()
        e.putString(SKU, sku)
        e.apply()
    }fun getSku(): String {
        val skus = mSharedPreferences.getString(SKU, "")
        return skus!!
    }

    fun clearAll() {
        val e = mSharedPreferences.all
        e.clear()
    }
    /**
     * Save data Token
     *
     * @param token
     */
    fun setToken(token: String?) {
        val e = mSharedPreferences.edit()
        e.putString(TOKEN, "Bearer "+token)
        e.apply()
    }

    /**
     * Clear data Token
     */
    fun clearToken() {
        val e = mSharedPreferences.edit()
        e.putString(TOKEN, "")
        e.commit()
    }

    /**
     * Get Access Token
     *
     * @return accessToken
     */
    fun getAccessToken(): String {
        val accessToken = mSharedPreferences.getString(TOKEN, "")

        return accessToken!!
    }
}