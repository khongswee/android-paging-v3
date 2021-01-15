package com.kho.pagedv3.core

import androidx.paging.PagingSource
import com.kho.pagedv3.core.model.DataUser
import retrofit2.HttpException
import java.io.IOException

class UserPagingSource(
    private val service: UserApi,
    private val errorCallBack: (String) -> Unit,
    private val emptyCallBack: () -> Unit,
    private val query: String
) : PagingSource<Int, DataUser>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DataUser> {
        val position = params.key ?: 1
        return try {
            /*
             if the api can search, it will send param query like this and pass into your service.
             */
            val response = service.getUser(position.toString())
            val repos = response.await().data
            if (position == 1 && repos.isNullOrEmpty()) {
                emptyCallBack.invoke()
            }
            val nextKey = if (repos.isEmpty()) {
                null
            } else {
                position + (params.loadSize / 20)
            }
            LoadResult.Page(
                data = repos,
                prevKey = if (position == 1) null else position - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            if (position == 1) {
                errorCallBack.invoke(exception.localizedMessage)
            }
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            if (position == 1) {
                errorCallBack.invoke(exception.localizedMessage)
            }
            return LoadResult.Error(exception)
        }
    }
}