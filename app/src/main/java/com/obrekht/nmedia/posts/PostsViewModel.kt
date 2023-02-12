package com.obrekht.nmedia.posts

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.obrekht.nmedia.posts.repository.PostRepository
import com.obrekht.nmedia.posts.repository.PostRepositoryInMemoryImpl
import com.obrekht.nmedia.posts.repository.model.Post

private val emptyPost = Post(
    id = 0,
    author = "",
    content = "",
    published = ""
)

class PostsViewModel : ViewModel() {
    private val repository: PostRepository = PostRepositoryInMemoryImpl()
    val data = repository.getAll()
    val edited = MutableLiveData(emptyPost)

    fun save(content: String) {
        edited.value?.let {
            if (content.isBlank()) {
                // TODO: Notify activity about error
            } else {
                val post = it.copy(
                    content = content.trim()
                )
                repository.save(post)
            }
        }
        edited.value = emptyPost
    }

    fun edit(post: Post) {
        edited.value = post
    }

    fun cancelEdit() {
        edited.value = emptyPost
    }

    fun likeById(id: Long) = repository.likeById(id)
    fun shareById(id: Long) = repository.shareById(id)
    fun removeById(id: Long) = repository.removeById(id)
}