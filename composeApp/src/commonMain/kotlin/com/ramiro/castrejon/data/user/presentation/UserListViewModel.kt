package com.ramiro.castrejon.data.user.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.bookpedia.core.domain.onError
import com.plcoding.bookpedia.core.domain.onSuccess
import com.ramiro.castrejon.domain.User
import com.ramiro.castrejon.domain.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserListViewModel(
    private val userRepository: UserRepository
) : ViewModel(){

    private val _users= MutableStateFlow(UserListState())
    private val _selectedUser = MutableStateFlow(User())
    var selectedUser = _selectedUser
   val users = _users.onStart {
        getUsers()
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        _users.value
    )

    fun selectUser(user: User){
        _selectedUser.value = user
    }

    fun getUsers(){
        viewModelScope.launch  {
            val result = userRepository.getUsers().onSuccess { results ->
                _users.update {
                    it.copy(isLoading = false, searchResults = results )
                }
                println("get users results: ${results}")
            }
                .onError { error ->
                    _users.update {
                        it.copy(isLoading = false, searchResults = emptyList())
                    }
                    println(error)
                }

        }
    }

}