package com.example.laptop_app.views.user.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.laptop_app.R
import com.example.laptop_app.adapter.rcv.CartAdapter
import com.example.laptop_app.adapter.rcv.LaptopAdapter
import com.example.laptop_app.controller.api.RetrofitInstance
import com.example.laptop_app.databinding.ActivitySearchBinding
import com.example.laptop_app.model.Product
import com.example.laptop_app.model.ProductSearch
import com.example.laptop_app.others.Const
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding

    private lateinit var products: List<Product>
    private lateinit var productsName: List<String>
    private lateinit var productAdapter: LaptopAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
        getAllProductsName()
    }

    private fun init(): Unit {
        products = ArrayList()
        productsName = ArrayList()
        productAdapter = LaptopAdapter(this, products, object: LaptopAdapter.ClickItem{
            override fun clickItem(product: Product) {
                showDetailsProduct(product)
            }
        })

        val gridLayoutManager: GridLayoutManager = GridLayoutManager(applicationContext, 2)
        binding.rcvSearch.layoutManager = gridLayoutManager
        binding.rcvSearch.adapter = productAdapter
    }

    private fun getAllProductsName(): Unit {
        val call = RetrofitInstance.apiService.getAllProductName()
        call.enqueue(object: Callback<List<String>> {
            override fun onResponse(call: Call<List<String>>?, response: Response<List<String>>?) {
                if (response != null) {
                    Log.d("Check", "Get list successful")
                    productsName = response.body()
                    setUpAdapter()
                }
            }

            override fun onFailure(call: Call<List<String>>?, t: Throwable?) {
                Toast.makeText(applicationContext, "Khong the lam moi nguon cung cap du lieu", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setUpAdapter(): Unit {
        val arrayAdapter: ArrayAdapter<String> = ArrayAdapter<String>(applicationContext, android.R.layout.simple_list_item_1, productsName)
        binding.inputSearchLaptop.setAdapter(arrayAdapter)
        binding.inputSearchLaptop.setOnItemClickListener { adapterView, view, position, id -> kotlin.run {
            val name: String = adapterView.getItemAtPosition(position) as String
            handleSearchProductByName(name)
        } }
    }

    private fun showDetailsProduct(product: Product): Unit {
        val intent: Intent = Intent(applicationContext, DetailsProductActivity::class.java)
        val bundle: Bundle = Bundle()
        bundle.putSerializable(Const.intentProduct, product)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    private fun handleSearchProductByName(name: String): Unit {
        val call = RetrofitInstance.apiService.searchProductByName(ProductSearch(name))
        call.enqueue(object: Callback<List<Product>> {
            override fun onResponse(
                call: Call<List<Product>>?,
                response: Response<List<Product>>?
            ) {
                if (response != null) {
                    products = response.body()
                    loadData()
                }
            }

            override fun onFailure(call: Call<List<Product>>?, t: Throwable?) {
                Toast.makeText(applicationContext, "Khong the tim thay", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun loadData(): Unit {
        productAdapter.setProduct(products)
        binding.rcvSearch.adapter = productAdapter
    }
}