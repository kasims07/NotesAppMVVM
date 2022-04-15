package com.kasim.notesappmvvm.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Display
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.transition.MaterialContainerTransform
import com.kasim.notesappmvvm.R
import com.kasim.notesappmvvm.activities.MainActivity
import com.kasim.notesappmvvm.databinding.BottomSheetLayoutBinding
import com.kasim.notesappmvvm.databinding.FragmentSaveOrUpdateBinding
import com.kasim.notesappmvvm.model.Note
import com.kasim.notesappmvvm.utils.hideKeyboard
import com.kasim.notesappmvvm.viewModel.NoteActivityViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import java.text.SimpleDateFormat
import java.util.*

class SaveOrUpdateFragment : Fragment(R.layout.fragment_save_or_update) {

    private lateinit var navController: NavController
    private lateinit var contentBinding: FragmentSaveOrUpdateBinding
    private var note: Note?=null
    private var color=-1
    private val noteActivityViewModel: NoteActivityViewModel by activityViewModels()
    private val currentDate = SimpleDateFormat.getInstance().format(Date())
    private val job = CoroutineScope(Dispatchers.Main)
    private val args: SaveOrUpdateFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val animation=MaterialContainerTransform().apply {
            drawingViewId=R.id.fragment
            scrimColor= Color.TRANSPARENT
            duration=300L

        }
        sharedElementEnterTransition=animation
        sharedElementReturnTransition=animation
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contentBinding = FragmentSaveOrUpdateBinding.bind(view)

        navController = Navigation.findNavController(view)
        val activity=activity as MainActivity

        contentBinding.backBtn.setOnClickListener{
            requireView().hideKeyboard()
            navController.popBackStack()
        }

        contentBinding.saveNote.setOnClickListener{
            saveNote()

            try {
                contentBinding.etNoteContent.setOnFocusChangeListener{_,hashFocus->
                    if (hashFocus){
                        contentBinding.bottomBar.visibility=View.VISIBLE
                        contentBinding.etNoteContent.setStylesBar(contentBinding.styleBar)
                    }else{
                        contentBinding.bottomBar.visibility=View.GONE
                    }

                }
            } catch (e: Throwable)
            {
                Log.d("TAG", e.stackTraceToString())
            }

            contentBinding.fbColorPiker.setOnClickListener {
                val bottomSheetDialog= BottomSheetDialog(
                    requireContext(),
                    R.style.BottomSheetDialogTheme
                )
                val bottomSheetView: View=layoutInflater.inflate(
                    R.layout.bottom_sheet_layout,
                    null,
                )
                val bottomSheetBinding=BottomSheetLayoutBinding.bind(bottomSheetView)
                bottomSheetBinding.apply {
                    colorpiker.apply {
                        setSelectedColor(color)
                        setOnColorSelectedListener {
                            value->
                            color=value
                            contentBinding.apply {
                                noteContentFragmentParent.setBackgroundColor(color)
                                toolBarContentNoteContent.setBackgroundColor(color)
                                bottomBar.setBackgroundColor(color)
                                activity.window.statusBarColor=color
                            }
                            bottomSheetBinding.bottomShitConteiner.setCardBackgroundColor(color)
                        }
                    }
                    bottomShitConteiner.setCardBackgroundColor(color)
                }
                bottomSheetView.post{
                    bottomSheetDialog.behavior.state=BottomSheetBehavior.STATE_EXPANDED
                }
            }
        }
    }

    private fun saveNote() {
        if(contentBinding.etNoteContent.text.toString().isEmpty() ||
                contentBinding.etTitile.text.toString().isEmpty())
        {
            Toast.makeText(activity,"Something is Empty",Toast.LENGTH_SHORT).show()
        }
        else
        {
            note=args.note
            when(note){
                null-> {
                    noteActivityViewModel.saveNote(
                        Note(
                            0,
                            contentBinding.etTitile.text.toString(),
                            contentBinding.etNoteContent.getMD(),
                            currentDate,
                            color
                        )
                    )
                    navController.navigate(SaveOrUpdateFragmentDirections.actionSaveOrUpdateFragmentToNoteFragment())
                }
                else ->
                {

                }

            }

        }
    }

}