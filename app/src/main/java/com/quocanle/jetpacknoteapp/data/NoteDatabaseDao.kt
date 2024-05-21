package com.quocanle.jetpacknoteapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.quocanle.jetpacknoteapp.model.Note
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface NoteDatabaseDao {
    @Query("SELECT * FROM note_table ORDER BY entryDate DESC")
    fun getNotesOrderDESC(): Flow<List<Note>>

    @Query("SELECT * FROM note_table")
    fun getNotes(): Flow<List<Note>>

    @Query("SELECT * FROM note_table WHERE id= :noteId")
    fun getNoteById(noteId: UUID): Flow<Note>

    @Query("SELECT * FROM note_table WHERE id= :noteId")
    fun getNoteById(noteId: String): Note

    @Query("DELETE FROM note_table")
    suspend fun deleteAllNotes()

    @Query("DELETE FROM note_table WHERE id= :noteId")
    suspend fun deleteNoteById(noteId: UUID)

    @Delete
    suspend fun delete(note: Note)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(notes: List<Note>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(note: Note)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateAll(notes: List<Note>)
}