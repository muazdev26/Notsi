package com.muazdev.notsi.domain

import kotlinx.coroutines.flow.Flow

interface NotesDataSource {

    suspend fun getSingleNote(id: Long): NotesModel?

    fun getAllNotes(): Flow<List<NotesModel>>

    suspend fun deleteNote(id: Long)

    suspend fun upsertNote(id: Long? = null, title: String, description: String)
}
