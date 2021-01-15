package com.kho.pagedv3.ui.main

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.kho.pagedv3.core.model.DataUser

class UserListPagedAdapter(private val onClickItem: (DataUser) -> Unit) :
    PagingDataAdapter<DataUser, UserViewHolder>(CONTENT_COMPARATOR) {
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    override fun onBindViewHolder(
        holder: UserViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        onBindViewHolder(holder, position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder.create(parent, onClickItem)
    }

    companion object {
        val CONTENT_COMPARATOR = object : DiffUtil.ItemCallback<DataUser>() {
            override fun areContentsTheSame(oldItem: DataUser, newItem: DataUser): Boolean =
                oldItem == newItem
            override fun areItemsTheSame(oldItem: DataUser, newItem: DataUser): Boolean =
                oldItem.id == newItem.id
        }
    }
}