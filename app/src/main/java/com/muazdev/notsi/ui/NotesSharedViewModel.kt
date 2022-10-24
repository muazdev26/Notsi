package com.muazdev.notsi.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muazdev.notsi.NotesEntity
import com.muazdev.notsi.data.local.NotesDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesSharedViewModel @Inject constructor(
    private val notesDataSource: NotesDataSource
) : ViewModel() {

    val sharedNote = MutableStateFlow(NotesEntity(0, "", ""))

    fun upsertData(id: Long? = null, title: String, desc: String) = viewModelScope.launch {
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