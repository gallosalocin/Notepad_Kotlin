package com.gallosalocin.notepadkotlin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.gallosalocin.notepadkotlin.databinding.ActivityNoteDetailBinding

class NoteDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNoteDetailBinding

    companion object {
        val REQUEST_EDIT_NOTE = 1
        val EXTRA_NOTE = "note"
        val EXTRA_NOTE_INDEX = "noteIndex"

        val ACTION_SAVE_NOTE = "com.gallosalocin.notepadkotlin.actions.ACTION_SAVE_NOTE"
        val ACTION_DELETE_NOTE = "com.gallosalocin.notepadkotlin.actions.ACTION_DELETE_NOTE"
    }

    lateinit var note: Note
    var noteIndex: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        note = intent.getParcelableExtra<Note>(EXTRA_NOTE)
        noteIndex = intent.getIntExtra(EXTRA_NOTE_INDEX, -1)

        binding.title.setText(note.title)
        binding.text.setText(note.text)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_note_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_save -> {
                saveNote()
                return true
            }
            R.id.action_delete -> {
                showConfirmDeleteNoteDialog()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun showConfirmDeleteNoteDialog() {
        val confirmFragment = ConfirmDeleteNoteDialogFragment(note.title)
        confirmFragment.listener = object : ConfirmDeleteNoteDialogFragment.ConfirmDeleteDialogListener {
            override fun onDialogPositiveClick() {
                deletenote()
            }
            override fun onDialogNegativeClick() {}
        }
        confirmFragment.show(supportFragmentManager, "confirmDeleteDialog")
    }

    fun saveNote() {
        note.title = binding.title.text.toString()
        note.text = binding.text.text.toString()

        intent = Intent(ACTION_SAVE_NOTE)
        intent.putExtra(EXTRA_NOTE, note as Parcelable)
        intent.putExtra(EXTRA_NOTE_INDEX, noteIndex)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    fun deletenote(){
        intent = Intent(ACTION_DELETE_NOTE)
        intent.putExtra(EXTRA_NOTE_INDEX, noteIndex)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }


}
