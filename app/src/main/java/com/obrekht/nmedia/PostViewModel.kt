package com.obrekht.nmedia

import androidx.lifecycle.ViewModel
import com.obrekht.nmedia.repository.PostRepository
import com.obrekht.nmedia.repository.PostRepositoryInMemoryImpl

class PostViewModel : ViewModel() {
    private val repository: PostRepository = PostRepositoryInMemoryImpl()
    val data = repository.get()

    fun like() = repository.like()
    fun share() = repository.share()
}