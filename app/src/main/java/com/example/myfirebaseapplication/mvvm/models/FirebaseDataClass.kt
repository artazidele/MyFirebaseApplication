package com.example.myfirebaseapplication.mvvm.models

data class FirebaseDataClass(
    val myId: String = "",
    val myString: String = "",
    val myInt: Int = 0,
    val myBoolean: Boolean = false,
    val myStringArray: ArrayList<String> = ArrayList(),
    val myIntArray: ArrayList<Int> = ArrayList()
)
