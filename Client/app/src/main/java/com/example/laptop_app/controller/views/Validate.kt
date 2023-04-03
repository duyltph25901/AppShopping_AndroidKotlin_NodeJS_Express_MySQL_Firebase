package com.example.laptop_app.controller.views

import android.app.Activity
import android.content.Context
import android.widget.Toast
import com.example.laptop_app.others.Const
import java.util.regex.Matcher
import java.util.regex.Pattern

object Validate {
    fun isNull(vararg input: String): Boolean {
        for (item in input) {
            if (item.isEmpty()) return true
        }

        return false
    }

    fun isEmail(email: String): Boolean {
        val pattern: Pattern
        val EMAIL_PATTERN =
            "^[_A-Za-z\\d-]+(\\.[_A-Za-z\\d-]+)*@[A-Za-z\\d]+(\\.[A-Za-z\\d]+)*(\\.[A-Za-z]{2,})$"
        pattern = Pattern.compile(EMAIL_PATTERN)
        val matcher: Matcher = pattern.matcher(email)

        return matcher.matches()
    }

    fun isUserName(userName: String): Boolean = userName.length >= 2

    fun isPassword(pass: String): Boolean = pass.length >= 6

    fun isConfirm(pass: String, confirmPass: String): Boolean = pass.matches(confirmPass.toRegex())

    fun isPhoneNumber(phoneNumber: String): Boolean {
        val p = Pattern.compile("^\\d{10}$")
        val m = p.matcher(phoneNumber)
        return m.matches()
    }

    fun isBreakingSignUp(activity: Activity, vararg input: Boolean): Boolean {
        val isNull = input[0]
        val email = input[1]
        val userName = input[2]
        val pass = input[3]

        if (isNull) {
            Toast.makeText(activity, Const.nullMessage, Toast.LENGTH_SHORT).show()
            return true
        }; if (!email) {
            Toast.makeText(activity, Const.errorEmailMessage, Toast.LENGTH_SHORT).show()
            return true
        }; if (!userName) {
            Toast.makeText(activity, Const.errorUserNameMessage, Toast.LENGTH_SHORT).show()
            return true
        }; if (!pass) {
            Toast.makeText(activity, Const.errorPasswordMessage, Toast.LENGTH_SHORT).show()
            return true
        }

        return false
    }

    fun isBreakingLogin(context: Context, vararg input: Boolean): Boolean {
        if (input[0]) {
            Toast.makeText(context, Const.nullMessage, Toast.LENGTH_SHORT).show()
            return true
        }; if (!input[1]) {
            Toast.makeText(context, Const.errorEmailMessage, Toast.LENGTH_SHORT).show()
            return true
        }

        return false
    }

    fun isBreakingUpdateUser(context: Context, vararg  input: Boolean): Boolean {
        val isNull: Boolean = input[0]
        val isPass: Boolean = input[1]
        val isConfirm: Boolean = input[2]
        val isMatchesOldPass: Boolean = input[3]

        if (isNull) {
            Toast.makeText(context, Const.nullMessage, Toast.LENGTH_SHORT).show()
            return true
        }; if (!isPass) {
            Toast.makeText(context, Const.errorPasswordMessage, Toast.LENGTH_SHORT).show()
            return true
        }; if (!isConfirm) {
            Toast.makeText(context, Const.errorConfirmPasswordMessage, Toast.LENGTH_SHORT).show()
            return true
        }; if (!isMatchesOldPass) {
            Toast.makeText(context, Const.errorNotMatchesOldPass, Toast.LENGTH_SHORT).show()
            return true
        }

        return false
    }

    fun isBreakingUpdateInfoUser(context: Context, vararg  input: Boolean): Boolean {
        val isNull: Boolean = input[0]
        val isPhoneNumber: Boolean = input[1]
        val isEmail: Boolean = input[2]
        val isUserName: Boolean = input[3]

        if (isNull) {
            Toast.makeText(context, Const.nullMessage, Toast.LENGTH_SHORT).show()
            return true
        }; if (!isPhoneNumber) {
            Toast.makeText(context, Const.errorPhoneNumberMessage, Toast.LENGTH_SHORT).show()
            return true
        }; if (!isEmail) {
            Toast.makeText(context, Const.errorEmailMessage, Toast.LENGTH_SHORT).show()
            return true
        }; if (!isUserName) {
            Toast.makeText(context, Const.errorUserNameMessage, Toast.LENGTH_SHORT).show()
            return true
        }

        return false
    }
}