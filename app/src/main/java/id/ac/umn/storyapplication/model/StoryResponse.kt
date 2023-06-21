package id.ac.umn.storyapplication.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class StoryResponse(
    val error: Boolean,
    val message: String,
    val listStory: ArrayList<Story>
    ) {
    @Entity(tableName = "storytable")
    data class Story(
        @PrimaryKey
        @field:SerializedName("id")
        val id: String,

        @field:SerializedName("name")
        val name: String,
        @field:SerializedName("description")
        val description: String,
        @field:SerializedName("photoUrl")
        val photoUrl: String,

        @field:SerializedName("lat")
        val lat : Float,
        @field:SerializedName("lon")
        val lon : Float
        )
}


