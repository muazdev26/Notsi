package com.muazdev.notsi.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muazdev.notsi.domain.NotesDataSource
import com.muazdev.notsi.domain.NotesModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesSharedViewModel @Inject constructor(
    private val notesDataSource: NotesDataSource
) : ViewModel() {

    val sharedNote = MutableStateFlow(NotesModel(0, "", ""))
    private var _needToObserveAgain = MutableLiveData(false)
    val needToObserveAgain: LiveData<Boolean> = _needToObserveAgain

    fun needToObserveAgain(value: Boolean = true) {
        _needToObserveAgain.value = value
    }

    fun upsertData(id: Long? = null, title: String, desc: String) =
        viewModelScope.launch(Dispatchers.IO) {
            notesDataSource.upsertNote(id = id, title = title, description = desc)
        }

    fun getAllNotes() = flow {
        notesDataSource.getAllNotes().collect {
            emit(it)
        }
    }

    fun deleteNote(id: Long) = viewModelScope.launch {
        notesDataSource.deleteNote(id)
    }

}