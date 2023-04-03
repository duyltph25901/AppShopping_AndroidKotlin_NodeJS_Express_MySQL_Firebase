package com.example.laptop_app.controller.database_firebase

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import com.example.laptop_app.controller.api.RetrofitInstance
import com.example.laptop_app.controller.db_local.DbLocal
import com.example.laptop_app.model.User
import com.example.laptop_app.others.Const
import com.example.laptop_app.views.shared.activities.LoginActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.util.*

object Storage {
    
    private var result: String = ""
    
    private fun setResult(result: String): Unit {
        this.result = result
    }
    
    public fun getResult(): String = this.result
    
    fun postImageToStorage(path: Bitmap, image: ImageView, context: Context): Unit {
        // init storage
        val stRef = FirebaseStorage.getInstance().reference
        val userRef = stRef.child("image_user_update").child(path.toString())
        // Get the data from an ImageView as bytes
        // Get the data from an ImageView as bytes
        image.isDrawingCacheEnabled = true
        image.buildDrawingCache()
        image.setImageBitmap(path)
        val bitmap = (image.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()
        // upload image from device
        // upload image from device
        val uploadTask = userRef.putBytes(data)
        uploadTask
            .addOnFailureListener {
                Toast.makeText(context, "Khong the thuc hien thao tac nay", Toast.LENGTH_SHORT).show()
            }
            .addOnSuccessListener {
                // get Uri img on firebase
                val getDownloadUriTask =
                    uploadTask.continueWithTask { task: Task<UploadTask.TaskSnapshot?> ->
                        if (!task.isSuccessful) {
                            throw Objects.requireNonNull(task.exception)!!
                        }
                        userRef.downloadUrl
                    }
                getDownloadUriTask.addOnCompleteListener { task: Task<Uri> ->
                    if (task.isSuccessful) {
                        setResult("${task.result}")
                        val user: User? = DbLocal.getUserCurrentLogged(context)
                        if (user != null) {
                            user.setImage("${task.result}")
                            handleUpdateUser(user, context)
                        }
                    } else {
                        Toast.makeText(context, "Khong the thuc hien thao tac nay", Toast.LENGTH_SHORT).show()
                        Log.e("Error update image firebase", "${task.exception}")
                    }
                }
            }
    }

    private fun handleUpdateUser(user: User, context: Context): Unit {
        val call = RetrofitInstance.apiService.updateUser(user)
        call.enqueue(object: Callback<Int> {
            override fun onResponse(call: Call<Int>?, response: Response<Int>?) {
                Toast.makeText(
                    context,
                    "Cap nhat thanh cong\nHe thong se tu dong dang xuat trong 7s",
                    Toast.LENGTH_SHORT
                ).show()

                Handler(Looper.getMainLooper()).postDelayed({
                    context.startActivity(Intent(context, LoginActivity::class.java))
                }, 7000)
            }

            override fun onFailure(call: Call<Int>?, t: Throwable?) {
                Toast.makeText(
                    context,
                    "Khong co phan hoi tu may chu!",
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }
}