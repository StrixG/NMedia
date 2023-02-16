package com.obrekht.nmedia.posts

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.obrekht.nmedia.R
import com.obrekht.nmedia.databinding.ActivityMainBinding
import com.obrekht.nmedia.utils.hideKeyboard

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: PostsViewModel by viewModels()

    private val interactionListener = PostInteractionListener(
        onEdit = {
            viewModel.edit(it)
        },
        onLike = { viewModel.likeById(it.id) },
        onShare = { viewModel.shareById(it.id) },
        onRemove = {
            viewModel.removeById(it.id)
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = PostsAdapter(interactionListener).apply {
            setHasStableIds(true)
            hasStableIds()
        }
        binding.postList.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }
        viewModel.edited.observe(this) { post ->
            if (post.id != 0L) {
                with(binding) {
                    inputField.requestFocus()
                    inputField.setText(post.content)
                    editingContent.text = post.content
                    editingGroup.isVisible = true
                }
            }
        }

        binding.cancelEditing.setOnClickListener {
            viewModel.cancelEdit()
            hideEditing()
            clearInput()
        }
        
        binding.save.setOnClickListener {
            with(binding.inputField) {
                if (text.isNullOrBlank()) {
                    Toast.makeText(
                        this@MainActivity,
                        context.getString(R.string.error_empty_content),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    viewModel.save(text.toString())
                    hideEditing()
                    clearInput()
                }
            }
        }
    }

    private fun clearInput() {
        with(binding.inputField) {
            clearFocus()
            text?.clear()

            hideKeyboard()
        }
    }


    private fun hideEditing() {
        binding.editingContent.text = ""
        binding.editingGroup.isVisible = false
    }
}