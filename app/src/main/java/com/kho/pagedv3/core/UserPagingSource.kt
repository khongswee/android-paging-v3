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
        val page = params.key ?: 1
        return try {
            val response = service.getUser(page.toString())
            val repos = response.await().data
            if (page == 1 && repos.isNullOrEmpty()) {
                emptyCallBack.invoke()
            }
            val nextKey = if (repos.isEmpty()) null else page.plus(1)
            val prevKey = if (page == 1) null else page.minus(1)
            LoadResult.Page(
                data = repos,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            if (page == 1) {
                errorCallBack.invoke(exception.localizedMessage)
            }
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            if (page == 1) {
                errorCallBack.invoke(exception.localizedMessage)
            }
            return LoadResult.Error(exception)
        }
    }

    companion object {
        private const val INITIAL_PAGE = 1
    }
}