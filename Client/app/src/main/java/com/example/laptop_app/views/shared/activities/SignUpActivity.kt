package com.example.laptop_app.views.shared.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.laptop_app.controller.api.RetrofitInstance
import com.example.laptop_app.controller.database_firebase.Authentication
import com.example.laptop_app.controller.views.Validate
import com.example.laptop_app.databinding.ActivitySignUpBinding
import com.example.laptop_app.model.User
import com.example.laptop_app.others.Const
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setOnclick()
    }

    private fun setOnclick(): Unit {
        binding.imageBack.setOnClickListener {
            startActivity(Intent(applicationContext, LoginActivity::class.java))
            finishAffinity()
        }
        binding.textLogin.setOnClickListener {
            startActivity(Intent(applicationContext, LoginActivity::class.java))
            finishAffinity()
        }
        binding.buttonSignUp.setOnClickListener { signUp() }
    }

    private fun signUp(): Unit {
        val email: String = binding.inputEmail.text.toString().trim()
        val pass: String = binding.inputPassword.text.toString().trim()
        val userName: String = binding.inputUserName.text.toString().trim()

        val isNull: Boolean = Validate.isNull(email, pass, userName)
        val isEmail: Boolean = Validate.isEmail(email)
        val isUserName: Boolean = Validate.isUserName(userName)
        val isPassword: Boolean = Validate.isPassword(pass)
        val isBreaking: Boolean = Validate.isBreakingSignUp(this, isNull, isEmail, isUserName, isPassword)

        if (isBreaking) return

        handleRegister(email, userName, pass)
    }

    private fun handleRegister(vararg input: String): Unit {
        val email = input[0]
        val userName = input[1]
        val pass = input[2]

        val user: User = User(
            Authentication.genIdAuto(), email, userName, pass, "null", "null", Const.linkAvatarDefault
        )

        val call = RetrofitInstance.apiService.handleRegister(user)
        call.enqueue(object: Callback<User> {
            override fun onResponse(call: Call<User>?, response: Response<User>?) {
                if (response != null) {
                    if (response.isSuccessful) {
                        finish()
                    }
                } else {
                    Toast.makeText(applicationContext, "Khong the tao tai khoan do gap su co!", Toast.LENGTH_SHORT).show()
                    Log.e("ErrorSignUp", "response == null")
                    return
                }
            }

            override fun onFailure(call: Call<User>?, t: Throwable?) {
                Toast.makeText(applicationContext, "Tao tai khoan khong thanh cong!", Toast.LENGTH_SHORT).show()
            }
        })
    }
}