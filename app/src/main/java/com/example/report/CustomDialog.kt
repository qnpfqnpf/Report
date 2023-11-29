package com.example.report

import android.app.ActionBar
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.Window
import com.example.report.databinding.DialogCustomBinding

class CustomDialog(private val context: Context, item: Item) {
    private val dialog = Dialog(context)
    private val mBinding: DialogCustomBinding by lazy { DialogCustomBinding.inflate(LayoutInflater.from(context)) }

    private val displayMetrics = context.resources.displayMetrics
    private val width = (displayMetrics.widthPixels * 0.8).toInt()
    private val height = (displayMetrics.heightPixels * 0.5).toInt()

    init {

        mBinding.apply {
            this.item = item
            btnEdit.setOnClickListener {
                //TODO: edit
            }
        }
        initDialog()
    }

    private fun initDialog() = with(dialog) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(mBinding.root)
        window?.setLayout(width, height)
    }

    fun show() {
        dialog.show()
    }
    fun dismiss() {
        dialog.dismiss()
    }
}
