package com.obrekht.nmedia.posts

import androidx.lifecycle.ViewModel
import com.obrekht.nmedia.posts.repository.PostRepository
import com.obrekht.nmedia.posts.repository.PostRepositoryInMemoryImpl

class PostsViewModel : ViewModel() {
    private val repository: PostRepository = PostRepositoryInMemoryImpl()
    val data = repository.getAll()

    fun likeById(id: Long) = repository.likeById(id)
    fun shareById(id: Long) = repository.shareById(id)
}