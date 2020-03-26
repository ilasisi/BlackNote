package com.black.note.org.data

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "note_table")
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @NonNull @ColumnInfo(name = "note") var note: String = "",
    @NonNull @ColumnInfo(name = "category") var category: String = "",
    @NonNull @ColumnInfo(name = "updateAt") var updateAt: Date = Date()
)