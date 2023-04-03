package com.example.laptop_app.controller.db_local

import android.content.Context
import com.example.laptop_app.controller.db_local.system.SystemDatabase
import com.example.laptop_app.controller.db_local.user.UserDatabase
import com.example.laptop_app.model.User
import com.example.laptop_app.others.Const
import java.sql.SQLException

object DbLocal {
    fun handleSaveUserCurrentLogged(user: User, context: Context): Int {
        return try {
            // delete all user before
            UserDatabase.getDatabase(context).userDao().deleteUserCurrent()
            // handle insert user current logged
            UserDatabase.getDatabase(context).userDao().insertUser(user)

            Const.success
        } catch (sqlEx: SQLException) {
            Const.fail
        }
    }

    fun getUserCurrentLogged(context: Context): User? {

        return try {
            return (UserDatabase.getDatabase(context).userDao().getUserLogged())
        } catch (errEx: SQLException) {null}
    }

    fun saveStatusSystemCurrent(context: Context, system: com.example.laptop_app.model.MySystem): Unit {
        SystemDatabase.getDatabase(context).SystemDao().clearSystem()
        SystemDatabase.getDatabase(context).SystemDao().insert(system)
    }

    fun removeAllStatusSystem(context: Context): Unit {
        SystemDatabase.getDatabase(context).SystemDao().clearSystem()
    }
}