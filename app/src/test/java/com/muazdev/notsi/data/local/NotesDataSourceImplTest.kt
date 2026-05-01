package com.muazdev.notsi.data.local

import app.cash.turbine.test
import com.muazdev.notsi.domain.NotesDataSource
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import javax.inject.Inject

@HiltAndroidTest
@Config(application = HiltTestApplication::class)
@RunWith(RobolectricTestRunner::class)
class NotesDataSourceImplTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var dataSource: NotesDataSource

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun `upsertNote should insert a new note and getSingleNote should retrieve it`() = runTest {
        val title = "Test Title"
        val desc = "Test Description"

        dataSource.upsertNote(id = 1, title = title, description = desc)

        val note = dataSource.getSingleNote(1)

        assertNotNull(note)
        assertEquals(1L, note?.id)
        assertEquals(title, note?.title)
        assertEquals(desc, note?.note)
    }

    @Test
    fun `upsertNote with existing ID should update the note`() = runTest {
        dataSource.upsertNote(id = 1, title = "Original Title", description = "Original Desc")

        dataSource.upsertNote(id = 1, title = "Updated Title", description = "Updated Desc")

        val note = dataSource.getSingleNote(1)
        assertNotNull(note)
        assertEquals("Updated Title", note?.title)
        assertEquals("Updated Desc", note?.note)
    }

    @Test
    fun `deleteNote should remove the note from database`() = runTest {
        dataSource.upsertNote(id = 1, title = "Title", description = "Desc")
        
        dataSource.deleteNote(1)

        val note = dataSource.getSingleNote(1)
        assertNull(note)
    }

    @Test
    fun `deleteNote with non-existent ID should not throw exception`() = runTest {
        dataSource.deleteNote(99)
        
        val note = dataSource.getSingleNote(99)
        assertNull(note)
    }

    @Test
    fun `getAllNotes should return all notes as a flow`() = runTest {
        dataSource.upsertNote(id = 1, title = "Note 1", description = "Desc 1")
        dataSource.upsertNote(id = 2, title = "Note 2", description = "Desc 2")

        dataSource.getAllNotes().test {
            val notes = awaitItem()
            assertEquals(2, notes.size)
            assertEquals("Note 1", notes[0].title)
            assertEquals("Note 2", notes[1].title)
        }
    }
}
