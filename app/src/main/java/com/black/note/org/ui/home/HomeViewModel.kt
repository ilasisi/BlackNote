package com.black.note.org.ui.home

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import android.app.Application
import com.black.note.org.data.Note
import com.black.note.org.data.NoteRepository


class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private var mRepository: NoteRepository? = null
    private var allNotes: LiveData<List<Note>>? = null

    init {
        mRepository = NoteRepository(application)
        allNotes = mRepository?.getAllNotes()
    }

    fun getAllNotes(): LiveData<List<Note>>? {
        return allNotes
    }
}