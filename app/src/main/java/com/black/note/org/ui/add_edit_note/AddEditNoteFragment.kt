package com.black.note.org.ui.add_edit_note

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.black.note.org.R
import android.text.TextUtils
import android.view.*
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.black.note.org.MainActivity
import com.black.note.org.model.Note
import com.black.note.org.databinding.AddEditNoteFragmentBinding
import com.black.note.org.ui.note_details.NoteDetailsFragment
import com.black.note.org.viewmodel.NoteViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class AddEditNoteFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private lateinit var mNoteViewModel: NoteViewModel
    private lateinit var binding: AddEditNoteFragmentBinding
    private var spinnerAdapter: ArrayAdapter<CharSequence>? = null
    private var category = ""
    private var noteId: Int? = -1
    private var note: String? = ""
    private var noteCategory: String? = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AddEditNoteFragmentBinding.inflate(inflater)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        noteId = arguments?.getInt(NoteDetailsFragment.NOTE_ID)
        note = arguments?.getString(NoteDetailsFragment.NOTE)
        noteCategory = arguments?.getString(NoteDetailsFragment.NOTE_CATEGORY)

        setupCategorySpinner()
        ifEditNote()

        binding.buttonSave.setOnClickListener {
            saveNote(it)
        }
    }

    private fun ifEditNote() {
        if (noteId == null) {
            (requireActivity() as MainActivity).toolbar.title = "Add Note"
        } else {
            binding.buttonSave.text = getString(R.string.update_note)
            binding.etNote.setText(note)
            val spinnerPosition = spinnerAdapter?.getPosition(noteCategory)
            if (spinnerPosition != null) {
                binding.spCategory.setSelection(spinnerPosition)
            }

            (requireActivity() as MainActivity).toolbar.title = "Edit Note"
        }
    }

    private fun setupCategorySpinner() {
        binding.spCategory.onItemSelectedListener = this
        spinnerAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.categories,
            android.R.layout.simple_spinner_item
        )
            spinnerAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spCategory.adapter = spinnerAdapter
    }

    private fun saveNote(view: View) {
        mNoteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        if (TextUtils.isEmpty(binding.etNote.text)) {
            Toast.makeText(
                requireContext(),
                "Note not saved",
                Toast.LENGTH_LONG
            ).show()
        } else {
            val date = Date()
            val etNote = binding.etNote.text.toString()
            if (noteId == null) {
                val newNote = Note(etNote, category, date)
                mNoteViewModel.insertNote(newNote)
                view.findNavController().navigate(R.id.navigate_to_home)
            } else if (noteId != null){
                if (noteId == -1) {
                    Toast.makeText(requireContext(), " Error! Could not update note!", Toast.LENGTH_SHORT).show()
                } else {
                    val updateNote = Note(etNote, category, date)
                    updateNote.id = noteId as Int
                    mNoteViewModel.updateNote(updateNote)
                    val bundle = Bundle()
                    if (note != null) {
                        bundle.putInt(NoteDetailsFragment.NOTE_ID, noteId!!)
                        bundle.putString(NoteDetailsFragment.NOTE, etNote)
                        bundle.putString(NoteDetailsFragment.NOTE_CATEGORY, category)
                        bundle.putSerializable(NoteDetailsFragment.NOTE_UPDATE_TIME, date)
                    }
                    view.findNavController().navigate(R.id.navigate_to_note_details, bundle)
                }
            } else {
                Toast.makeText(requireContext(), "Note not saved!", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val categoryItem = parent?.getItemAtPosition(position).toString()
        category = categoryItem
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {}

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
    }

}
