package com.obrekht.nmedia.posts

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.obrekht.nmedia.R
import com.obrekht.nmedia.databinding.ActivityMainBinding
import com.obrekht.nmedia.posts.model.Post

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: PostsViewModel by viewModels()

    private val addEditPostLauncher =
        registerForActivityResult(AddEditPostResultContract()) { result ->
            if (result == null) {
                viewModel.cancelEdit()
            } else {
                viewModel.save(result)
            }
        }

    private val interactionListener = object : PostInteractionListener {
        override fun onEdit(post: Post) {
            viewModel.edit(post)
            addEditPostLauncher.launch(post.content)
        }

        override fun onLike(post: Post) {
            viewModel.likeById(post.id)
        }

        override fun onShare(post: Post) {
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, post.content)
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(intent, getString(R.string.chooser_share_post))
            startActivity(shareIntent)

            viewModel.shareById(post.id)
        }

        override fun onRemove(post: Post) {
            viewModel.removeById(post.id)
        }

        override fun onVideoClick(post: Post) {
            if (post.video.isNotBlank()) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.video))
                startActivity(intent)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = PostsAdapter(interactionListener).apply {
            setHasStableIds(true)
        }
        binding.postList.adapter = adapter

        viewModel.data.observe(this) { posts ->
            val isNewPost = adapter.currentList.size < posts.size
            adapter.submitList(posts) {
                if (isNewPost) {
                    binding.postList.smoothScrollToPosition(0)
                }
            }

            binding.emptyText.isVisible = posts.isEmpty()
            binding.postList.isVisible = posts.isNotEmpty()
        }

        binding.buttonAddPost.setOnClickListener {
            addEditPostLauncher.launch(null)
        }
    }
}