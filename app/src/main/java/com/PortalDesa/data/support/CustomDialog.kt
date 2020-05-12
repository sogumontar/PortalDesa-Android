package com.PortalDesa.data.support

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import com.PortalDesa.R
import kotlinx.android.synthetic.main.dialog_alert.*

class CustomDialog {
    fun alertDialog(
        context: Context,
        title: String,
        message: String,
        buttonText: String,
        isCancelable: Boolean
    ) {
        try {
            if (!(context as Activity).isFinishing) {
                // custom dialog
                val dialog = Dialog(context)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.setContentView(R.layout.dialog_alert)
                dialog.setCancelable(isCancelable)

                dialog.tv_title.text = title
                dialog.tv_message.text = message
                dialog.btn_primary.text = buttonText

                // if button is clicked, close the custom dialog
                dialog.btn_primary.setOnClickListener {
                    dialog.dismiss()
                }

//                ic_close.setOnClickListener { dialog.dismiss() }

                dialog.show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}