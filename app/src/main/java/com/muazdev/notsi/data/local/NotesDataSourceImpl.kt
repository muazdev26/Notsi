package com.muazdev.notsi.data.local

import com.muazdev.notsi.NotesDb
import com.muazdev.notsi.NotesEntity
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class NotesDataSourceImpl(
    notesDb: NotesDb,
    private val dispatcher: CoroutineDispatcher
) : NotesDataSource {

    private val queries = notesDb.notesEntityQueries
    override suspend fun getSingleNote(id: Long): NotesEntity? {
        return withContext(dispatcher) {
            queries.getSingleNote(id).executeAsOneOrNull()
        }
    }

    override fun getAllNotes(): Flow<List<NotesEntity>> {
        return queries.getAllNotes().asFlow().mapToList()
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