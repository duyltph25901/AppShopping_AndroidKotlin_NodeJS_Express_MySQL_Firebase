package com.example.laptop_app.views.user.fragments

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.telecom.Call
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.laptop_app.R
import com.example.laptop_app.adapter.rcv.CategoryAdapter
import com.example.laptop_app.adapter.rcv.LaptopAdapter
import com.example.laptop_app.controller.api.RetrofitInstance
import com.example.laptop_app.databinding.FragmentHomeBinding
import com.example.laptop_app.model.Category
import com.example.laptop_app.model.Product
import com.example.laptop_app.model.User
import com.example.laptop_app.others.Const
import com.example.laptop_app.views.user.activities.DetailsProductActivity
import com.example.laptop_app.views.user.activities.SearchActivity
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var categories: MutableList<Category>
    private lateinit var categoryAdapter: CategoryAdapter

    private lateinit var products: List<Product>
    private lateinit var productAdapter: LaptopAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // init view
        binding = FragmentHomeBinding.inflate(layoutInflater)

        init()
        getAllProduct()
        setOnclick()

        return binding.root
    }

    private fun init(): Unit {
        products = mutableListOf()
        categories = mutableListOf()

        categoryAdapter = CategoryAdapter(
            requireContext(),
            categories,
            object : CategoryAdapter.ClickItem {
                override fun clickItem(category: Category) {
                    getCategory(category.getId())
                }
            })
        productAdapter = LaptopAdapter(
            requireContext(),
            products,
            object : LaptopAdapter.ClickItem {
                override fun clickItem(product: Product) {
                    showDetailsProduct(product)
                }
            })

        categories = getCategories()
        categoryAdapter.setCategories(categories)
        binding.rcvCategory.adapter = categoryAdapter

        binding.rcvProduct.layoutManager = GridLayoutManager(requireContext(), 2)
    }

    private fun getCategories(): MutableList<Category> {
        val list: MutableList<Category> = mutableListOf()

        list.add(Category(Const.all, "All"))
        list.add(Category(Const.idMacbook, "Macbook"))
        list.add(Category(Const.idGamingLaptop, "Gaming"))
        list.add(Category(Const.idUltrabook, "Ultrabook"))

        return list
    }

    private fun getAllProduct(): Unit {

        val call = RetrofitInstance.apiService.getAllProduct()
        call.enqueue(object : Callback<List<Product>> {
            override fun onResponse(
                call: retrofit2.Call<List<Product>>?,
                response: Response<List<Product>>?
            ) {
                if (response != null) {
                    products = response.body()
                    loadData()
                }
            }

            override fun onFailure(call: retrofit2.Call<List<Product>>?, t: Throwable?) {
                Toast.makeText(requireContext(), "Khong the lam moi du lieu", Toast.LENGTH_SHORT)
                    .show()
            }

        })
    }

    private fun getMacbook(): Unit {

        val call = RetrofitInstance.apiService.getMacbook()
        call.enqueue(object : Callback<List<Product>> {
            override fun onResponse(
                call: retrofit2.Call<List<Product>>?,
                response: Response<List<Product>>?
            ) {
                if (response != null) {
                    products = response.body()
                    loadData()
                }
            }

            override fun onFailure(call: retrofit2.Call<List<Product>>?, t: Throwable?) {
                Toast.makeText(requireContext(), "Khong the lam moi du lieu", Toast.LENGTH_SHORT)
                    .show()
            }

        })
    }

    private fun getUltrabook(): Unit {

        val call = RetrofitInstance.apiService.getUltrabook()
        call.enqueue(object : Callback<List<Product>> {
            override fun onResponse(
                call: retrofit2.Call<List<Product>>?,
                response: Response<List<Product>>?
            ) {
                if (response != null) {
                    products = response.body()
                    loadData()
                }
            }

            override fun onFailure(call: retrofit2.Call<List<Product>>?, t: Throwable?) {
                Toast.makeText(requireContext(), "Khong the lam moi du lieu", Toast.LENGTH_SHORT)
                    .show()
            }

        })
    }

    private fun getGaming(): Unit {
        val call = RetrofitInstance.apiService.getGaming()
        call.enqueue(object : Callback<List<Product>> {
            override fun onResponse(
                call: retrofit2.Call<List<Product>>?,
                response: Response<List<Product>>?
            ) {
                if (response != null) {
                    products = response.body()
                    loadData()
                }
            }

            override fun onFailure(call: retrofit2.Call<List<Product>>?, t: Throwable?) {
                Toast.makeText(requireContext(), "Khong the lam moi du lieu", Toast.LENGTH_SHORT)
                    .show()
            }

        })
    }

    private fun loadData(): Unit {
        productAdapter.setProduct(products)
        binding.rcvProduct.adapter = productAdapter
    }

    private fun getCategory(index: Int): Unit {
        when (index) {
            Const.all -> getAllProduct()
            Const.idMacbook -> getMacbook()
            Const.idUltrabook -> getUltrabook()
            Const.idGamingLaptop -> getGaming()
            else -> getAllProduct()
        }
    }

    private fun showDetailsProduct(product: Product): Unit {
        val intent: Intent = Intent(requireContext(), DetailsProductActivity::class.java)
        val bundle: Bundle = Bundle()
        bundle.putSerializable(Const.intentProduct, product)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    private fun setOnclick(): Unit {
        binding.layoutSearch.setOnClickListener { startActivity(Intent(requireContext(), SearchActivity::class.java)) }
    }

    override fun onResume() {
        super.onResume()

        getAllProduct()
    }
}