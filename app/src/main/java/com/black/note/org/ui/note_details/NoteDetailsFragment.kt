package com.black.note.org.ui.note_details

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController

import com.black.note.org.R
import com.black.note.org.databinding.NoteDetailsFragmentBinding
import com.black.note.org.utils.setDate
import java.util.*

class NoteDetailsFragment : Fragment() {

    private lateinit var binding: NoteDetailsFragmentBinding
    private var noteId: Int? = -1
    private var note: String? = ""
    private var noteCategory: String? = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = NoteDetailsFragmentBinding.inflate(inflater)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initValues()
        setUpCategoryColor()
        binding.editNote.setOnClickListener {
            navigateToEditFragment(it)
        }

        binding.tvNote.setOnClickListener {
            navigateToEditFragment(it)
        }
    }

    private fun initValues() {
        noteId = arguments?.getInt(NOTE_ID)
        note = arguments?.getString(NOTE)
        noteCategory = arguments?.getString(NOTE_CATEGORY)

        binding.tvNote.text = note
        binding.tvCategory.text = noteCategory
        binding.tvDate.setDate(arguments?.getSerializable(NOTE_UPDATE_TIME) as Date?)
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
                binding.tvCategory.apply {
                    setTextColor(ContextCompat.getColor(requireContext(), R.color.colorAccent))
                }
            }
            "Study" -> {
                binding.tvCategory.apply {
                    setTextColor(ContextCompat.getColor(requireContext(), R.color.studyColor))
                }
            }
            "Family Affairs" -> {
                binding.tvCategory.apply {
                    setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.familyAffairsColor
                        )
                    )
                }
            }
            "Personal" -> {
                binding.tvCategory.apply {
                    setTextColor(ContextCompat.getColor(requireContext(), R.color.personalColor))
                }
            }
            "Other" -> {
                binding.tvCategory.apply {
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
