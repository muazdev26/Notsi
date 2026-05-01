package com.muazdev.notsi.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.muazdev.notsi.NotesDb
import com.muazdev.notsi.data.local.NotesDataSourceImpl
import com.muazdev.notsi.domain.NotesDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [AppModule::class]
)
object TestAppModule {

    @Provides
    @Singleton
    fun provideSqlDriver(): SqlDriver {
        val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        NotesDb.Schema.create(driver)
        return driver
    }

    @Provides
    @Singleton
    fun provideNotesDataSource(
        sqlDriver: SqlDriver,
        dispatcher: CoroutineDispatcher
    ): NotesDataSource {
        return NotesDataSourceImpl(NotesDb(sqlDriver), dispatcher)
    }

    @Provides
    @Singleton
    fun provideDispatcher(): CoroutineDispatcher = Dispatchers.Unconfined
}
