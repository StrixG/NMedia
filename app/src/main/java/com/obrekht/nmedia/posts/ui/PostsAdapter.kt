package com.obrekht.nmedia.posts.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.obrekht.nmedia.R
import com.obrekht.nmedia.databinding.ItemPostBinding
import com.obrekht.nmedia.posts.model.Post
import com.obrekht.nmedia.utils.StringUtils

class PostsAdapter(
    private val interactionListener: PostInteractionListener
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
        private val binding: ItemPostBinding,
        private val interactionListener: PostInteractionListener
    ) : RecyclerView.ViewHolder(binding.root) {

        private var post: Post? = null
        private val popupMenu = PopupMenu(binding.menu.context, binding.menu).apply {
            inflate(R.menu.options_post)
            setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.edit -> {
                        post?.let(interactionListener::onEdit)
                        true
                    }
                    R.id.remove -> {
                        post?.let(interactionListener::onRemove)
                        true
                    }
                    else -> false
                }
            }
        }

        init {
            with(binding) {
                card.setOnClickListener {
                    post?.let(interactionListener::onClick)
                }
                like.setOnClickListener {
                    post?.let(interactionListener::onLike)
                }
                share.setOnClickListener {
                    post?.let(interactionListener::onShare)
                }

                val onVideoClick = View.OnClickListener {
                    post?.let(interactionListener::onVideoClick)
                }
                videoThumbnail.setOnClickListener(onVideoClick)
                videoTitle.setOnClickListener(onVideoClick)

                menu.setOnClickListener {
                    popupMenu.show()
                }
            }
        }

        fun bind(post: Post) {
            this.post = post

            binding.apply {
                avatar.setImageResource(R.drawable.ic_launcher_foreground)

                author.text = post.author
                published.text = post.published
                content.text = post.content

                // TODO: Get video data from the post
                videoThumbnail.setImageResource(R.drawable.sample_thumbnail)
                videoTitle.text = "Шоурил студентов курса «Моушн-дизайнер в 2D и 3D»"
                video.isVisible = post.video.isNotBlank()

                like.isChecked = post.likedByMe
                like.text = StringUtils.getCompactNumber(post.likes)
                share.text = StringUtils.getCompactNumber(post.shares)
                viewsCount.text = StringUtils.getCompactNumber(post.views)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = ViewHolder(binding, interactionListener)

        return holder
    }

    override fun getItemId(position: Int): Long = getItem(position).id

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }
}
