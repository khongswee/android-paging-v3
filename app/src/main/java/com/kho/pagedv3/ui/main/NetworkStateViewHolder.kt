package com.kho.pagedv3.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.kho.pagedv3.databinding.NetworkStateItemBinding

class NetworkStateViewHolder(
    private val networkStateItemBinding: NetworkStateItemBinding,
    private val retryCallBack: () -> Unit
) : RecyclerView.ViewHolder(networkStateItemBinding.root) {
    fun bindTo(loadState: LoadState) {
        networkStateItemBinding.progressBar.isVisible = loadState is LoadState.Loading
        networkStateItemBinding.btRetry.isVisible = loadState is LoadState.Error
        networkStateItemBinding.tvErrorMsg.isVisible = !(loadState as? LoadState.Error)?.error?.message.isNullOrBlank()
        networkStateItemBinding.tvErrorMsg.text = (loadState as? LoadState.Error)?.error?.message
        networkStateItemBinding.btRetry.setOnClickListener {
            retryCallBack.invoke()
        }
    }

    companion object {
        fun create(parent: ViewGroup,retryCallBack: () -> Unit): NetworkStateViewHolder {
            val binding =
                NetworkStateItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return NetworkStateViewHolder(binding,retryCallBack)
        }
    }
}