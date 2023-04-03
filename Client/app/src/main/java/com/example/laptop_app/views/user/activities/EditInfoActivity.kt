package com.example.laptop_app.views.user.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.widget.Toast
import com.example.laptop_app.R
import com.example.laptop_app.controller.api.RetrofitInstance
import com.example.laptop_app.controller.db_local.DbLocal
import com.example.laptop_app.controller.views.Validate
import com.example.laptop_app.databinding.ActivityEditBinding
import com.example.laptop_app.databinding.ActivityEditInfoBinding
import com.example.laptop_app.model.User
import com.example.laptop_app.views.shared.activities.LoginActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showData()
        setOnclick()
    }

    private fun showData(): Unit {
        val user: User? = DbLocal.getUserCurrentLogged(applicationContext)
        binding.inputUserName.setText(user?.getUserName())
        binding.inputEmail.setText(user?.getEmail())
        binding.inputPhoneNumber.setText(user?.getPhoneNumber())
        binding.inputAddress.setText(user?.getAddress())
    }

    private fun setOnclick(): Unit {
        binding.buttonSaveChange.setOnClickListener { update() }
    }

    private fun update(): Unit {
        val userName: String = binding.inputUserName.text.toString().trim()
        val email: String = binding.inputEmail.text.toString().trim()
        val phoneNumber: String = binding.inputPhoneNumber.text.toString().trim()
        val address: String = binding.inputAddress.text.toString().trim()

        val isNull: Boolean = Validate.isNull(userName, email, phoneNumber, address) || phoneNumber.matches("null".toRegex()) || address.matches("null".toRegex())
        val isPhoneNumber: Boolean = Validate.isPhoneNumber(phoneNumber)
        val isEmail: Boolean = Validate.isEmail(email)
        val isUserName: Boolean = Validate.isUserName(userName)
        val isBreaking: Boolean = Validate.isBreakingUpdateInfoUser(applicationContext, isNull, isPhoneNumber, isEmail, isUserName)
        if (isBreaking) return

        handleUpdate()
    }

    private fun handleUpdate(): Unit {
        val user: User? = DbLocal.getUserCurrentLogged(applicationContext)
        val userName: String = binding.inputUserName.text.toString().trim()
        val email: String = binding.inputEmail.text.toString().trim()
        val phoneNumber: String = binding.inputPhoneNumber.text.toString().trim()
        val address: String = binding.inputAddress.text.toString().trim()

        user?.setUserName(userName)
        user?.setEmail(email)
        user?.setPhoneNumber(phoneNumber)
        user?.setAddress(address)

        if (user != null) {
            val call = RetrofitInstance.apiService.updateUser(user)
            call.enqueue(object: Callback<Int> {
                override fun onResponse(call: Call<Int>?, response: Response<Int>?) {
                    if (response != null) {
                        binding.buttonSaveChange.isEnabled = false
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

                override fun onFailure(call: Call<Int>?, t: Throwable?) {
                    Toast.makeText(
                        applicationContext,
                        "Khong co phan hoi tu may chu!",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            })
        }
    }
}