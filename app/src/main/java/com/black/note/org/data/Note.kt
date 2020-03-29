package com.black.note.org.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "note_table")
data class Note(
    var note: String = "",
    var category: String = "",
    var updateAt: Date = Date()
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}