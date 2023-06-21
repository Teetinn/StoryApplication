package id.ac.umn.storyapplication.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import id.ac.umn.storyapplication.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvDetailItem.setText(intent.getStringExtra("intent_title"))
        binding.tvDescription.setText(intent.getStringExtra("intent_desc"))
        Glide.with(this)
            .load(intent.getStringExtra("intent_photo"))
            .transition(DrawableTransitionOptions.withCrossFade())
            .centerCrop()
            .into(binding.ivDetailPhoto)
    }
}