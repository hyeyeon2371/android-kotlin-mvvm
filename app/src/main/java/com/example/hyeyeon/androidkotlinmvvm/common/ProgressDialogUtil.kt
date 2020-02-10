package com.example.hyeyeon.androidkotlinmvvm.common

import android.content.Context
import android.support.v7.app.AppCompatDialog

class ProgressDialogUtil(private val context: Context, private val theme: Int) {
    private lateinit var dialog: AppCompatDialog

    fun show() {
        if (!::dialog.isInitialized)
            dialog = AppCompatDialog(context, theme)
        dialog.show()
    }

    fun hide() {
        if (::dialog.isInitialized && dialog.isShowing) {
            dialog.dismiss()
        }
    }
}