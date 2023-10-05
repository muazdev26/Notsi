package com.muazdev.notsi.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.muazdev.notsi.NotesEntity
import com.muazdev.notsi.R
import com.muazdev.notsi.base.BaseFragment
import com.muazdev.notsi.databinding.FragmentHomeBinding
import com.muazdev.notsi.domain.NotesModel
import com.muazdev.notsi.ui.NotesSharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val notesSharedViewModel: NotesSharedViewModel by activityViewModels()
    private lateinit var notesAdapter: NotesAdapter

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fabAdd.setOnClickListener {
            notesSharedViewModel.sharedNote.value = NotesModel(0, "", "")
            findNavController().navigate(R.id.action_homeFragment_to_upsertNoteFragment)
        }

        notesAdapter = NotesAdapter({ position ->
            notesSharedViewModel.sharedNote.value = notesAdapter.currentList[position]
            findNavController().navigate(R.id.action_homeFragment_to_upsertNoteFragment)
        }, { position ->
            notesSharedViewModel.deleteNote(notesAdapter.currentList[position].id)
        })
        binding.rvNotes.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = notesAdapter
        }


        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                notesSharedViewModel.getAllNotes().collect { notes ->
                    notesAdapter.submitList(notes)
                }
            }
        }

    }

}