package com.ramiro.castrejon.data.room.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.bookpedia.core.domain.onError
import com.plcoding.bookpedia.core.domain.onSuccess
import com.ramiro.castrejon.domain.RoomRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RoomListViewModel(
    private val roomRepository: RoomRepository
): ViewModel() {

    private val _rooms = MutableStateFlow(RoomListState())
    val rooms = _rooms.onStart {
        getRooms()
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        _rooms.value
    )

    private fun getRooms() {
        viewModelScope.launch {
            roomRepository.getRooms().onSuccess {results ->
                _rooms.update {
                    it.copy(isLoading = false, roomResults = results)
                }
                println("View Model: $results")
            }.onError { error->
                _rooms.update {
                    it.copy(isLoading = false, roomResults = emptyList())
                }
                println(error)
            }
        }
    }
}