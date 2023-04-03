package com.example.laptop_app.views.user.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.laptop_app.R
import com.example.laptop_app.adapter.rcv.CartAdapter
import com.example.laptop_app.controller.api.RetrofitInstance
import com.example.laptop_app.controller.database_firebase.Authentication
import com.example.laptop_app.controller.db_local.DbLocal
import com.example.laptop_app.databinding.FragmentCartBinding
import com.example.laptop_app.model.Cart
import com.example.laptop_app.model.Order
import com.example.laptop_app.model.respone.ResCart
import com.example.laptop_app.model.Product
import com.example.laptop_app.model.User
import com.example.laptop_app.others.Const
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding

    private lateinit var resCarts: List<ResCart>
    private lateinit var cartAdapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(layoutInflater)

        init()
        setOnclick()
        showInformation()
        getCarts()

        return binding.root
    }

    private fun init(): Unit {
        resCarts = ArrayList()
        cartAdapter = CartAdapter(requireContext(), resCarts, object: CartAdapter.ClickItem {
            override fun showDetailsProduct(product: Product) {

            }

            override fun riseProduct(resCart: ResCart) {
                reduceRiseProduct(resCart, 1)
            }

            override fun reduceProduct(resCart: ResCart) {
                reduceRiseProduct(resCart, -1)
            }

        })

        // show list cart
        binding.rcvCart.layoutManager = LinearLayoutManager(requireContext())
        binding.rcvCart.adapter = cartAdapter

        // show price sum
        calculateTotalAmount()
    }

    private fun setOnclick(): Unit {
        binding.textBuy.setOnClickListener { buy() }
    }

    private fun getCarts(): Unit {
        val idUserCurrent: String = DbLocal.getUserCurrentLogged(requireContext())?.getId() ?: ""
        val call = RetrofitInstance.apiService.getCart(idUserCurrent)
        call.enqueue(object: Callback<List<ResCart>> {
            override fun onResponse(call: Call<List<ResCart>>?, response: Response<List<ResCart>>?) {

                if (response != null) {
                    resCarts = response.body()
                    loadData()
                }
            }

            override fun onFailure(call: Call<List<ResCart>>?, t: Throwable?) {
                Toast.makeText(requireContext(), "Không thể làm mới nguồn dữ liệu!!!", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun loadData(): Unit {
        cartAdapter.setCarts(resCarts)
        binding.rcvCart.adapter = cartAdapter

        calculateTotalAmount()
    }

    private fun reduceRiseProduct(resCart: ResCart, flag: Int): Unit {
        /*
        * flag == 1 => rise
        * flag = -1 => reduces
        */
        var temp: Int = if (flag == 1) {
            resCart.getProductQuantity() + 1
        } else {
            resCart.getProductQuantity() - 1
        }

        if (temp == 0) {
            removeCart(resCart)
        } else {
            resCart.setProductQuantity(temp)
            updateCart(resCart)
        }

        getCarts()
    }

    private fun removeCart(resCart: ResCart): Unit {
        val call = RetrofitInstance.apiService.deleteCart(resCart.getId())
        call.enqueue(object: Callback<Int> {
            override fun onResponse(call: Call<Int>?, response: Response<Int>?) {
                if (response != null) {
                    getCarts()
                }
            }

            override fun onFailure(call: Call<Int>?, t: Throwable?) {
                Toast.makeText(requireContext(), "Khong the thuc hien thao tac nay", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun updateCart(resCart: ResCart): Unit {
        val cart: Cart = Cart(
            resCart.getId(),
            resCart.getIdUser(),
            resCart.getIdOrder(),
            resCart.getProduct().getId(),
            resCart.getProductQuantity(),
        )
        val call = RetrofitInstance.apiService.updateCart(resCart.getId(), cart)
        call.enqueue(object: Callback<Int> {
            override fun onResponse(call: Call<Int>?, response: Response<Int>?) {
                if (response != null) {
                    Log.d("Check", "Thanh cong")
                }
            }

            override fun onFailure(call: Call<Int>?, t: Throwable?) {
                Toast.makeText(requireContext(), "Khong the cap nhat gio hang", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // tinh tong tien
    private fun calculateTotalAmount(): Unit {
        var sum: Double = 0.0
        for (i in resCarts.indices) {
            sum += resCarts[i].getProduct().getTax() * resCarts[i].getProductQuantity()
        }
        binding.textPriceSum.text = "Tổng tiền: ${sum}$"
    }

    private fun showInformation(): Unit {
        // get user current logged
        val user: User? = DbLocal.getUserCurrentLogged(requireContext())

        if (user != null) {
            Glide.with(requireContext())
                .load(user.getImage())
                .error(R.mipmap.ic_launcher)
                .into(binding.avatarUserCurrentLogged)
            binding.textUserNameCurrentLogged.text = user.getUserName()
        } else {
            return
        }
    }

    private fun buy(): Unit {
        val user: User? = DbLocal.getUserCurrentLogged(requireContext())
        if (user?.getPhoneNumber() == null || user?.getAddress() == null) {
            Toast.makeText(requireContext(), Const.errorNullInfo, Toast.LENGTH_SHORT).show()
            return
        }

        for (i in resCarts.indices) {
            val idOrder = Authentication.genIdAuto()

            // handle add order
            val order: Order = Order(idOrder, user.getId() ?: "", getTime(), Const.waitingConfirm, Const.COD)
            addOrder(order)

            // update cart
            resCarts[i].setIdOrder(idOrder)
            updateCart(resCarts[i])
        }
        Toast.makeText(requireContext(), "Dat hang thanh cong", Toast.LENGTH_SHORT).show()
    }

    private fun addOrder(order: Order): Unit {
        val call = RetrofitInstance.apiService.addOrder(order)
        call.enqueue(object: Callback<Int> {
            override fun onResponse(call: Call<Int>?, response: Response<Int>?) {
                Log.d("CheckAddOrder", "Add successful")
            }

            override fun onFailure(call: Call<Int>?, t: Throwable?) {
                Log.e("CheckAddOrder", "Can not add")
            }

        })
    }

    private fun getTime(): String {
        val calendar: Calendar = Calendar.getInstance()
        val currentTime: Date = calendar.time
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

        return sdf.format(Date())
    }

    override fun onResume() {
        super.onResume()

        getCarts()
    }
}