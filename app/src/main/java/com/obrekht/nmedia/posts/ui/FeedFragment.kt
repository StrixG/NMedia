package com.obrekht.nmedia.posts.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.obrekht.nmedia.R
import com.obrekht.nmedia.databinding.FragmentFeedBinding
import com.obrekht.nmedia.posts.model.Post
import com.obrekht.nmedia.utils.viewBinding

class FeedFragment : Fragment(R.layout.fragment_feed) {
    private val binding by viewBinding(FragmentFeedBinding::bind)

    private val viewModel: PostsViewModel by activityViewModels()

    private val interactionListener = object : PostInteractionListener {
        override fun onClick(post: Post) {
            val action = FeedFragmentDirections.actionOpenPost(post.id)
            findNavController().navigate(action)
        }

        override fun onVideoClick(post: Post) {
            if (post.video.isNotBlank()) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.video))
                startActivity(intent)
            }
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

        override fun onEdit(post: Post) {
            viewModel.edit(post)
            openPostEditor(post.content)
        }

        override fun onRemove(post: Post) {
            viewModel.removeById(post.id)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = PostsAdapter(interactionListener).apply {
            setHasStableIds(true)
        }
        binding.postList.adapter = adapter

        viewModel.data.observe(viewLifecycleOwner) { posts ->
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
            openPostEditor()
        }
    }

    private fun openPostEditor(text: String = "") {
        val action = FeedFragmentDirections.actionOpenPostEditor().apply {
            this.text = text
        }
        findNavController().navigate(action)
    }
}
