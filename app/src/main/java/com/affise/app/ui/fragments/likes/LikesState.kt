package com.affise.app.ui.fragments.likes

import com.affise.app.ui.fragments.likes.adapters.ProductLikeEntity

sealed class LikesState {
    object LoadingState : LikesState()
    object UpdateState : LikesState()
    object ErrorState : LikesState()
    data class DataState(
        val likeProducts: List<ProductLikeEntity>
    ) : LikesState()
}