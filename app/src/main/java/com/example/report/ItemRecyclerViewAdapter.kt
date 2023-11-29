package com.example.report


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.report.databinding.ItemItemBinding

class ItemRecyclerViewAdapter : RecyclerView.Adapter<ItemRecyclerViewAdapter.ItemViewHolder>() {
    private var mList: ArrayList<Item> = ArrayList()

    fun submitList(list: ArrayList<Item>) {
        mList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val mBinding = ItemItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(mBinding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount(): Int = mList.size

    class ItemViewHolder(private val mBinding: ItemItemBinding) : RecyclerView.ViewHolder(mBinding.root) {
        fun bind(item: Item) {
            mBinding.apply {
                this.item = item
                executePendingBindings()

                root.setOnClickListener {
                    CustomDialog(root.context, item).show()
                }
            }
        }
    }
}
