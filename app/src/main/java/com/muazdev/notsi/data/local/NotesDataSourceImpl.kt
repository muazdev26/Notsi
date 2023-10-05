package com.muazdev.notsi.data.local

import com.muazdev.notsi.NotesDb
import com.muazdev.notsi.data.mapper.toNotesModel
import com.muazdev.notsi.domain.NotesModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class NotesDataSourceImpl(
    notesDb: NotesDb,
    private val dispatcher: CoroutineDispatcher
) : NotesDataSource {

    private val queries = notesDb.notesEntityQueries
    override suspend fun getSingleNote(id: Long): NotesModel? {
        return withContext(dispatcher) {
            queries.getSingleNote(id).executeAsOneOrNull()?.toNotesModel()
        }
    }

    override fun getAllNotes() = flow {
        emit(queries.getAllNotes().executeAsList().map { it.toNotesModel() })
    }

    override suspend fun deleteNote(id: Long) {
        withContext(dispatcher) {
            queries.deleteNote(id)
        }
    }

    override suspend fun upsertNote(id: Long?, title: String, description: String) {
        withContext(dispatcher) {
            queries.upsertNote(id, title, description)
        }
    }
}