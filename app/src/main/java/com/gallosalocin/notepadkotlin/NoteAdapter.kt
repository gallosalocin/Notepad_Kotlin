package com.gallosalocin.notepadkotlin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gallosalocin.notepadkotlin.databinding.ItemNoteBinding

class NoteAdapter(val notes: List<Note>, val itemClickListener: View.OnClickListener) : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note: Note = notes[position]
        holder.cardView.setOnClickListener(itemClickListener)
        holder.cardView.tag = position
        holder.title.text = note.title
        holder.excerpt.text = note.text

    }

    override fun getItemCount(): Int {
        return notes.size
    }

    class ViewHolder(val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root) {
            val cardView = binding.cardView
            val title = binding.title
            val excerpt = binding.excerpt
    }
}