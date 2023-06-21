package id.ac.umn.storyapplication.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import id.ac.umn.storyapplication.databinding.ActivityStoryBinding
import id.ac.umn.storyapplication.storage.SharedPrefManager


class StoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStoryBinding
    private val storyViewModel: StoryViewModel by viewModels {
        ViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonLogout.setOnClickListener{

            SharedPrefManager.getInstance(this).clear()

            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)
        }

        binding.buttonAddStory.setOnClickListener{
            val intent = Intent(applicationContext, CreateStory::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)
        }

        binding.buttonMaps.setOnClickListener{
            val intent = Intent(applicationContext, MapsActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)
        }
    }

    private fun getData() {
        val adapter = StoryAdapter()

        binding.rvStory.adapter = adapter
        storyViewModel.story.observe(this, {
            adapter.submitData(lifecycle, it)
        })

    }

    override fun onResume(){
        super.onResume()
        binding.rvStory.layoutManager = LinearLayoutManager(this)

        getData()
    }

    override fun onStart() {
        super.onStart()

        if(!SharedPrefManager.getInstance(this).isLoggedIn) {
            val intent = Intent(applicationContext, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)
        }
    }
}
