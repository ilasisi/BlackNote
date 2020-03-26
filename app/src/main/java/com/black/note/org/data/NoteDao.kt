package com.black.note.org.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NoteDao {

    @Query("SELECT * from note_table ORDER BY updateAt DESC")
    fun getAlphabetizedNotes(): LiveData<List<Note>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertNote(note: Note)

    @Query("DELETE FROM note_table")
    fun deleteAllNote()
}
