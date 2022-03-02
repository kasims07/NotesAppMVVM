package com.kasim.notesappmvvm.db

import android.icu.util.VersionInfo
import androidx.room.Database
import androidx.room.RoomDatabase
import com.kasim.notesappmvvm.model.Note


@Database(
    entities = [Note::class],
    version = 1,
    exportSchema = false
)

abstract class NoteDatabase: RoomDatabase() {
}