package com.obrekht.nmedia.posts

import com.obrekht.nmedia.posts.repository.model.Post

typealias OnEditListener = (post: Post) -> Unit
typealias OnRemoveListener = (post: Post) -> Unit
typealias OnLikeListener = (post: Post) -> Unit
typealias OnShareListener = (post: Post) -> Unit

class PostInteractionListener(
    var onEdit: OnEditListener = {},
    var onRemove: OnRemoveListener = {},
    var onLike: OnLikeListener = {},
    var onShare: OnShareListener = {}
)