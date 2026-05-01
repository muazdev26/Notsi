package com.muazdev.notsi.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muazdev.notsi.domain.NotesDataSource
import com.muazdev.notsi.domain.NotesModel
import com.muazdev.notsi.util.Event
import com.muazdev.notsi.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesSharedViewModel @Inject constructor(
    private val notesDataSource: NotesDataSource
) : ViewModel() {

    val sharedNote = MutableStateFlow(NotesModel(0, "", ""))

    fun selectNote(note: NotesModel) {
        sharedNote.value = note
    }

    fun clearSelectedNote() {
        sharedNote.value = NotesModel(0, "", "")
    }

    private val _upsertNoteStatus = MutableLiveData<Event<Resource<NotesModel>>>()
    val upsertNoteStatus: LiveData<Event<Resource<NotesModel>>> = _upsertNoteStatus

    fun upsertData(id: Long? = null, title: String, desc: String) {
        if (title.isBlank()) {
            _upsertNoteStatus.postValue(Event(Resource.error("Kindly enter title", null)))
            return
        }
        if (desc.isBlank()) {
            _upsertNoteStatus.postValue(Event(Resource.error("Kindly enter description", null)))
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            notesDataSource.upsertNote(id = id, title = title, description = desc)
            _upsertNoteStatus.postValue(Event(Resource.success(null)))
        }
    }

    fun getAllNotes() = notesDataSource.getAllNotes()

    fun deleteNote(id: Long) = viewModelScope.launch {
        notesDataSource.deleteNote(id)
    }

}