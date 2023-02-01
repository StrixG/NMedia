package com.obrekht.nmedia

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.obrekht.nmedia.databinding.ActivityMainBinding
import com.obrekht.nmedia.utils.StringUtils

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: PostViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.data.observe(this) { post ->
            with(binding) {
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

        with(binding) {
            like.setOnClickListener {
                viewModel.like()
            }

            share.setOnClickListener {
                viewModel.share()
            }
        }
    }
}