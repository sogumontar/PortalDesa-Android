package com.loginkt.data.support

import android.content.Context
import android.content.SharedPreferences
import android.media.session.MediaSession
import com.google.gson.Gson

class Preferences(mContext: Context) {

    val TAG = "Preferences"

    private lateinit var mContext: Context

    lateinit var mSharedPreferences: SharedPreferences

    val TOKEN = "token"

    val ROLE = "Unknown"

    init {
        mSharedPreferences = mContext.getSharedPreferences("portal_preference", 0)
    }
    fun setRole(role: String?) {
        val e = mSharedPreferences.edit()
        e.putString(ROLE, role)
        e.apply()
    }fun clearAll() {
        val e = mSharedPreferences.all
        e.clear()
    }
    fun getRole(): String {
        val roles = mSharedPreferences.getString(ROLE, "")
        return roles!!
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