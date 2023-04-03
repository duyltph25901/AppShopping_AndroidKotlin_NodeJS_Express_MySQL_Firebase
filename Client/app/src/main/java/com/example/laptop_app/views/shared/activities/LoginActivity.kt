package com.example.laptop_app.views.shared.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.laptop_app.model.MySystem
import android.widget.Toast
import com.example.laptop_app.controller.api.RetrofitInstance
import com.example.laptop_app.controller.db_local.DbLocal
import com.example.laptop_app.controller.views.Validate
import com.example.laptop_app.databinding.ActivityLoginBinding
import com.example.laptop_app.model.UserFound
import com.example.laptop_app.model.User
import com.example.laptop_app.others.Const
import com.example.laptop_app.views.user.activities.HomeActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setOnclick()
    }

    private fun setOnclick(): Unit {
        binding.textSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
        binding.imageBack.setOnClickListener {finish()}
//        binding.textForgotPass.setOnClickListener { forgotPassword() }
        binding.imageLogin.setOnClickListener {login()}
    }

    private fun forgotPassword(): Unit {

    }

    private fun login(): Unit {
        val email: String = binding.inputEmail.text.toString().trim()
        val pass: String = binding.inputPassword.text.toString().trim()

        val isNull: Boolean = Validate.isNull(email, pass)
        val isEmail: Boolean = Validate.isEmail(email)
        val isBreaking: Boolean = Validate.isBreakingLogin(applicationContext, isNull, isEmail)

        if (isBreaking) return

        val userFound = UserFound(email, pass)
        handleLogin(userFound)
    }

    private fun handleLogin(userFound: UserFound): Unit {
        val call = RetrofitInstance.apiService.handleLogin(userFound)

        call.enqueue(object: Callback<User> {
            override fun onResponse(call: Call<User>?, response: Response<User>?) {
                if (response != null) {
                    val user: User = response.body()
                    if (user != null) {
                        val result: Int = DbLocal.handleSaveUserCurrentLogged(user, applicationContext)
                        if (result == Const.success) {
                            startActivity(Intent(applicationContext, HomeActivity::class.java))
                            saveStatusSystem()
                        } else {
                            Toast.makeText(applicationContext, "Co loi xay ra", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(applicationContext, "Sai tai khoan", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(applicationContext, "Res == null", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<User>?, t: Throwable?) {
                Toast.makeText(applicationContext, "Sai tai khoan", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun saveStatusSystem(): Unit {
        val mySystem: MySystem = MySystem(true)
        DbLocal.saveStatusSystemCurrent(applicationContext, mySystem)
    }
}
