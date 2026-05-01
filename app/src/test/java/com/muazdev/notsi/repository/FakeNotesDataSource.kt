package com.muazdev.notsi.repository

import com.muazdev.notsi.domain.NotesDataSource
import com.muazdev.notsi.domain.NotesModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class FakeNotesDataSource : NotesDataSource {

    private val notes = mutableListOf<NotesModel>()
    private val notesFlow = MutableStateFlow<List<NotesModel>>(notes)

    override suspend fun getSingleNote(id: Long): NotesModel? {
        return notes.find { it.id == id }
    }

    override fun getAllNotes(): Flow<List<NotesModel>> {
        return notesFlow
    }

    override suspend fun deleteNote(id: Long) {
        notes.removeIf { it.id == id }
        refreshFlow()
    }

    override suspend fun upsertNote(id: Long?, title: String, description: String) {
        val noteId = id ?: ((notes.maxOfOrNull { it.id } ?: 0L) + 1L)
        notes.removeIf { it.id == noteId }
        notes.add(NotesModel(noteId, title, description))
        refreshFlow()
    }

    private fun refreshFlow() {
        notesFlow.value = notes.toList()
    }
}
