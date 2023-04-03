package com.example.laptop_app.controller.views

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.AbsListView
import android.widget.Button
import com.example.laptop_app.R
import com.example.laptop_app.views.user.activities.EditInfoActivity
import com.example.laptop_app.views.user.activities.EditPassActivity

object MyDialog {
    fun showDialogUpdateUser( layout: Int, context: Context): Unit {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(layout)
        val window = dialog.window
        window!!.setLayout(
            AbsListView.LayoutParams.MATCH_PARENT,
            AbsListView.LayoutParams.WRAP_CONTENT
        )
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()

        val buttonPass: Button = dialog.findViewById(R.id.buttonUpdatePass)
        val buttonInfo: Button = dialog.findViewById(R.id.buttonUpdateInfo)

        buttonPass.setOnClickListener {
            context.startActivity(Intent(context, EditPassActivity::class.java))
            dialog.cancel()
        }
        buttonInfo.setOnClickListener {
            context.startActivity(Intent(context, EditInfoActivity::class.java ))
            dialog.cancel()
        }
    }
}
