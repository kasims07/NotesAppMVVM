package com.kasim.notesappmvvm.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.google.android.material.transition.MaterialElevationScale
import com.kasim.notesappmvvm.R
import com.kasim.notesappmvvm.activities.MainActivity
import com.kasim.notesappmvvm.databinding.FragmentNoteBinding
import com.kasim.notesappmvvm.utils.hideKeyboard
import com.kasim.notesappmvvm.viewModel.NoteActivityViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NoteFragment : Fragment(R.layout.fragment_note) {


    private lateinit var noteBinding: FragmentNoteBinding
    private val noteActivityViewModel: NoteActivityViewModel by activityViewModels()

    override fun onCreate(savedInstanseState: Bundle?){
        super.onCreate(savedInstanseState)
        exitTransition=MaterialElevationScale(false).apply {
            duration=350
        }
        enterTransition=MaterialElevationScale(true).apply {
            duration=350
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        noteBinding= FragmentNoteBinding.bind(view)
        val activity=activity as MainActivity
        val navController=Navigation.findNavController(view)
        requireView().hideKeyboard()
        CoroutineScope(Dispatchers.Main).launch {
            delay(10)
            activity.window.statusBarColor= Color.WHITE
            activity.window.clearFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            activity.window.statusBarColor=Color.parseColor("#9E9D9D")
        }

        noteBinding.addNoteFab.setOnClickListener {
            noteBinding.appbarLayout.visibility=View.INVISIBLE
            navController.navigate(NoteFragmentDirections.actionNoteFragmentToSaveOrUpdateFragment())
        }

        noteBinding.innerFab.setOnClickListener {
            noteBinding.appbarLayout.visibility=View.INVISIBLE
            navController.navigate(NoteFragmentDirections.actionNoteFragmentToSaveOrUpdateFragment())
        }

    }
}