package com.obrekht.nmedia.posts.repository

import androidx.lifecycle.LiveData
import com.obrekht.nmedia.posts.model.Post

interface PostRepository {
    fun getAll(): LiveData<List<Post>>
    fun likeById(id: Long)
    fun shareById(id: Long)
    fun removeById(id: Long)
    fun save(post: Post)
}