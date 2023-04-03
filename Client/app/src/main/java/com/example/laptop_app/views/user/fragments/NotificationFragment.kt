package com.example.laptop_app.views.user.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.laptop_app.R
import com.example.laptop_app.adapter.rcv.OrderAdapter
import com.example.laptop_app.controller.api.RetrofitInstance
import com.example.laptop_app.controller.db_local.DbLocal
import com.example.laptop_app.databinding.FragmentNotificationBinding
import com.example.laptop_app.model.Product
import com.example.laptop_app.model.User
import com.example.laptop_app.model.respone.ResOrder
import com.example.laptop_app.others.Const
import com.example.laptop_app.views.user.activities.DetailsProductActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NotificationFragment : Fragment() {

    private lateinit var binding: FragmentNotificationBinding
    private lateinit var orders: List<ResOrder>
    private lateinit var orderAdapter: OrderAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentNotificationBinding.inflate(layoutInflater)

        showData()
        init()
        getDatabase()

        return binding.root
    }

    private fun showData(): Unit {
        val user: User? = DbLocal.getUserCurrentLogged(requireContext())
        Glide.with(requireContext())
            .load(user?.getImage())
            .error(R.mipmap.ic_launcher)
            .into(binding.avatarUserCurrentLogged)
        binding.textUserNameCurrentLogged.text = user?.getUserName()
    }

    private fun init(): Unit {
        orders = ArrayList()
        orderAdapter = OrderAdapter(orders, requireContext(), object: OrderAdapter.ClickItem {
            override fun buyAgain(product: Product) {
                val intent: Intent = Intent(requireContext(), DetailsProductActivity::class.java)
                val bundle: Bundle = Bundle()
                bundle.putSerializable(Const.intentProduct, product)
                intent.putExtras(bundle)
                startActivity(intent)
            }

        })
        val layoutManager: LinearLayoutManager = LinearLayoutManager(requireContext())
        binding.rcvOrder.layoutManager = layoutManager
        binding.rcvOrder.adapter = orderAdapter
    }

    private fun getDatabase(): Unit {
        val call = RetrofitInstance.apiService.getOrders(DbLocal.getUserCurrentLogged(requireContext())?.getId() ?: "hello")
        call.enqueue(object: Callback<List<ResOrder>> {
            override fun onResponse(
                call: Call<List<ResOrder>>?,
                response: Response<List<ResOrder>>?
            ) {
                if (response != null) {
                    orders = response.body()
                    loadData()
                }
            }

            override fun onFailure(call: Call<List<ResOrder>>?, t: Throwable?) {
                Toast.makeText(requireContext(), "Khong the lam moi nguon du lieu", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun loadData(): Unit {
        orderAdapter.setOrders(orders)
        binding.rcvOrder.adapter = orderAdapter
    }

    override fun onResume() {
        super.onResume()

        getDatabase()
    }
}