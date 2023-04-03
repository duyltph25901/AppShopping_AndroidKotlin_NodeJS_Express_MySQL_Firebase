package com.example.laptop_app.views.user.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.laptop_app.R
import com.example.laptop_app.controller.api.RetrofitInstance
import com.example.laptop_app.controller.database_firebase.Authentication
import com.example.laptop_app.controller.db_local.DbLocal
import com.example.laptop_app.databinding.ActivityDetailsProductBinding
import com.example.laptop_app.model.*
import com.example.laptop_app.others.Const
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class DetailsProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsProductBinding

    private lateinit var product: Product
    private var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDetailsProductBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        init();
        getProduct()
        showData()
        onClick()
    }

    private fun init(): Unit {
        user = DbLocal.getUserCurrentLogged(applicationContext)
    }

    private fun getProduct(): Unit {
        product = intent.extras?.get(Const.intentProduct) as Product
    }

    private fun showData(): Unit {
        Glide.with(this)
            .load(product.getImage())
            .error(R.mipmap.ic_launcher)
            .into(binding.imageProduct)
        binding.textPurchases.text = "Đã bán: ${product.getPurchases()}"
        binding.textProductName.text = product.getName()
        binding.textDescription.text = product.getDescription()
        binding.textCategory.text = "Danh mục: ${product.getCategory()}"
        if (product.getQuantity() >= 0) {
            binding.buttonBuyNow.isEnabled = true
            binding.textStatus.text = "Tình trạng: Còn hàng"
        } else {
            binding.buttonBuyNow.isEnabled = false
            binding.textStatus.text = "Tình trạng: Hết hàng"
        }
        binding.textNewPrice.text = "$ ${product.getTax()}"
        if (product.getDiscount() > 0) {
            binding.layoutOldPrice.visibility = View.VISIBLE
            binding.textOldPrice.text = "$ ${product.getPrice()}"
            binding.textDiscount.text = "- ${product.getDiscount()}%"
            binding.textSaleOff.text = "Sale off"
        } else {
            binding.layoutOldPrice.visibility = View.GONE
            binding.textSaleOff.text = "Free ship"
        }
    }

    private fun onClick(): Unit {
        binding.imageBack.setOnClickListener { finish() }
        binding.imageAddCart.setOnClickListener { handleAddProductToCart() }
        binding.buttonBuyNow.setOnClickListener { buyNow() }
    }

    private fun handleAddProductToCart(): Unit {
        val idProductCurrent: String = product.getId()
        val idUserCurrent: String = DbLocal.getUserCurrentLogged(applicationContext)?.getId() ?: ""
        val addCart: AddCart = AddCart(idProductCurrent, idUserCurrent, Authentication.genIdAuto())

        val call = RetrofitInstance.apiService.addProductToCart(addCart)
        call.enqueue(object: Callback<Int> {
            override fun onResponse(call: Call<Int>?, response: Response<Int>?) {
                Toast.makeText(applicationContext, "Them gio hang thanh cong", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<Int>?, t: Throwable?) {
                Toast.makeText(applicationContext, "Khong the thuc hien thao tac nay", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun buyNow(): Unit {
        val order: Order = Order(
            Authentication.genIdAuto(),
            user?.getId() ?: "",
            getTime(),
            -1,
            -1
        )
        val cart: Cart = Cart(
            Authentication.genIdAuto(),
            user?.getId() ?: "",
            order.getId(),
            product.getId(),
            1
        )

        // add cart and order
        handleAddOrder(order)
        handleAddCart(cart)
    }

    private fun handleAddCart(cart: Cart): Unit {
        val call = RetrofitInstance.apiService.addCart(cart)
        call.enqueue(object: Callback<Int> {
            override fun onResponse(call: Call<Int>?, response: Response<Int>?) {
                Toast.makeText(applicationContext, "Don hang da dat thanh cong", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<Int>?, t: Throwable?) {
                Log.e("Check", "Can not add")
            }

        })
    }

    private fun handleAddOrder(order: Order): Unit {
        val call = RetrofitInstance.apiService.addOrder(order)
        call.enqueue(object: Callback<Int> {
            override fun onResponse(call: Call<Int>?, response: Response<Int>?) {
                Log.d("Check", "Add successful")
            }

            override fun onFailure(call: Call<Int>?, t: Throwable?) {
                Log.e("Check", "Can not add")
            }

        })
    }

    private fun getTime(): String {
        val calendar: Calendar = Calendar.getInstance()
        val currentTime: Date = calendar.time
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

        return sdf.format(Date())
    }
}