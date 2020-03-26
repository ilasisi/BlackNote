package com.black.note.org.ui.add_note

import androidx.lifecycle.ViewModel
import com.black.note.org.data.Note
import com.black.note.org.data.NoteRepository

class AddNoteViewModel : ViewModel() {

    private var mRepository: NoteRepository? = null

    fun insertNote(note: Note) {
        mRepository?.insertNote(note)
    }
}
