package com.example.myfirebaseapplication.mvvm.views

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myfirebaseapplication.R
import com.example.myfirebaseapplication.databinding.ActivityListBinding
import com.example.myfirebaseapplication.mvvm.helpers.Navigation
import com.example.myfirebaseapplication.mvvm.helpers.NetworkViewModel
import com.example.myfirebaseapplication.mvvm.viewmodels.MyViewModel

class ListActivity : AppCompatActivity() {
    private val viewModel: MyViewModel by viewModels()
    private lateinit var binding: ActivityListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTitle("My List Activity")
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.allItemRecyclerView.layoutManager = LinearLayoutManager(this)
        if (NetworkViewModel().checkConnection(this) == true) {
            loadItemList()
        }
    }


    private fun loadItemList() {
        viewModel.getAllItems() {
            if (it?.isNotEmpty() == true) {
                binding.allItemRecyclerView.adapter = MyListAdapter(it!!)
                binding.allItemRecyclerView.visibility = View.VISIBLE
                Log.d(ContentValues.TAG, "NOT EMPTY")
            } else {
                Log.d(ContentValues.TAG, "EMPTY")
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.item_list_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add -> Navigation().fromTo(this, AddItemActivity())
        }
        return super.onOptionsItemSelected(item)
    }

}