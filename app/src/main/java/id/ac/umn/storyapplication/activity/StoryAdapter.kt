package id.ac.umn.storyapplication.activity

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import id.ac.umn.storyapplication.databinding.StoryItemBinding
import id.ac.umn.storyapplication.model.StoryResponse


class StoryAdapter() : PagingDataAdapter<StoryResponse.Story, StoryAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = StoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind( holder.itemView.context, data)
        }
    }

    class MyViewHolder(private val binding: StoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(context: Context, data: StoryResponse.Story) {
            binding.tvItem.text = data.name
            Glide.with(binding.ivPhoto)
                .load(data.photoUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .centerCrop()
                .into(binding.ivPhoto)

            with(binding) {
                root.setOnClickListener {
                    Intent(context, DetailActivity::class.java).also { intent ->
                        intent
                            .putExtra("intent_title", data.name)
                            .putExtra("intent_photo", data.photoUrl)
                            .putExtra("intent_desc", data.description)
                        context.startActivity(intent)
                    }
                }
            }
        }
    }

    companion object {
        private const val TAG = "StoryAdapter"

        val DIFF_CALLBACK = object: DiffUtil.ItemCallback<StoryResponse.Story>(){
            override fun areItemsTheSame(
                oldItem: StoryResponse.Story,
                newItem: StoryResponse.Story
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: StoryResponse.Story,
                newItem: StoryResponse.Story
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}
