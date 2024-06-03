package com.olabode.wilson.caresaas

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes


fun Context.showToast(@StringRes resId: Int) {
    Toast.makeText(this, getString(resId), Toast.LENGTH_SHORT).show()
}

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}