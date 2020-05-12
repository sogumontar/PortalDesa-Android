package com.PortalDesa.data.support

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.androidadvance.topsnackbar.TSnackbar
import com.PortalDesa.R

class TopSnackBar {

    fun showError(context: Context, view: View, message: String) {
        try {
            val snackbar = TSnackbar
                .make(view, message, TSnackbar.LENGTH_LONG)
            snackbar.setActionTextColor(Color.WHITE)
            val snackbarView = snackbar.view
            snackbarView.setBackgroundColor(ContextCompat.getColor(context, R.color.snackbar_red))
            val textView =
                snackbarView.findViewById<View>(com.androidadvance.topsnackbar.R.id.snackbar_text) as TextView
            textView.setTextColor(Color.WHITE)
            snackbar.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}