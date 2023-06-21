package id.ac.umn.storyapplication.api

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.paging.*
import id.ac.umn.storyapplication.model.StoryResponse
import id.ac.umn.storyapplication.storage.SharedPrefManager

class StoryRepository(context : Context, private val storyDatabase: StoryDatabase, private val apiService: Api) {

    val loginTokenSpm : String = SharedPrefManager.getInstance(context).loginResult.token

    fun getStory(): LiveData<PagingData<StoryResponse.Story>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = StoryRemoteMediator("Bearer $loginTokenSpm", storyDatabase, apiService),
            pagingSourceFactory = {
                storyDatabase.storyDao().getAllStory()
            }
        ).liveData
    }
}