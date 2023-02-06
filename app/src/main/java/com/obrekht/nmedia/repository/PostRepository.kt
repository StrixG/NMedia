package com.obrekht.nmedia.repository

import androidx.lifecycle.LiveData
import com.obrekht.nmedia.model.Post

interface PostRepository {
    fun get(): LiveData<Post>
    fun like()
    fun share()
}