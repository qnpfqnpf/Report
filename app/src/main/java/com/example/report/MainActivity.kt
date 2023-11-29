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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        if (Firebase.auth.currentUser == null) {
            startActivity(
                Intent(this, LoginActivity::class.java))
        }

        submitList()

        mBinding.apply {
            btnLogout.setOnClickListener {
                Firebase.auth.signOut()
                startActivity(Intent(this@MainActivity, LoginActivity::class.java))
            }
            viewItem.adapter = adapter
            viewItem.layoutManager = LinearLayoutManager(baseContext, RecyclerView.VERTICAL, false)
            val dividerItemDecoration = DividerItemDecoration(viewItem.context, LinearLayoutManager.VERTICAL)
            viewItem.addItemDecoration(dividerItemDecoration)
        }
    }

    private fun submitList() {
        //TODO: Get From FireStore
        val list = ArrayList<Item>()
        repeat(10) {
            list += Item("${it}", "${it}", "${it}", "${it}", it % 2 == 0)
        }
        adapter.submitList(list)
    }
}