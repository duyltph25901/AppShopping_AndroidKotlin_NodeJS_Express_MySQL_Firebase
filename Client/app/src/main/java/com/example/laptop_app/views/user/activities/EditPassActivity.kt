package com.example.laptop_app.views.user.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.telecom.Call
import android.text.InputType
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.laptop_app.controller.api.RetrofitInstance
import com.example.laptop_app.controller.db_local.DbLocal
import com.example.laptop_app.controller.views.Validate
import com.example.laptop_app.databinding.ActivityEditBinding
import com.example.laptop_app.model.User
import com.example.laptop_app.views.shared.activities.LoginActivity
import retrofit2.Callback
import retrofit2.Response

class EditPassActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditBinding
    private var user: User? = null
    private var currentHideShow: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
        setOnclick()
    }

    private fun init(): Unit {
        user = DbLocal.getUserCurrentLogged(applicationContext)
    }

    private fun setOnclick(): Unit {
        binding.imageX.setOnClickListener { finish() }
        binding.textHideShow.setOnClickListener { hideShow() }
        binding.buttonUpdate.setOnClickListener { update() }
    }

    private fun hideShow(): Unit {
        ++currentHideShow
        /*
        *   currentHideShow == 1 => hide
        *   currentHideShow == 2 => show
        * */
        /*
        *   currentHideShow == 1 => hide
        *   currentHideShow == 2 => show
        * */if (currentHideShow % 2 == 0) {
            binding.inputPassCurrent.inputType = InputType.TYPE_CLASS_TEXT
            binding.textHideShow.text = "Ẩn"
        } else {
            binding.inputPassCurrent.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            binding.textHideShow.text = "Hiện"
        }
    }

    private fun update(): Unit {
        val currentPass: String = binding.inputPassCurrent.text.toString().trim()
        val newPass: String = binding.inputNewPass.text.toString().trim()
        val confirmPass: String = binding.inputPassConfirm.text.toString().trim()

        val isNull: Boolean = Validate.isNull(currentPass, newPass, confirmPass)
        val isMatchPass: Boolean =
            user?.getPassword()?.toRegex()?.let { currentPass.matches(it) } == true
        val isPassword: Boolean = Validate.isPassword(newPass)
        val isConfirmPass: Boolean = Validate.isConfirm(newPass, confirmPass)
        val isBreaking: Boolean = Validate.isBreakingUpdateUser(
            applicationContext,
            isNull,
            isPassword,
            isConfirmPass,
            isMatchPass
        )

        if (isBreaking) return

        user?.setPass(newPass)
        handleUpdate()
    }

    private fun handleUpdate(): Unit {
        if (user != null) {
            val call = RetrofitInstance.apiService.updateUser(user!!)
            call.enqueue(object : Callback<Int> {
                override fun onResponse(call: retrofit2.Call<Int>?, response: Response<Int>?) {
                    if (response != null) {
                        enableButton()
                        Toast.makeText(
                            applicationContext,
                            "Cap nhat thanh cong\nHe thong se tu dong dang xuat trong 7s",
                            Toast.LENGTH_SHORT
                        ).show()
                        Handler(Looper.getMainLooper()).postDelayed({
                            startActivity(Intent(applicationContext, LoginActivity::class.java))
                        }, 7000)
                    }
                }

                override fun onFailure(call: retrofit2.Call<Int>?, t: Throwable?) {
                    Toast.makeText(
                        applicationContext,
                        "Khong co phan hoi tu may chu!",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            })
        }
    }

    private fun enableButton(): Unit {
        binding.buttonUpdate.isEnabled = false
        binding.imageX.isEnabled = false
    }
}