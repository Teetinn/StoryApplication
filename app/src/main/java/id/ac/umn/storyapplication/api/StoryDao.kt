package id.ac.umn.storyapplication.api

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.ac.umn.storyapplication.model.StoryResponse

@Dao
interface StoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStory(quote: List<StoryResponse.Story>)

    @Query("SELECT * FROM storytable")
    fun getAllStory(): PagingSource<Int, StoryResponse.Story>

    @Query("DELETE FROM storytable")
    suspend fun deleteAll()
}