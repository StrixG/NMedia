package com.obrekht.nmedia

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.obrekht.nmedia.databinding.ActivityMainBinding
import com.obrekht.nmedia.model.Post
import com.obrekht.nmedia.utils.StringUtils

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = Post(
            id = 1,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            published = "21 мая в 18:36",
            likes = 7450,
            likedByMe = false,
            shares = 996,
            views = 44731
        )

        with(binding) {
            avatar.setImageResource(R.drawable.ic_launcher_foreground)

            author.text = post.author
            published.text = post.published
            content.text = post.content

            likeCount.text = StringUtils.getCompactNumber(post.likes)
            if (post.likedByMe) {
                like.setImageResource(R.drawable.ic_heart_24)
            }
            shareCount.text = StringUtils.getCompactNumber(post.shares)
            viewsCount.text = StringUtils.getCompactNumber(post.views)

            like.setOnClickListener {
                post.likedByMe = !post.likedByMe
                if (post.likedByMe) {
                    like.setImageResource(R.drawable.ic_heart_24)
                    post.likes++
                } else {
                    like.setImageResource(R.drawable.ic_heart_border_24)
                    post.likes--
                }

                likeCount.text = StringUtils.getCompactNumber(post.likes)
            }

            share.setOnClickListener {
                post.shares++
                shareCount.text = StringUtils.getCompactNumber(post.shares)
            }
        }
    }
}