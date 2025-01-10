package com.ramiro.castrejon.data.user.presentation

import com.ramiro.castrejon.domain.User

data class UserListState (
    val searchResults: List<User> = emptyList(),
    val isLoading: Boolean = true
)