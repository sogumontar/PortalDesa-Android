package com.loginkt.data.support

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson

class Preferences {

    val TAG = "Preferences"

    private lateinit var mContext: Context

    lateinit var mSharedPreferences: SharedPreferences

    val TOKEN = "token"


    fun Preferences(mContext: Context){
        this.mContext = mContext
        mSharedPreferences = mContext.getSharedPreferences("portal_preference", 0)
    }

    /**
     * Save data Token
     *
     * @param token
     */
    fun setToken(token: String?) {
        val e = mSharedPreferences.edit()
        e.putString(Preferences().TOKEN, Gson().toJson(token))
        e.apply()
    }

    /**
     * Clear data Token
     */
    fun clearToken() {
        val e = mSharedPreferences.edit()
        e.putString(Preferences().TOKEN, "")
        e.commit()
    }

    /**
     * Get Access Token
     *
     * @return accessToken
     */
    fun getAccessToken(): String {
        val tokenJson = mSharedPreferences.getString(Preferences().TOKEN, "")
        var accessToken = ""
        accessToken = "Bearer " + tokenJson

        return accessToken
    }
}