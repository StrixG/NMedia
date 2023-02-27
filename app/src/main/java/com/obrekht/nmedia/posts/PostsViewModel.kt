package com.obrekht.nmedia.posts

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.obrekht.nmedia.posts.model.Post
import com.obrekht.nmedia.posts.repository.PostRepository
import com.obrekht.nmedia.posts.repository.PostRepositoryFileImpl

private val emptyPost = Post(
    id = 0,
    author = "",
    content = "",
    published = ""
)

class PostsViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PostRepository = PostRepositoryFileImpl(application)
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