package com.muazdev.notsi.data.local

import com.muazdev.notsi.NotesEntity
import kotlinx.coroutines.flow.Flow

interface NotesDataSource {

    suspend fun getSingleNote(id: Long): NotesEntity?

    fun getAllNotes(): Flow<List<NotesEntity>>

    suspend fun deleteNote(id: Long)

    suspend fun upsertNote(id: Long? = null, title: String, description: String)
}
