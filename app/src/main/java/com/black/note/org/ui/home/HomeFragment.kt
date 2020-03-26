package com.black.note.org.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import android.view.inputmethod.InputMethodManager


class HomeFragment : Fragment() {
    private lateinit var mNoteViewModel: HomeViewModel
    private lateinit var tvNoNote: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NoteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.home_fragment, container, false)

        hideKeyboard()
        initView(root)
        setupAdapter()
        loadViewModel()

        val fabAddNote = root.findViewById<FloatingActionButton>(R.id.add_note)
        fabAddNote.setOnClickListener {view ->
            view.findNavController().navigate(R.id.addNoteFragment)
        }

        return root
    }

    private fun initView(root: View) {
        tvNoNote = root.findViewById(R.id.tv_no_note)
        recyclerView = root.findViewById(R.id.rv_note)
    }

    private fun setupAdapter() {
        adapter = NoteAdapter(requireContext())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun loadViewModel() {
        mNoteViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        mNoteViewModel.getAllNotes()?.observe(this,
            Observer<List<Note>> { notes ->
                if (notes.isNullOrEmpty()) {
                    tvNoNote.visibility = View.VISIBLE
                } else {
                    adapter.setNotes(notes)
                }
            })
    }

    private fun hideKeyboard() {
        val inputManager= requireActivity()
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val v = requireActivity().currentFocus ?: return

        inputManager.hideSoftInputFromWindow(v.windowToken, 0)
    }
}
