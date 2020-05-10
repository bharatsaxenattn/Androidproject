package com.example.album.utils

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import com.example.album.R


class CustomProgressBar {
    private var dialog: Dialog? = null

    fun show(context: Context): Dialog? {
        return show(context, false)
    }

    fun show(context: Context, cancelable: Boolean): Dialog? {
        return show(context, cancelable, null)
    }

    fun show(context: Context, cancelable: Boolean,
             cancelListener: DialogInterface.OnCancelListener?): Dialog? {
        val inflator = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = inflator.inflate(R.layout.progress_bar, null)

        dialog = Dialog(context, android.R.style.Theme_NoTitleBar)
        dialog!!.window!!.setBackgroundDrawableResource(android.R.color.transparent);
        dialog!!.setContentView(view)
        dialog!!.setCancelable(cancelable)
        dialog!!.setOnCancelListener(cancelListener)
        dialog!!.show()
        return dialog
    }

    fun getDialog(): Dialog? {
        return dialog
    }
}