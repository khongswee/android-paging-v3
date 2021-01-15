package com.kho.pagedv3.ui.main

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

class PagingLoadStateAdapter(private val adapter: UserListPagedAdapter) :
    LoadStateAdapter<NetworkStateViewHolder>() {
    override fun onBindViewHolder(holder: NetworkStateViewHolder, loadState: LoadState) {
        holder.bindTo(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): NetworkStateViewHolder {
        return NetworkStateViewHolder.create(parent) { adapter.retry() }
    }
}