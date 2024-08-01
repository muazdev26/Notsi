package com.muazdev.notsi.data.local

import com.muazdev.notsi.domain.NotesModel
import kotlinx.coroutines.flow.Flow

interface NotesDataSource {

    suspend fun getSingleNote(id: Long): NotesModel?

    fun getAllNotes(): Flow<List<NotesModel>>

    suspend fun deleteNote(id: Long)

    suspend fun upsertNote(id: Long? = null, title: String, description: String)
}
