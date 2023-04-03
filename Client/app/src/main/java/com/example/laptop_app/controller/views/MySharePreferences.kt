package com.example.laptop_app.controller.views

import android.content.Context
import android.content.SharedPreferences
import com.example.laptop_app.others.Const

class MySharePreferences(
    var context: Context
) {
    private val mySharePreferences: String = Const.MY_SHARED_PREFERENCES

    fun putBoolean(key: String, value: Boolean) {
        var sharedPreferences: SharedPreferences = context.getSharedPreferences(mySharePreferences, Context.MODE_PRIVATE)
        var editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun getBooleanValue(key: String): Boolean {
        var sharedPreferences: SharedPreferences = context.getSharedPreferences(mySharePreferences, Context.MODE_PRIVATE)

        return sharedPreferences.getBoolean(key, false)
    }
}