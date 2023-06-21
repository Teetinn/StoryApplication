package id.ac.umn.storyapplication

import id.ac.umn.storyapplication.model.StoryResponse

object DataDummy {

    fun generateDummyStoryResponse(): List<StoryResponse.Story> {
        val items: MutableList<StoryResponse.Story> = arrayListOf()
        for (i in 1..20) {
            val story = StoryResponse.Story(
                id = "id_$i",
                name = "Name $i",
                description = "Description $i",
                photoUrl = "",
                lat = i.toFloat(),
                lon = i.toFloat(),
            )
            items.add(story)
        }
        return items
    }
}