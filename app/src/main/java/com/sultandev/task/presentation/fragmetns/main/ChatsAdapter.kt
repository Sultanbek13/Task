package com.sultandev.task.presentation.fragmetns.main

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sultandev.task.R
import com.sultandev.task.data.remote.models.userinfo.ItemChat
import com.sultandev.task.databinding.ItemListChatBinding

class ChatsAdapter : ListAdapter<ItemChat, ChatsAdapter.ViewHolder>(ChatsAdapterComparator) {

    private var itemClickListener:((ItemChat)->Unit)? = null

    fun setOnClickListener(block: (ItemChat) -> Unit) {
        itemClickListener = block
    }

    inner class ViewHolder(private val viewBinding: ItemListChatBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        init {
            viewBinding.root.setOnClickListener {
                itemClickListener?.invoke(getItem(absoluteAdapterPosition))
            }
        }

        @SuppressLint("ResourceAsColor", "SetTextI18n")
        fun bind() {
            viewBinding.apply {
                tvUserName.text = getItem(absoluteAdapterPosition).name
                if(getItem(absoluteAdapterPosition).status) {
                    userStatus.text = "Online"
                    userStatus.setTextColor(Color.parseColor("#5ECF56"))
                } else {
                    userStatus.text = "Offline"
                    userStatus.setTextColor(Color.parseColor("#C12E2E"))
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemListChatBinding.bind(
                LayoutInflater.from(parent.context).inflate(R.layout.item_list_chat, parent, false)
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind()
}

object ChatsAdapterComparator : DiffUtil.ItemCallback<ItemChat>() {
    override fun areItemsTheSame(oldItem: ItemChat, newItem: ItemChat): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ItemChat, newItem: ItemChat): Boolean {
        return oldItem.id == newItem.id && oldItem.status == newItem.status && oldItem.name == newItem.name
    }
}