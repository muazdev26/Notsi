package com.muazdev.notsi.data.mapper

import com.muazdev.notsi.NotesEntity
import com.muazdev.notsi.domain.NotesModel

fun NotesEntity.toNotesModel() = NotesModel(id, title, description)
