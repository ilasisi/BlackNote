package com.black.note.org.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.black.note.org.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.black.note.org.adapter.NoteAdapter
import androidx.lifecycle.ViewModelProvider
import com.black.note.org.data.Note
import android.content.Context
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.black.note.org.ui.note_details.NoteDetailsFragment
import com.black.note.org.viewmodel.NoteViewModel

class HomeFragment : Fragment() {
    private lateinit var mNoteViewModel: NoteViewModel
    private lateinit var tvNoNote: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NoteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.home_fragment, container, false)

        setHasOptionsMenu(true)

        hideKeyboard()
        initView(root)
        setupAdapter()
        loadViewModel()
        onItemClick(root)

        val fabAddNote = root.findViewById<FloatingActionButton>(R.id.add_note)
        fabAddNote.setOnClickListener {view ->
            view.findNavController().navigate(R.id.navigate_to_add_edit_note)
        }

        return root
    }

    private fun initView(root: View) {
        tvNoNote = root.findViewById(R.id.tv_no_note)
        recyclerView = root.findViewById(R.id.rv_note)
    }

    private fun setupAdapter() {
        adapter = NoteAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun loadViewModel() {
        mNoteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        mNoteViewModel.getAllNotes()?.observe(this,
            Observer<List<Note>> {
                if (it.isNullOrEmpty()) {
                    tvNoNote.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                } else {
                    adapter.setNotes(it)
                }
            })
    }

    private fun onItemClick(root: View) {
        adapter.setOnItemClickListener(object : NoteAdapter.OnItemClickListener {

            override fun onDeleteClick(position: Int) {
                adapter.getNoteAt(position)?.let { mNoteViewModel.deleteNote(it) }
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
