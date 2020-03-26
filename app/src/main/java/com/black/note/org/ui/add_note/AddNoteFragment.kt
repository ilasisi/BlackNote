package com.black.note.org.ui.add_note

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.black.note.org.R
import android.text.TextUtils
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.black.note.org.data.Note
import java.util.*


class AddNoteFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private lateinit var mAddNoteViewModel: AddNoteViewModel
    private var mEditNoteView: EditText? = null
    private var mSpinnerCategory: Spinner? = null
    private var category = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.note_fragment, container, false)

        mAddNoteViewModel = ViewModelProvider(this).get(AddNoteViewModel::class.java)

        mEditNoteView = root.findViewById(R.id.et_note)

        mSpinnerCategory = root.findViewById(R.id.sp_category)
        mSpinnerCategory?.onItemSelectedListener = this
        ArrayAdapter.createFromResource(requireContext(),
            R.array.categories,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            mSpinnerCategory?.adapter = adapter
        }
        val button = root.findViewById<Button>(R.id.button_save)
        button.setOnClickListener { view ->
            if (TextUtils.isEmpty(mEditNoteView?.text)) {
                Toast.makeText(
                    requireContext(),
                    "Note not saved",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                val date = Date()
                val etNote = mEditNoteView?.text.toString()
                val note = Note(0, etNote, category, date)
                mAddNoteViewModel.insertNote(note)
                view.findNavController().navigate(R.id.homeFragment)
            }
        }

        return root
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {}

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val categoryItem = parent?.getItemAtPosition(position).toString()
        category = categoryItem
    }

}
