package com.obrekht.nmedia.posts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.obrekht.nmedia.R
import com.obrekht.nmedia.databinding.ItemPostBinding
import com.obrekht.nmedia.posts.repository.model.Post
import com.obrekht.nmedia.utils.StringUtils

class PostsAdapter(
    var interactionListener: PostInteractionListener
) : ListAdapter<Post, PostsAdapter.ViewHolder>(
    AsyncDifferConfig.Builder(DiffCallback()).build()
) {

    class DiffCallback : DiffUtil.ItemCallback<Post>() {
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

                like.isChecked = post.likedByMe
                like.text = StringUtils.getCompactNumber(post.likes)
                share.text = StringUtils.getCompactNumber(post.shares)
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
                interactionListener.onLike(post)
            }
            share.setOnClickListener {
                val post = getItem(holder.bindingAdapterPosition)
                interactionListener.onShare(post)
            }

            val popupMenu = PopupMenu(menu.context, menu).apply {
                inflate(R.menu.options_post)
                setOnMenuItemClickListener { item ->
                    val post = getItem(holder.bindingAdapterPosition)
                    when (item.itemId) {
                        R.id.edit -> {
                            interactionListener.onEdit(post)
                            true
                        }
                        R.id.remove -> {
                            interactionListener.onRemove(post)
                            true
                        }
                        else -> false
                    }
                }
            }
            menu.setOnClickListener {
                popupMenu.show()
            }
        }

        return holder
    }

    override fun getItemId(position: Int): Long = getItem(position).id

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = getItem(position)
        holder.itemId
        holder.bind(post)
    }
}