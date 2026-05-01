package com.muazdev.notsi.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.muazdev.notsi.repository.FakeNotesDataSource
import com.muazdev.notsi.util.Status
import com.muazdev.notsi.util.getOrAwaitValueTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NotesSharedViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: NotesSharedViewModel
    private lateinit var fakeNotesDataSource: FakeNotesDataSource

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        fakeNotesDataSource = FakeNotesDataSource()
        viewModel = NotesSharedViewModel(fakeNotesDataSource)
    }

    @Test
    fun `upsert note with empty title, returns error`() {
        viewModel.upsertData(title = "", desc = "description")
        
        val value = viewModel.upsertNoteStatus.getOrAwaitValueTest()
        
        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
        assertThat(value.peekContent().message).isEqualTo("Kindly enter title")
    }

    @Test
    fun `upsert note with empty description, returns error`() {
        viewModel.upsertData(title = "Title", desc = "")
        
        val value = viewModel.upsertNoteStatus.getOrAwaitValueTest()
        
        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
        assertThat(value.peekContent().message).isEqualTo("Kindly enter description")
    }

    @Test
    fun `upsert note with valid input, returns success`() {
        viewModel.upsertData(title = "Title", desc = "Description")
        
        val value = viewModel.upsertNoteStatus.getOrAwaitValueTest()
        
        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.SUCCESS)
    }
}
