package id.ac.umn.storyapplication.activity

import android.content.Context
import id.ac.umn.storyapplication.api.RetrofitClient
import id.ac.umn.storyapplication.api.StoryDatabase
import id.ac.umn.storyapplication.api.StoryRepository

object Injection {
    fun provideRepository(context: Context): StoryRepository {
        val database = StoryDatabase.getDatabase(context)
        val apiService = RetrofitClient.getApiService()
        return StoryRepository(context, database, apiService)
    }
}