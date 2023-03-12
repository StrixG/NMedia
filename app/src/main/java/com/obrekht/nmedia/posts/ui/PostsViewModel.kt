package com.obrekht.nmedia.posts.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.obrekht.nmedia.posts.data.AppDb
import com.obrekht.nmedia.posts.data.PostRepository
import com.obrekht.nmedia.posts.data.PostRepositorySQLiteImpl
import com.obrekht.nmedia.posts.model.Post

private val emptyPost = Post(
    id = 0,
    author = "",
    content = "",
    published = ""
)

class PostsViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PostRepository = PostRepositorySQLiteImpl(
        AppDb.getInstance(application).postDao
    )
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
