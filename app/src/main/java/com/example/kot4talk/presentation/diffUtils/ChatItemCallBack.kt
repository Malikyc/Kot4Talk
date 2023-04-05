package com.example.kot4talk.presentation.diffUtils

import androidx.recyclerview.widget.DiffUtil.ItemCallback
import com.example.kot4talk.domain.entities.Chat

class ChatItemCallBack : ItemCallback<Chat>() {
    override fun areItemsTheSame(oldItem: Chat, newItem: Chat): Boolean {
        return  oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Chat, newItem: Chat): Boolean {
        return  oldItem == newItem
    }
}