package com.example.myfirebaseapplication.mvvm.models

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class FirebaseDatabase {
    val db = FirebaseFirestore.getInstance()

    fun getItems(): Task<QuerySnapshot> {
        return db.collection("Item")
            .orderBy("myString")
            .get()
    }

    fun deleteItem(id: String): Task<Void> {
        return db.collection("Item").document(id)
            .delete()
    }

    fun getItem(id: String): Task<DocumentSnapshot> {
        return db.collection("Item")
            .document(id)
            .get()
    }

    fun addItem(item: FirebaseDataClass): Task<Void> {
        return db.collection("Item").document(item.myId)
            .set(item)
    }

    fun updateItem(item: FirebaseDataClass): Task<Void> {
        return db.collection("Item").document(item.myId)
            .update(
                mapOf(
                    "myString" to item.myString,
                    "myInt" to item.myInt,
                    "myBoolean" to item.myBoolean,
                    "myStringArray" to item.myStringArray,
                    "myIntArray" to item.myIntArray
                )
            )
    }
}