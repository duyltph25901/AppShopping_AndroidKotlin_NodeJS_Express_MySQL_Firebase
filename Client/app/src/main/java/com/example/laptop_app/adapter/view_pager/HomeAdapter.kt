package com.example.laptop_app.adapter.view_pager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.laptop_app.views.user.fragments.AccountFragment
import com.example.laptop_app.views.user.fragments.CartFragment
import com.example.laptop_app.views.user.fragments.HomeFragment
import com.example.laptop_app.views.user.fragments.NotificationFragment

class HomeAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment =
        when (position) {
            0 -> HomeFragment()
            1 -> CartFragment()
            2 -> NotificationFragment()
            3 -> AccountFragment()
            else -> HomeFragment()
        }
}