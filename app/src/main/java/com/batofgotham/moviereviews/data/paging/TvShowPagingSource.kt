package com.batofgotham.moviereviews.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.batofgotham.moviereviews.data.model.TvShow
import com.batofgotham.moviereviews.data.remote.movies.ApiService

class TvShowPagingSource(
    private val apiService: ApiService
) : PagingSource<Int, TvShow>() {
    override fun getRefreshKey(state: PagingState<Int, TvShow>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)

        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TvShow> {
        return try {
            val position = params.key ?: 1
            val response = apiService.getTopRatedTvShows(position)

            return LoadResult.Page(
                data = response.results,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (position == response.total_pages) null else position + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}