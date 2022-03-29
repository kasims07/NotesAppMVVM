package com.kasim.notesappmvvm.repository

import androidx.room.Query
import com.kasim.notesappmvvm.db.NoteDatabase
import com.kasim.notesappmvvm.model.Note

class NoteRepository(private val db: NoteDatabase) {

    fun getNote()= db.getNoteDao().getAllNote();

    fun searchNote(query: String)= db.getNoteDao().searchNote(query);

    suspend fun addNote(note: Note)= db.getNoteDao().addNote(note);

    suspend fun updateNote(note: Note)= db.getNoteDao().updateNote(note);

    suspend fun deleteNote(note: Note)= db.getNoteDao().deletNote(note);




}