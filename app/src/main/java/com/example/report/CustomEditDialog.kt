package com.example.report

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.Window
import com.example.report.databinding.DialogCustomEditBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class CustomEditDialog(private val context: Context, item: Item = Item()) {
    private val dialog = Dialog(context)
    private val mBinding: DialogCustomEditBinding by lazy { DialogCustomEditBinding.inflate(LayoutInflater.from(context)) }
    private val firesStore = FiresStore()

    private val displayMetrics = context.resources.displayMetrics
    private val width = (displayMetrics.widthPixels * 0.8).toInt()
    private val height = (displayMetrics.heightPixels * 0.5).toInt()

    init {
        mBinding.apply {
            this.item = item
            btnConfirm.setOnClickListener {
                val newItem = Item(
                    title = editTitle.text.toString(),
                    content = editContent.text.toString(),
                    user = Firebase.auth.currentUser?.email.toString(),
                    price = editPrice.text.toString().toLong(),
                    isSold = chkSold.isChecked
                )

                if(item.key.isEmpty()) {
                    firesStore.addData(newItem)
                }else {
                    firesStore.updateData(item.key, newItem)
                }
                dismiss()
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
