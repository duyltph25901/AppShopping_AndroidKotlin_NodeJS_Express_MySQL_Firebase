package com.example.laptop_app.views.user.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.laptop_app.R
import com.example.laptop_app.adapter.rcv.BottomAdapter
import com.example.laptop_app.adapter.rcv.CategoryAdapter
import com.example.laptop_app.adapter.view_pager.HomeAdapter
import com.example.laptop_app.databinding.ActivityHomeBinding
import com.example.laptop_app.model.BottomBar
import com.example.laptop_app.model.Category
import com.example.laptop_app.others.Const
import com.example.laptop_app.views.user.fragments.AccountFragment
import com.example.laptop_app.views.user.fragments.CartFragment
import com.example.laptop_app.views.user.fragments.HomeFragment
import com.example.laptop_app.views.user.fragments.NotificationFragment

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var bottomBars: MutableList<BottomBar>
    private lateinit var fragments: MutableList<Fragment>
    private lateinit var bottomAdapter: BottomAdapter
    private lateinit var homeAdapter: HomeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityHomeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        init()
    }

    private fun init(): Unit {
        // init
        fragments = mutableListOf()
        bottomBars = mutableListOf()
        bottomAdapter = BottomAdapter(applicationContext, bottomBars, object: BottomAdapter.ClickItem {
            override fun clickItem(bottomBar: BottomBar) {
                replaceFragment(bottomBar.getId())
            }
        })

        homeAdapter = HomeAdapter(this)

        // show data
        bottomBars = getBottomBars()
        bottomAdapter.setBottomBar(bottomBars)
        binding.rcvBottomBar.adapter = bottomAdapter

        fragments = getFragment()
        binding.layoutContainer.adapter = homeAdapter
        binding.layoutContainer.isUserInputEnabled = false
    }

    private fun getBottomBars(): MutableList<BottomBar> {
        val list: MutableList<BottomBar> = mutableListOf()

        list.add(BottomBar(Const.bottomHome, R.drawable.icon_home, "Home"))
        list.add(BottomBar(Const.bottomCart, R.drawable.icon_cart, "Cart"))
        list.add(BottomBar(Const.bottomOrder, R.drawable.icon_order, "History order"))
        list.add(BottomBar(Const.bottomAccount, R.drawable.icon_user, "Account"))

        return list
    }

    private fun getFragment(): MutableList<Fragment> {
        val fragments: MutableList<Fragment> = mutableListOf()

        val homeFragment: HomeFragment = HomeFragment()
        val cartFragment: CartFragment = CartFragment()
        val notificationFragment: NotificationFragment = NotificationFragment()
        val accountFragment: AccountFragment = AccountFragment()

        fragments.add(homeFragment)
        fragments.add(cartFragment)
        fragments.add(notificationFragment)
        fragments.add(accountFragment)

        return fragments
    }

    private fun replaceFragment(index: Int) {
        binding.layoutContainer.currentItem = index
    }
}