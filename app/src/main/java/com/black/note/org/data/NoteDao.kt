package com.black.note.org.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.black.note.org.model.Note

@Dao
interface NoteDao {

    @Query("SELECT * from note_table ORDER BY updateAt DESC")
    fun getAlphabetizedNotes(): LiveData<List<Note>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertNote(note: Note)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun updateNote(note: Note)

    @Delete
    fun deleteNote(note: Note)

    @Query("DELETE FROM note_table")
    fun deleteAllNote()
}
