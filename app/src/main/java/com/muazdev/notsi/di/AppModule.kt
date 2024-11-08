package com.muazdev.notsi.di

import android.app.Application
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.muazdev.notsi.NotesDb
import com.muazdev.notsi.domain.NotesDataSource
import com.muazdev.notsi.data.local.NotesDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSqlDelightProvider(app: Application): SqlDriver {
        return AndroidSqliteDriver(
            schema = NotesDb.Schema,
            context = app,
            name = "notes.db"
        )
    }

    @Provides
    @Singleton
    fun provideNotesDataSource(
        sqliteDriver: SqlDriver,
        dispatcher: CoroutineDispatcher
    ): NotesDataSource {
        return NotesDataSourceImpl(
            NotesDb(sqliteDriver), dispatcher
        )
    }

    @Provides
    @Singleton
    fun providesDispatcher(): CoroutineDispatcher = Dispatchers.IO
}