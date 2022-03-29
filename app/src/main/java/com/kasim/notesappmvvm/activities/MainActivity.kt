package com.kasim.notesappmvvm.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.kasim.notesappmvvm.R
import com.kasim.notesappmvvm.databinding.ActivityMainBinding
import com.kasim.notesappmvvm.db.NoteDatabase
import com.kasim.notesappmvvm.repository.NoteRepository
import com.kasim.notesappmvvm.viewModel.NoteActivityViewModel
import com.kasim.notesappmvvm.viewModel.NoteActivityViewModelFactory

class MainActivity : AppCompatActivity() {

    lateinit var noteActivityViewModel: NoteActivityViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)

        try {
            setContentView(binding.root)
            val noteRepository = NoteRepository(NoteDatabase(this))
            val noteActivityViewModelFactory = NoteActivityViewModelFactory(noteRepository)
            noteActivityViewModel = ViewModelProvider(this,
            noteActivityViewModelFactory)[NoteActivityViewModel::class.java]
        }catch (e: Exception){
            Log.d("Tag","Error")
        }
    }
}