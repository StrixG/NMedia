package com.obrekht.nmedia.posts.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.obrekht.nmedia.posts.model.Post

class PostRepositorySQLiteImpl(
    private val dao: PostDao
) : PostRepository {
    private var postList = emptyList<Post>()
    private val data = MutableLiveData(postList)

    init {
        postList = dao.getAll()
        data.value = postList
    }

    override fun getAll(): LiveData<List<Post>> = data

    override fun save(post: Post) {
        val id = post.id
        val saved = dao.save(post)
        postList = if (id == 0L) {
            listOf(saved) + postList
        } else {
            postList.map {
                if (it.id == id) saved else it
            }
        }
        data.value = postList
    }

    override fun likeById(id: Long) {
        dao.likeById(id)
        postList = postList.map {
            if (it.id == id) {
                it.copy(
                    likedByMe = !it.likedByMe,
                    likes = if (it.likedByMe) it.likes - 1 else it.likes + 1
                )
            } else it
        }
        data.value = postList
    }

    override fun shareById(id: Long) {
        dao.shareById(id)
        postList = postList.map {
            if (it.id == id) {
                it.copy(
                    shares = it.shares + 1
                )
            } else it
        }
        data.value = postList
    }

    override fun removeById(id: Long) {
        dao.removeById(id)
        postList = postList.filter { it.id != id }
        data.value = postList
    }
}

