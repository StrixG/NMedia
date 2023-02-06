package com.obrekht.nmedia.posts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.obrekht.nmedia.R
import com.obrekht.nmedia.databinding.ItemPostBinding
import com.obrekht.nmedia.posts.repository.model.Post
import com.obrekht.nmedia.utils.StringUtils

typealias OnLikeListener = (post: Post) -> Unit
typealias OnShareListener = (post: Post) -> Unit

class PostsAdapter(
    val onLikeListener: OnLikeListener? = null,
    val onShareListener: OnShareListener? = null
) : ListAdapter<Post, PostsAdapter.ViewHolder>(
    AsyncDifferConfig.Builder(PostDiffCallback()).build()
) {

    class PostDiffCallback : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem == newItem
        }
    }

    class ViewHolder(
        private val binding: ItemPostBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post) {
            binding.apply {
                avatar.setImageResource(R.drawable.ic_launcher_foreground)

                author.text = post.author
                published.text = post.published
                content.text = post.content

                if (post.likedByMe) {
                    like.setImageResource(R.drawable.ic_heart_24)
                } else {
                    like.setImageResource(R.drawable.ic_heart_border_24)
                }
                likeCount.text = StringUtils.getCompactNumber(post.likes)
                shareCount.text = StringUtils.getCompactNumber(post.shares)
                viewsCount.text = StringUtils.getCompactNumber(post.views)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = ViewHolder(binding)

        binding.apply {
            like.setOnClickListener {
                val post = getItem(holder.bindingAdapterPosition)
                onLikeListener?.invoke(post)
            }
            share.setOnClickListener {
                val post = getItem(holder.bindingAdapterPosition)
                onShareListener?.invoke(post)
            }
        }

        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }
}