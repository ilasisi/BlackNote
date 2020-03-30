package com.black.note.org.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.black.note.org.R
import androidx.recyclerview.widget.LinearLayoutManager
import com.black.note.org.adapter.NoteAdapter
import androidx.lifecycle.ViewModelProvider
import com.black.note.org.model.Note
import android.content.Context
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.black.note.org.databinding.HomeFragmentBinding
import com.black.note.org.ui.note_details.NoteDetailsFragment
import com.black.note.org.viewmodel.NoteViewModel

class HomeFragment : Fragment() {
    private lateinit var mNoteViewModel: NoteViewModel
    private lateinit var noteAdapter: NoteAdapter
    private lateinit var binding: HomeFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = HomeFragmentBinding.inflate(inflater)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hideKeyboard()
        setupAdapter()
        loadViewModel()
        onItemClick(binding.root)

        binding.addNote.setOnClickListener {
            it.findNavController().navigate(R.id.navigate_to_add_edit_note)
        }
    }

    private fun setupAdapter() {
        noteAdapter = NoteAdapter()
        binding.rvNote.apply {
            adapter = noteAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun loadViewModel() {
        mNoteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        mNoteViewModel.getAllNotes()?.observe(this,
            Observer<List<Note>> {
                if (it.isNullOrEmpty()) {
                    binding.tvNoNote.visibility = View.VISIBLE
                    binding.rvNote.visibility = View.GONE
                } else {
                    noteAdapter.setNotes(it)
                }
            })
    }

    private fun onItemClick(root: View) {
        noteAdapter.setOnItemClickListener(object : NoteAdapter.OnItemClickListener {

            override fun onDeleteClick(position: Int) {
                noteAdapter.getNoteAt(position)?.let { mNoteViewModel.deleteNote(it) }
            }

            override fun onEditClick(note: Note?) {
                val bundle = Bundle()
                if (note != null) {
                    bundle.putInt(NoteDetailsFragment.NOTE_ID, note.id)
                    bundle.putString(NoteDetailsFragment.NOTE, note.note)
                    bundle.putString(NoteDetailsFragment.NOTE_CATEGORY, note.category)
                    bundle.putSerializable(NoteDetailsFragment.NOTE_UPDATE_TIME, note.updateAt)
                }
                root.findNavController().navigate(R.id.navigate_to_add_edit_note, bundle)
            }

            override fun onItemClick(note: Note?) {
                val bundle = Bundle()
                if (note != null) {
                    bundle.putInt(NoteDetailsFragment.NOTE_ID, note.id)
                    bundle.putString(NoteDetailsFragment.NOTE, note.note)
                    bundle.putString(NoteDetailsFragment.NOTE_CATEGORY, note.category)
                    bundle.putSerializable(NoteDetailsFragment.NOTE_UPDATE_TIME, note.updateAt)
                }
                root.findNavController().navigate(R.id.noteDetailsFragment, bundle)
            }

        })
    }

    private fun hideKeyboard() {
        val inputManager= requireActivity()
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val v = requireActivity().currentFocus ?: return

        inputManager.hideSoftInputFromWindow(v.windowToken, 0)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_delete_all -> {
                mNoteViewModel.deleteAllNotes()
                Toast.makeText(requireContext(), "All notes deleted", Toast.LENGTH_LONG).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
