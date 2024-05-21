package com.quocanle.jetpacknoteapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.quocanle.jetpacknoteapp.model.Note
import com.quocanle.jetpacknoteapp.utils.Converters

@Database(entities = [Note::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao():NoteDatabaseDao
}