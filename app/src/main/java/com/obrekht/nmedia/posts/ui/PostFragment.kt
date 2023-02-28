package com.obrekht.nmedia.posts.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.obrekht.nmedia.R
import com.obrekht.nmedia.databinding.ItemPostBinding
import com.obrekht.nmedia.posts.model.Post
import com.obrekht.nmedia.utils.viewBinding

class PostFragment : Fragment(R.layout.item_post) {
    private val binding by viewBinding(ItemPostBinding::bind)

    private val viewModel: PostsViewModel by activityViewModels()
    private val args: PostFragmentArgs by navArgs()
    private var post: Post? = null

    private val interactionListener = object : PostInteractionListener {
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
            findNavController().navigateUp()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.data.observe(viewLifecycleOwner) {
            post = it.find { post -> post.id == args.postId }
            post?.let { post ->
                val viewHolder = PostsAdapter.ViewHolder(binding, interactionListener)
                viewHolder.bind(post)

                binding.content.maxLines = Int.MAX_VALUE
            }
        }
    }

    private fun openPostEditor(text: String = "") {
        val action = PostFragmentDirections.actionOpenEditor().apply {
            this.text = text
        }
        findNavController().navigate(action)
    }
}
