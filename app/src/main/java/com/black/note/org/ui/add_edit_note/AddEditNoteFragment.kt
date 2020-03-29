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
import com.black.note.org.data.Note
import com.black.note.org.ui.note_details.NoteDetailsFragment
import com.black.note.org.viewmodel.NoteViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class AddEditNoteFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private lateinit var mNoteViewModel: NoteViewModel
    private var spinnerAdapter: ArrayAdapter<CharSequence>? = null
    private var mEditNoteView: EditText? = null
    private var mSpinnerCategory: Spinner? = null
    private var btnSave: Button? = null
    private var category = ""
    private var noteId: Int? = -1
    private var note: String? = ""
    private var noteCategory: String? = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.add_edit_note_fragment, container, false)

        setHasOptionsMenu(true)

        noteId = arguments?.getInt(NoteDetailsFragment.NOTE_ID)
        note = arguments?.getString(NoteDetailsFragment.NOTE)
        noteCategory = arguments?.getString(NoteDetailsFragment.NOTE_CATEGORY)

        initView(root)
        setupCategorySpinner()
        ifEditNote()

        btnSave?.setOnClickListener { view ->
            saveNote(view)
        }

        return root
    }

    private fun ifEditNote() {
        if (noteId == null) {
            (requireActivity() as MainActivity).toolbar.title = "Add Note"
        } else {
            btnSave?.text = getString(R.string.update_note)
            mEditNoteView?.setText(note)
            val spinnerPosition = spinnerAdapter?.getPosition(noteCategory)
            if (spinnerPosition != null) {
                mSpinnerCategory?.setSelection(spinnerPosition)
            }

            (requireActivity() as MainActivity).toolbar.title = "Edit Note"
        }
    }

    private fun initView(root: View) {
        mEditNoteView = root.findViewById(R.id.et_note)
        mSpinnerCategory = root.findViewById(R.id.sp_category)
        btnSave = root.findViewById(R.id.button_save)
    }

    private fun setupCategorySpinner() {
        mSpinnerCategory?.onItemSelectedListener = this
        spinnerAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.categories,
            android.R.layout.simple_spinner_item
        )
            spinnerAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            mSpinnerCategory?.adapter = spinnerAdapter
    }

    private fun saveNote(view: View) {
        mNoteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        if (TextUtils.isEmpty(mEditNoteView?.text)) {
            Toast.makeText(
                requireContext(),
                "Note not saved",
                Toast.LENGTH_LONG
            ).show()
        } else {
            val date = Date()
            val etNote = mEditNoteView?.text.toString()
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
