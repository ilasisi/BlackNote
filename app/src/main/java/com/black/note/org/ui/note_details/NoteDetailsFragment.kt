package com.black.note.org.ui.note_details

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController

import com.black.note.org.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.util.*

class NoteDetailsFragment : Fragment() {

    private var tvNote: TextView? = null
    private var tvNoteCategory: TextView? = null
    private var tvNoteDate: TextView? = null
    private var fabEditNote: FloatingActionButton? = null

    private var noteId: Int? = -1
    private var note: String? = ""
    private var noteCategory: String? = ""
    private var noteDate: String? = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.note_details_fragment, container, false)

        setHasOptionsMenu(true)

        initValues(root)
        setUpCategoryColor()

        fabEditNote?.setOnClickListener { view ->
            navigateToEditFragment(view)
        }

        tvNote?.setOnClickListener { view ->
            navigateToEditFragment(view)
        }

        return root
    }

    private fun initValues(root: View) {
        val format = SimpleDateFormat("dd/MM/yyyy", Locale.US)

        noteId = arguments?.getInt(NOTE_ID)
        note = arguments?.getString(NOTE)
        noteCategory = arguments?.getString(NOTE_CATEGORY)
        noteDate = format.format(arguments?.getSerializable(NOTE_UPDATE_TIME))

        tvNote = root.findViewById(R.id.tv_note)
        tvNoteCategory = root.findViewById(R.id.tv_category)
        tvNoteDate = root.findViewById(R.id.tv_date)
        fabEditNote = root.findViewById(R.id.et_note)

        tvNote?.text = note
        tvNoteCategory?.text = noteCategory
        tvNoteDate?.text = noteDate
    }

    private fun navigateToEditFragment(root: View) {
        val bundle = Bundle()
        noteId?.let { bundle.putInt(NOTE_ID, it) }
        bundle.putString(NOTE_CATEGORY, noteCategory)
        bundle.putString(NOTE, note)

        root.findNavController().navigate(R.id.navigate_to_add_edit_note, bundle)
    }

    private fun setUpCategoryColor() {
        when (noteCategory) {
            "Work" -> {
                tvNoteCategory?.apply {
                    setTextColor(ContextCompat.getColor(requireContext(), R.color.colorAccent))
                }
            }
            "Study" -> {
                tvNoteCategory?.apply {
                    setTextColor(ContextCompat.getColor(requireContext(), R.color.studyColor))
                }
            }
            "Family Affairs" -> {
                tvNoteCategory?.apply {
                    setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.familyAffairsColor
                        )
                    )
                }
            }
            "Personal" -> {
                tvNoteCategory?.apply {
                    setTextColor(ContextCompat.getColor(requireContext(), R.color.personalColor))
                }
            }
            "Other" -> {
                tvNoteCategory?.apply {
                    setTextColor(ContextCompat.getColor(requireContext(), R.color.otherColor))
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
    }

    companion object {
        const val NOTE_ID = "note_id"
        const val NOTE = "note"
        const val NOTE_CATEGORY = "note_category"
        const val NOTE_UPDATE_TIME = "note_update_at"
    }

}
