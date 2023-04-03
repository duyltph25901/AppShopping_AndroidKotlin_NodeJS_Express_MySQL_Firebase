package com.example.laptop_app.views.shared.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.laptop_app.adapter.view_pager.OnBoardingAdapter
import com.example.laptop_app.databinding.ActivityOnBoardingBinding

class OnBoardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnBoardingBinding
    private lateinit var adapter: OnBoardingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
        setOnclick()
        slide()
    }

    private fun init() {
        binding.layoutContainer.isUserInputEnabled = false
        adapter = OnBoardingAdapter(this)
        binding.layoutContainer.adapter = adapter
        binding.circleIndicator.setViewPager(binding.layoutContainer)
    }

    private fun setOnclick() {
        binding.buttonLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finishAffinity()
        }
        binding.buttonSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
            finishAffinity()
        }
    }

    private fun slide() {
        binding.layoutContainer.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                Handler(Looper.getMainLooper())
                    .postDelayed({
                        var index = binding.layoutContainer.currentItem
                        if (index >= 2) {
                            index = 0
                        } else {
                            ++index
                        }
                        binding.layoutContainer.currentItem = index
                    }, 5000)
            }
        })
    }
}
