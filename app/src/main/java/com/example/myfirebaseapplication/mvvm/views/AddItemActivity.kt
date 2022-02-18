package com.example.myfirebaseapplication.mvvm.views

import android.Manifest
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.example.myfirebaseapplication.R
import com.example.myfirebaseapplication.databinding.ActivityAddItemBinding
import com.example.myfirebaseapplication.mvvm.helpers.Navigation
import com.example.myfirebaseapplication.mvvm.helpers.NetworkViewModel
import com.example.myfirebaseapplication.mvvm.models.FirebaseDataClass
import com.example.myfirebaseapplication.mvvm.viewmodels.MyViewModel
import java.io.ByteArrayOutputStream
import java.util.*
import kotlin.collections.ArrayList

class AddItemActivity : AppCompatActivity() {
    private val pickImage = 1
    lateinit var imageView: ImageView
    private var imageUri: Uri? = null
    lateinit var uploadImage: ImageView

    private val viewModel: MyViewModel by viewModels()
    private lateinit var binding: ActivityAddItemBinding
    private lateinit var stringVariableEditText: EditText
    private lateinit var intVariableEditText: EditText
    private lateinit var booleanVariableCheckBox: CheckBox
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTitle("Add Item Activity")
        actionBar?.setHomeButtonEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        stringVariableEditText = binding.stringEditText
        intVariableEditText = binding.intEditText
        booleanVariableCheckBox = binding.booleanCheckBox
        binding.saveItemButton.setOnClickListener {
            if (NetworkViewModel().checkConnection(this) == true) {
                saveItem()
            }
        }
        imageView = binding.imageView
        uploadImage = binding.imageView
        if (NetworkViewModel().checkConnection(this) == true) {
            uploadImageIntoImageView()
        }

        binding.chooseImageButton.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(permissions, 1001)
                } else {
                    val intent =
                        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
                    startActivityForResult(intent, pickImage)
                }
            } else {
                val intent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
                startActivityForResult(intent, pickImage)
            }
        }
        binding.saveImageButton.setOnClickListener {
            if (NetworkViewModel().checkConnection(this) == true) {
                viewModel.addOneImage(imageView, "New") {
                    //
                }
            }
        }
    }

    private fun uploadImageIntoImageView() {
        viewModel.uploadOneImage("New") {
            if (it != null) {
                Glide.with(this@AddItemActivity)
                    .load(it.toString())
                    .into(uploadImage)
                Log.d(ContentValues.TAG, "NOT NULL")
            } else {
                Log.d(ContentValues.TAG, "NULL")
            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            imageView.setImageURI(imageUri)
        }
    }


    private fun saveItem() {
        var inputErrors = 0
        val string = stringVariableEditText.text.toString()
        val intString = intVariableEditText.text.toString()
        var int = 0
        if (stringIsInteger(intString) == true) {
            int = intString.toInt()
        } else {
            inputErrors = 1
        }
        val boolean = booleanVariableCheckBox.isChecked
        val id = UUID.randomUUID().toString()
        val stringArr = ArrayList<String>()
        val intArr = ArrayList<Int>()
        val newItem = FirebaseDataClass(id, string, int, boolean, stringArr, intArr)
        if (inputErrors == 0) {
            viewModel.addNewItem(newItem) {
                if (it == true) {
                    Navigation().fromTo(this, ListActivity())
                } else {
                    // Message about errors
                }
            }
        } else if (inputErrors == 1) {
            // Error message that number is not integer
        }
    }

    private fun stringIsInteger(inputInteger: String): Boolean {
        var isInteger = false
        val intNull = inputInteger.toIntOrNull()
        if (intNull?.equals(null) == false) {
            isInteger = true
        }
        return isInteger
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Navigation().fromTo(this, ListActivity())
        return true
    }
}
