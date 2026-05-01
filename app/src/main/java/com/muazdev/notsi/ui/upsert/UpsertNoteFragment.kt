package com.muazdev.notsi.ui.upsert

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.muazdev.notsi.R
import com.muazdev.notsi.base.BottomSheetBaseFragment
import com.muazdev.notsi.databinding.FragmentUpsertNoteBinding
import com.muazdev.notsi.ui.NotesSharedViewModel
import com.muazdev.notsi.util.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpsertNoteFragment : BottomSheetBaseFragment<FragmentUpsertNoteBinding>() {


    private val notesSharedViewModel: NotesSharedViewModel by activityViewModels()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentUpsertNoteBinding
        get() = FragmentUpsertNoteBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val selectedNote = notesSharedViewModel.sharedNote.value

        subscribeToObservers()

        binding.apply {

            if (selectedNote.title != "") {
                etTitle.setText(selectedNote.title)
                etDesc.setText(selectedNote.note)
                tvTitle.text = requireContext().getString(R.string.edit_note)
            }
            ivDone.setOnClickListener {
                val title = etTitle.text.toString().trim()
                val desc = etDesc.text.toString().trim()

                if (selectedNote.title != "")
                    notesSharedViewModel.upsertData(selectedNote.id, title = title, desc = desc)
                else
                    notesSharedViewModel.upsertData(title = title, desc = desc)
            }
        }
    }

    private fun subscribeToObservers() {
        notesSharedViewModel.upsertNoteStatus.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { result ->
                when (result.status) {
                    Status.SUCCESS -> {
                        findNavController().navigateUp()
                    }

                    Status.ERROR -> {
                        showSnackBar(result.message ?: "An unknown error occurred")
                    }

                    Status.LOADING -> {
                        /* No-op */
                    }
                }
            }
        }
    }

}