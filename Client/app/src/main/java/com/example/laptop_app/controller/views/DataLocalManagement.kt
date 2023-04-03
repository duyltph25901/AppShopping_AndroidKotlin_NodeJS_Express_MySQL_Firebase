package com.example.laptop_app.controller.views

import android.content.Context
import com.example.laptop_app.others.Const

object DataLocalManagement {
    private val prefFirstInstall: String = Const.PREF_FIRST_INSTALL
    private var instance: DataLocalManagement? = null
    private lateinit var mySharePreferences: MySharePreferences

    fun init(context: Context): Unit {
        instance = DataLocalManagement
        instance!!.mySharePreferences = MySharePreferences(context)
    }

    private fun getInstance(): DataLocalManagement = instance ?: DataLocalManagement

    fun setFirstInstall(isFirstInstall: Boolean) {
        DataLocalManagement.getInstance().mySharePreferences.putBoolean(prefFirstInstall, isFirstInstall)
    }

    fun getFistInstall(): Boolean = DataLocalManagement.getInstance().mySharePreferences.getBooleanValue(prefFirstInstall)
}