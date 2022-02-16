package com.example.myfirebaseapplication.mvvm.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myfirebaseapplication.R
import com.example.myfirebaseapplication.mvvm.models.FirebaseDataClass

class MyListAdapter (private val myData: ArrayList<FirebaseDataClass>) :
    RecyclerView.Adapter<MyListAdapter.MyViewHolder>(){

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val stringTextView: TextView
//        val deleteButton: Button
//        val editButton: Button

        init {
            stringTextView = view.findViewById(R.id.string_text_view)
//            deleteButton = view.findViewById(R.id.delete_button)
//            editButton = view.findViewById(R.id.edit_button)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.my_list_row, viewGroup, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: MyViewHolder, position: Int) {
        viewHolder.stringTextView.text = myData[position].myString
//        viewHolder.deleteButton.setOnClickListener {
//
//        }
//        viewHolder.editButton.setOnClickListener {
//
//        }
    }
    override fun getItemCount() = myData.size
}