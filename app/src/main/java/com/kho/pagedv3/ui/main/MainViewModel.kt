package com.kho.pagedv3.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.kho.pagedv3.core.UserRepository
import com.kho.pagedv3.core.model.DataUser
import com.kho.pagedv3.ui.main.MainFragment.Companion.STATE_DATA_EMPTY
import com.kho.pagedv3.ui.main.MainFragment.Companion.STATE_DATA_ERROR
import kotlinx.coroutines.flow.Flow

class MainViewModel(private val repository: UserRepository) : ViewModel() {
    private var currentSearchResult: Flow<PagingData<DataUser>>? = null
    val liveDataInitialLoadData: MutableLiveData<Pair<Int,String>> = MutableLiveData()
    private var currentQueryValue: String? = null

    fun fetchData(query: String = ""): Flow<PagingData<DataUser>> {
        val lastResult = currentSearchResult
        if (query == currentQueryValue && lastResult != null) {
            return lastResult
        }
        currentQueryValue = query
        val newResult: Flow<PagingData<DataUser>> =
            repository.getSearchResultStream(errorCallback = {
                liveDataInitialLoadData.postValue(Pair(STATE_DATA_ERROR,it))
            }, emptyCallBack = {
                liveDataInitialLoadData.postValue(Pair(STATE_DATA_EMPTY,""))
            })
                .cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }

}