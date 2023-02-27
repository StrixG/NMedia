package com.obrekht.nmedia.posts.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.obrekht.nmedia.posts.model.Post

class PostRepositorySharedPrefsImpl(
    private val context: Context
) : PostRepository {
    private val gson = Gson()
    private val prefs = context.getSharedPreferences("repo", Context.MODE_PRIVATE)
    private var postList = emptyList<Post>()
    private var nextId = 1L

    private val data = MutableLiveData(postList)

    init {
        prefs.getString(KEY, null)?.let {
            postList = gson.fromJson(it, TYPE)
            data.value = postList
        }
    }

    override fun getAll(): LiveData<List<Post>> = data

    override fun save(post: Post) {
        if (post.id == 0L) {
            postList = listOf(
                post.copy(
                    id = nextId++,
                    author = "Nikita",
                    likedByMe = false,
                    published = "now"
                )
            ) + postList
        } else {
            postList = postList.map {
                if (it.id == post.id) {
                    it.copy(
                        content = post.content
                    )
                } else it
            }
        }
        data.value = postList
        sync()
    }

    override fun likeById(id: Long) {
        postList = postList.map {
            if (it.id == id) {
                val likes = it.likes + if (it.likedByMe) -1 else 1
                it.copy(
                    likedByMe = !it.likedByMe,
                    likes = likes
                )
            } else it
        }
        data.value = postList
        sync()
    }

    override fun shareById(id: Long) {
        postList = postList.map {
            if (it.id == id) {
                it.copy(
                    shares = it.shares + 1
                )
            } else it
        }
        data.value = postList
        sync()
    }

    override fun removeById(id: Long) {
        postList = postList.filter { it.id != id }
        data.value = postList
        sync()
    }

    private fun sync() {
        with(prefs.edit()) {
            putString(KEY, gson.toJson(postList))
            apply()
        }
    }

    companion object {
        private const val KEY = "posts"
        private val TYPE = TypeToken.getParameterized(List::class.java, Post::class.java).type
    }
}