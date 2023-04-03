package com.example.laptop_app.controller.database_firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase

object Authentication {
    fun isLoggedIn(): Boolean {
        val user: FirebaseUser? = FirebaseAuth.getInstance().currentUser
        return user != null
    }

    fun genIdAuto(): String = FirebaseDatabase.getInstance().reference.push().key ?: "";
}