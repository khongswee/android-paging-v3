package com.kho.pagedv3.core

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.kho.pagedv3.core.model.DataUser
import kotlinx.coroutines.flow.Flow

class UserRepository(private val service: UserApi) {

    fun getSearchResultStream(
        errorCallback: (String) -> Unit,
        emptyCallBack: () -> Unit,
        query: String = ""
    ): Flow<PagingData<DataUser>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { UserPagingSource(service, errorCallback, emptyCallBack, query) }
        ).flow
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 20
    }
}