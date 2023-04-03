package com.example.laptop_app.views.shared.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.laptop_app.R
import com.example.laptop_app.model.MySystem
import com.example.laptop_app.controller.db_local.system.SystemDatabase
import com.example.laptop_app.controller.views.DataLocalManagement
import com.example.laptop_app.views.user.activities.HomeActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        /**
         * Nếu là lần đầu tải app: Màn hình chờ => màn hình giới thiệu => login
         * Ngược lại: Màn hình chờ => check loged in : đã login: => Màn hình chính
         *                                             ngược lại: => login
         * */
        checkAppCase()
    }

    private fun checkAppCase() {
        if (!DataLocalManagement.getFistInstall()) {
            DataLocalManagement.setFirstInstall(true)
            navigation(OnBoardingActivity::class.java)
            Log.d("Message check install app", "This is first time install app")
        } else {
            Log.d("Message check install app", "Not the first time install app")
            val mySystem: MySystem? = SystemDatabase.getDatabase(applicationContext).SystemDao().getSystem()
            if (mySystem == null || !mySystem.isLogged()) {
                navigation(LoginActivity::class.java)
                finishAffinity()
            } else {
                navigation(HomeActivity::class.java)
            }
        }
    }

    private fun navigation(className: Class<*>) {
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, className))
        }, 3000)
    }
}