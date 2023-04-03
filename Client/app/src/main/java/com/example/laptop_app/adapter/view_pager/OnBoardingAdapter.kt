package com.example.laptop_app.adapter.view_pager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.laptop_app.views.shared.fragments.OnB1Fragment
import com.example.laptop_app.views.shared.fragments.OnB2Fragment
import com.example.laptop_app.views.shared.fragments.OnB3Fragment

class OnBoardingAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> OnB1Fragment()
            1 -> OnB2Fragment()
            2 -> OnB3Fragment()
            else -> OnB1Fragment()
        }
    }
}