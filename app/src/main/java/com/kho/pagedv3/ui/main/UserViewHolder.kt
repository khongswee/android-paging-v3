package com.kho.pagedv3.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kho.pagedv3.core.model.DataUser
import com.kho.pagedv3.databinding.MainUserItemBinding

class UserViewHolder(
    private val binding: MainUserItemBinding,
    private val onClickItem: (DataUser) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    companion object {
        fun create(parent: ViewGroup, onClickItem: (DataUser) -> Unit): UserViewHolder {
            val binding = MainUserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return UserViewHolder(binding,onClickItem)
        }
    }

    fun bind(data:DataUser){
        binding.item = data
    }
}
