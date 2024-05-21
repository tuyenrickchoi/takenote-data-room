package com.quocanle.jetpacknoteapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quocanle.jetpacknoteapp.data.NoteRepository
import com.quocanle.jetpacknoteapp.model.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(private val repository: NoteRepository) : ViewModel(){
    private val _noteList = MutableStateFlow<List<Note>>(emptyList())
    val noteList = _noteList.asStateFlow()

    init {
//        fetchNotes()
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllNotes().collect {
                listOfNotes ->
                if (listOfNotes.isNullOrEmpty()) {
                    Log.d("DEBUG", "Note list is Null or Empty")
                } else {
                    _noteList.value = listOfNotes
                    Log.d("DEBUG", listOfNotes.toString())
                }
            }
        }
    }

    private fun fetchNotes() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllNotes().collect { listOfNotes ->
                _noteList.value = listOfNotes
            }
        }
    }

    fun addNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addNote(note)
            repository.getAllNotes().collect { listOfNotes ->
                _noteList.value = listOfNotes
            }
        }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateNote(note)
            repository.getAllNotes().collect { listOfNotes ->
                _noteList.value = listOfNotes
            }
        }
    }

    fun removeNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteNote(note)
            repository.getAllNotes().collect { listOfNotes ->
                _noteList.value = listOfNotes
            }
        }
    }
}