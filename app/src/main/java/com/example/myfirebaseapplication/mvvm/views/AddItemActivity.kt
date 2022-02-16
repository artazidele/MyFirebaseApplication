package com.example.myfirebaseapplication.mvvm.views

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import com.example.myfirebaseapplication.R
import com.example.myfirebaseapplication.databinding.ActivityAddItemBinding
import com.example.myfirebaseapplication.mvvm.helpers.Navigation
import com.example.myfirebaseapplication.mvvm.helpers.NetworkViewModel
import com.example.myfirebaseapplication.mvvm.models.FirebaseDataClass
import com.example.myfirebaseapplication.mvvm.viewmodels.MyViewModel
import java.util.*
import kotlin.collections.ArrayList

class AddItemActivity : AppCompatActivity() {
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
    }

    private fun saveItem() {
        val string = stringVariableEditText.text.toString()
        val int = intVariableEditText.text.toString().toInt()
        val boolean = booleanVariableCheckBox.isChecked
        val id = UUID.randomUUID().toString()
        val stringArr = ArrayList<String>()
        val intArr = ArrayList<Int>()
        val newItem = FirebaseDataClass(id, string, int, boolean, stringArr, intArr)
        viewModel.addNewItem(newItem) {
            if (it == true) {
                Navigation().fromTo(this, ListActivity())
                Log.d(ContentValues.TAG, "Add success")
            } else {
                Log.d(ContentValues.TAG, "Add failure")
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Navigation().fromTo(this, ListActivity())
        return true
    }
}
