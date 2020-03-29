package com.black.note.org.viewmodel

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import android.app.Application
import com.black.note.org.data.Note
import com.black.note.org.data.NoteRepository


class NoteViewModel(application: Application) : AndroidViewModel(application) {
    private var mRepository: NoteRepository? = null
    private var allNotes: LiveData<List<Note>>? = null

    init {
        mRepository = NoteRepository(application)
        allNotes = mRepository?.getAllNotes()
    }

    fun getAllNotes(): LiveData<List<Note>>? {
        return allNotes
    }

    fun insertNote(note: Note) {
        mRepository?.insertNote(note)
    }

    fun updateNote(note: Note) {
        mRepository?.updateNote(note)
    }

    fun deleteNote(note: Note) {
        mRepository?.deleteNote(note)
    }

    fun deleteAllNotes() {
        mRepository?.deleteAllNotes()
    }
}