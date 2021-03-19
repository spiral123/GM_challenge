package com.example.gmchallenge.app.utils

import android.content.Context
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.text.DateFormatSymbols

val String.toDisplayDate: String
    get() {
        val month = DateFormatSymbols().months[this.substring(5, 7).toInt() - 1].take(3)
        return this.substring(8, 10) + " " + month + " " + this.substring(0, 4)
    }

fun View.hideKeyboardOnDrag() {
    this.setOnTouchListener { v, event ->
        v?.let {
            if (!v.hasFocus() && event != null && event.action == MotionEvent.ACTION_MOVE) {
                v.hideSoftKeyboard()
                v.requestFocus()
            }
        }
        v?.performClick()
        false
    }
}

fun View.hideSoftKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}