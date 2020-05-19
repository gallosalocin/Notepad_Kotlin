package com.gallosalocin.notepadkotlin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.gallosalocin.notepadkotlin.databinding.ActivityNoteListBinding
import com.gallosalocin.notepadkotlin.utils.deleteNote
import com.gallosalocin.notepadkotlin.utils.loadNotes
import com.gallosalocin.notepadkotlin.utils.persistNote
import com.google.android.material.snackbar.Snackbar

class NoteListActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityNoteListBinding

    lateinit var notes: MutableList<Note>
    lateinit var adapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setSupportActionBar(binding.toolbar)

        binding.fab.setOnClickListener(this)

        notes = loadNotes(this)

        adapter = NoteAdapter(notes, this)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK || data == null) {
            return
        }
        when (requestCode) {
            NoteDetailActivity.REQUEST_EDIT_NOTE -> processEditNoteResult(data)
        }
    }

    private fun processEditNoteResult(data: Intent) {
        val noteIndex = data.getIntExtra(NoteDetailActivity.EXTRA_NOTE_INDEX, -1)

        when (data.action) {
            NoteDetailActivity.ACTION_SAVE_NOTE -> {
                val note = data.getParcelableExtra<Note>(NoteDetailActivity.EXTRA_NOTE)
                saveNote(note, noteIndex)
            }
            NoteDetailActivity.ACTION_DELETE_NOTE -> {
                deleteNote(noteIndex)
            }
        }
    }

    override fun onClick(view: View) {
        if (view.tag != null) {
            showNoteDetail(view.tag as Int)
        } else {
            when (view.id) {
                R.id.fab -> createNewNote()
            }
        }
    }

    private fun createNewNote() {
        showNoteDetail(-1)
    }

    fun saveNote(note: Note, noteIndex: Int) {
        persistNote(this, note)
        if (noteIndex < 0) {
            notes.add(0, note)
        } else {
            notes[noteIndex] = note
        }
        adapter.notifyDataSetChanged()
    }

    private fun deleteNote(noteIndex: Int) {
        if (noteIndex < 0) {
            return
        }
        val note = notes.removeAt(noteIndex)
        deleteNote(this, note)
        adapter.notifyDataSetChanged()

        Snackbar.make(binding.coordinatorLayout, "${note.title} supprimé", Snackbar.LENGTH_SHORT).show()
    }

    fun showNoteDetail(noteIndex: Int) {
        val note = if (noteIndex < 0) Note() else notes[noteIndex]

        val intent = Intent(this, NoteDetailActivity::class.java)
        intent.putExtra(NoteDetailActivity.EXTRA_NOTE, note as Parcelable)
        intent.putExtra(NoteDetailActivity.EXTRA_NOTE_INDEX, noteIndex)
        startActivityForResult(intent, NoteDetailActivity.REQUEST_EDIT_NOTE)
    }

}
