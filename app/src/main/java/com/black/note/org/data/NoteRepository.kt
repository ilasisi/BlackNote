package com.black.note.org.data

import androidx.lifecycle.LiveData
import android.app.Application
import com.black.note.org.model.Note
import kotlin.concurrent.thread


internal class NoteRepository(application: Application) {

    private var mNoteDao: NoteDao? = null
    private var allNotes: LiveData<List<Note>>? = null

    init {
        val db = NoteDatabase.getDatabase(application)
        mNoteDao = db?.noteDao()
        allNotes = mNoteDao?.getAlphabetizedNotes()
    }

    fun getAllNotes(): LiveData<List<Note>>? {
        return allNotes
    }

    fun insertNote(note: Note) {
        thread { mNoteDao?.insertNote(note) }
    }

    fun updateNote(note: Note) {
        thread { mNoteDao?.updateNote(note) }
    }

    fun deleteNote(note: Note) {
        thread { mNoteDao?.deleteNote(note) }
    }

    fun deleteAllNotes() {
        thread { mNoteDao?.deleteAllNote() }
    }
}