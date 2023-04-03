package com.example.laptop_app.views.user.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.laptop_app.R
import com.example.laptop_app.controller.database_firebase.Storage
import com.example.laptop_app.controller.db_local.DbLocal
import com.example.laptop_app.controller.views.MyDialog
import com.example.laptop_app.databinding.FragmentAccountBinding
import com.example.laptop_app.model.User
import com.example.laptop_app.views.shared.activities.LoginActivity

class AccountFragment : Fragment() {

    private lateinit var binding: FragmentAccountBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAccountBinding.inflate(layoutInflater)

        val userCurrent: User? = getUser()

        showData(userCurrent)
        setOnclick()

        return binding.root
    }

    private fun getUser(): User? = DbLocal.getUserCurrentLogged(requireContext()) ?: null

    private fun showData(user: User?): Unit {
        if (user != null) {
            Glide.with(requireContext())
                .load(user.getImage())
                .error(R.mipmap.ic_launcher)
                .into(binding.avatarUser)
            binding.textUserName.text = user.getUserName()
            binding.textPhoneNumber.text = user.getPhoneNumber()
            binding.textAddress.text = user.getAddress()

            if (user.getEmail().length >= 25) {
                binding.textEmail.text = user.getEmail().substring(0, 20) + "..."
            } else {
                binding.textEmail.text = user.getEmail()
            }
        } else {
            return
        }
    }

    private fun setOnclick(): Unit {
        binding.buttonEdit.setOnClickListener { editUser() }
        binding.avatarUser.setOnClickListener { openGallery() }
        binding.buttonLogOut.setOnClickListener { logOut() }
    }

    private fun editUser(): Unit {
        MyDialog.showDialogUpdateUser(R.layout.dialog_option_update, requireContext())
    }

    private fun openGallery(): Unit {
        val intent: Intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT

        activityResultLauncher.launch(Intent.createChooser(intent, "Select Picture"))
    }

    private val activityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val intent: Intent? = result.data
            val uri: Uri? = intent?.data
            val path = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, uri)
            Storage.postImageToStorage(path, binding.avatarUser, requireContext())
        }
    }

    private fun logOut(): Unit {
        startActivity(Intent(requireContext(), LoginActivity::class.java))
        activity?.finishAffinity()

        DbLocal.removeAllStatusSystem(requireContext())
    }
}