package com.example.report

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import com.example.report.databinding.DialogCustomBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class CustomDialog(private val context: Context, item: Item) {
    private val dialog = Dialog(context)
    private val mBinding: DialogCustomBinding by lazy { DialogCustomBinding.inflate(LayoutInflater.from(context)) }

    private val displayMetrics = context.resources.displayMetrics
    private val width = (displayMetrics.widthPixels * 0.8).toInt()
    private val height = (displayMetrics.heightPixels * 0.5).toInt()

    init {
        mBinding.apply {
            this.item = item
            btnEdit.visibility = View.INVISIBLE

            if(item.user == Firebase.auth.currentUser?.email.toString()) {
                btnEdit.visibility = View.VISIBLE
            }

            btnEdit.setOnClickListener {
                dismiss()
                CustomEditDialog(context, item).show()
            }
        }
        initDialog()
    }

    private fun initDialog() = with(dialog) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(mBinding.root)
        setCancelable(true)
        window?.setLayout(width, height)
    }

    fun show() {
        dialog.show()
    }
    fun dismiss() {
        dialog.dismiss()
    }
}
