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
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpsertNoteFragment : BottomSheetBaseFragment<FragmentUpsertNoteBinding>() {


    private val notesSharedViewModel: NotesSharedViewModel by activityViewModels()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentUpsertNoteBinding
        get() = FragmentUpsertNoteBinding::inflate


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val selectedNote = notesSharedViewModel.sharedNote.value

        binding.apply {

            if (selectedNote.title != "") {
                etTitle.setText(selectedNote.title)
                etDesc.setText(selectedNote.description)
                tvTitle.text = requireContext().getString(R.string.edit_note)
            }
            ivDone.setOnClickListener {
                val title = etTitle.text.toString().trim()
                val desc = etDesc.text.toString().trim()

                if (title.isBlank()) {
                    showSnackBar("Kindly enter title")
                } else if (desc.isBlank()) {
                    showSnackBar("Kindly enter description")
                } else {
                    if (selectedNote.title != "")
                        notesSharedViewModel.upsertData(selectedNote.id, title = title, desc = desc)
                    else
                        notesSharedViewModel.upsertData(title = title, desc = desc)
                    findNavController().navigateUp()
                }
            }
        }
    }

}