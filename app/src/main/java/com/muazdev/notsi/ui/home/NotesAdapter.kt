package com.muazdev.notsi.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.muazdev.notsi.NotesEntity
import com.muazdev.notsi.R
import com.muazdev.notsi.databinding.ItemNotesBinding

class NotesAdapter(
    private val onEditClickListener: (position: Int) -> Unit,
    private val onDeleteClickListener: (position: Int) -> Unit
) : ListAdapter<NotesEntity, NotesAdapter.NotesHolder>(NotesDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = NotesHolder(
        ItemNotesBinding.bind(
            LayoutInflater.from(parent.context).inflate(R.layout.item_notes, parent, false)
        )
    )


    override fun onBindViewHolder(holder: NotesHolder, position: Int) {
        holder.bind(position)
    }

    inner class NotesHolder(
        private val binding: ItemNotesBinding
    ) : ViewHolder(binding.root) {

        fun bind(position: Int) {
            binding.apply {
                tvTitle.text = currentList[position].title
                tvDesc.text = currentList[position].description

                ivDelete.setOnClickListener { onDeleteClickListener(position) }
                itemView.setOnClickListener { onEditClickListener(position) }
            }
        }
    }

    class NotesDiffUtil : DiffUtil.ItemCallback<NotesEntity>() {
        override fun areItemsTheSame(oldItem: NotesEntity, newItem: NotesEntity) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: NotesEntity, newItem: NotesEntity) =
            oldItem == newItem
    }


}