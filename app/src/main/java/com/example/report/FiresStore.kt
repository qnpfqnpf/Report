package com.example.report

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FiresStore {

    private val _itemList = MutableLiveData<List<Item>>()
    val itemList: LiveData<List<Item>> get() = _itemList

    private val db = Firebase.firestore

    fun addData(item: Item) {
        val newItem = hashMapOf(
            "content" to item.content,
            "isSold" to item.isSold,
            "price" to item.price,
            "title" to item.title,
            "user" to item.user
        )

        db.collection("items")
            .add(newItem)
    }

    fun readData(filter: (Item) -> Boolean) {
        db.collection("items")
            .get()
            .addOnSuccessListener { documents ->
                val list = mutableListOf<Item>()
                for (document in documents) {
                    document?.let {
                        val item = Item(
                            content = it.getString("content") ?: "",
                            isSold = it.getBoolean("isSold") ?: false,
                            price = it.getLong("price") ?: 0,
                            title = it.getString("title") ?: "",
                            user = it.getString("user") ?: "",
                            key = it.id
                        )
                        list.add(item)
                    }
                }
                _itemList.postValue(list.filter(filter))
            }
    }

    fun updateData(key: String, item: Item) {
        val db = Firebase.firestore
        val newItem = hashMapOf(
            "content" to item.content,
            "isSold" to item.isSold,
            "price" to item.price,
            "title" to item.title,
            "user" to item.user
        )
        db.collection("items").document(key)
            .update(newItem as Map<String, Any>)
    }
}