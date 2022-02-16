package com.example.myfirebaseapplication.mvvm.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myfirebaseapplication.mvvm.helpers.NetworkDataStatus
import com.example.myfirebaseapplication.mvvm.models.FirebaseDataClass
import com.example.myfirebaseapplication.mvvm.models.FirebaseDatabase
import com.google.firebase.firestore.ktx.toObject

class MyViewModel : ViewModel() {
    private val _network_status = MutableLiveData<NetworkDataStatus>()
    val networkStatus: LiveData<NetworkDataStatus> = _network_status

    fun addNewItem(item: FirebaseDataClass, onResult: (Boolean) -> Unit) {
        _network_status.value = NetworkDataStatus.LOADING
        FirebaseDatabase().addItem(item)
            .addOnSuccessListener { documents ->
                _network_status.value = NetworkDataStatus.DONE
                onResult(true)
            }
            .addOnFailureListener {
                _network_status.value = NetworkDataStatus.ERROR
                onResult(false)
            }
    }

    fun getOneItem(id: String, onResult: (FirebaseDataClass?) -> Unit) {
        _network_status.value = NetworkDataStatus.LOADING
        FirebaseDatabase().getItem(id)
            .addOnSuccessListener { document ->
                _network_status.value = NetworkDataStatus.DONE
                val allergen = document.toObject<FirebaseDataClass>()
                onResult(allergen)
            }
            .addOnFailureListener {
                _network_status.value = NetworkDataStatus.ERROR
                onResult(null)
            }
    }

    fun deleteOneItem(id: String, onResult: (Boolean) -> Unit) {
        _network_status.value = NetworkDataStatus.LOADING
        FirebaseDatabase().deleteItem(id)
            .addOnSuccessListener {
                _network_status.value = NetworkDataStatus.DONE
                onResult(true)
            }
            .addOnFailureListener {
                _network_status.value = NetworkDataStatus.ERROR
                onResult(false)
            }
    }

    fun updateOneItem(item: FirebaseDataClass, onResult: (Boolean) -> Unit) {
        _network_status.value = NetworkDataStatus.LOADING
        FirebaseDatabase().updateItem(item)
            .addOnSuccessListener {
                _network_status.value = NetworkDataStatus.DONE
                onResult(true)
            }
            .addOnFailureListener {
                _network_status.value = NetworkDataStatus.ERROR
                onResult(false)
            }
    }

    fun getAllItems(onResult: (ArrayList<FirebaseDataClass>?) -> Unit) {
        var itemList = ArrayList<FirebaseDataClass>()
        _network_status.value = NetworkDataStatus.LOADING
        FirebaseDatabase().getItems()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val item = document.toObject<FirebaseDataClass>()
                    itemList.add(item)
                }
                _network_status.value = NetworkDataStatus.DONE
                onResult(itemList)
            }
            .addOnFailureListener {
                _network_status.value = NetworkDataStatus.ERROR
                onResult(null)
            }
    }
}