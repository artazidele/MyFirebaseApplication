package com.example.myfirebaseapplication.mvvm.models

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream

class FirebaseDatabase {
    val db = FirebaseFirestore.getInstance()
    val storage = Firebase.storage

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

    fun addImage(imageView: ImageView, title: String): UploadTask {
        val storageRef = storage.reference
        val productRef = storageRef.child(title)
        imageView.isDrawingCacheEnabled = true
        imageView.buildDrawingCache()
        val bitmap = (imageView.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()
        return productRef.putBytes(data)
    }


    public fun getImageReference(title: String): Task<Uri> {
        return storage.reference.child(title).downloadUrl
    }


}
