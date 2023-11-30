package com.example.report

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.report.databinding.ActivityMainBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class MainActivity : AppCompatActivity() {

    private val mBinding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val adapter = ItemRecyclerViewAdapter()
    private val fireStore = FiresStore()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        if (Firebase.auth.currentUser == null) {
            startActivity(
                Intent(this, LoginActivity::class.java))
        }

        fireStore.itemList.observe(this) { list ->
            adapter.submitList(list)
        }

        mBinding.apply {
            btnLogout.setOnClickListener {
                Firebase.auth.signOut()
                startActivity(Intent(this@MainActivity, LoginActivity::class.java))
            }

            btnAdd.setOnClickListener {
                CustomEditDialog(this@MainActivity).show()
            }

            btnSearch.setOnClickListener {
                when {
                    editPrice.text.isNotBlank() -> {
                        submitList { item: Item ->  item.price <= editPrice.text.toString().toLong()}
                    }
                    chkSold.isChecked -> {
                        submitList { item: Item ->  item.isSold }
                    }
                    else -> {
                        submitList { true }
                    }
                }
            }

            btnMyList.setOnClickListener {
                submitList { item: Item -> item.user == Firebase.auth.currentUser?.email.toString() }
            }

            viewItem.adapter = adapter
            viewItem.layoutManager = LinearLayoutManager(baseContext, RecyclerView.VERTICAL, false)
            val dividerItemDecoration = DividerItemDecoration(viewItem.context, LinearLayoutManager.VERTICAL)
            viewItem.addItemDecoration(dividerItemDecoration)
        }
    }

    private fun submitList(filter: (Item) -> Boolean) {
        fireStore.readData(filter)
    }
}