package id.ac.umn.storyapplication.activity

import android.content.Context
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import id.ac.umn.storyapplication.api.StoryRepository
import id.ac.umn.storyapplication.model.StoryResponse

class StoryViewModel(private val storyRepository: StoryRepository) : ViewModel() {
    val story : LiveData<PagingData<StoryResponse.Story>> =
    storyRepository.getStory().cachedIn(viewModelScope)
}


class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return StoryViewModel(Injection.provideRepository(context)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }


}

