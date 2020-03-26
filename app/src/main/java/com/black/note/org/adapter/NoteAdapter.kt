package com.black.note.org.adapter

import android.content.Context
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.black.note.org.R
import com.black.note.org.data.Note
import java.text.SimpleDateFormat
import java.util.*

class NoteAdapter internal constructor(context: Context) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private var mNotes: List<Note>? = null

    inner class NoteViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val noteItemView: TextView = itemView.findViewById(R.id.tv_note)
        val categoryItemView: TextView = itemView.findViewById(R.id.tv_category)
        val dateItemView: TextView = itemView.findViewById(R.id.tv_date)
        val leftView: View = itemView.findViewById(R.id.v_left)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val itemView = mInflater.inflate(R.layout.note_item, parent, false)
        return NoteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        if (mNotes != null) {
            val current = mNotes!![position]
            val format = SimpleDateFormat("dd/MM/yyyy", Locale.US)
            val date = format.format(current.updateAt)
            holder.noteItemView.text = current.note
            holder.categoryItemView.text = current.category
            holder.dateItemView.text = date

            when {
                current.category == "Work" -> {
                    holder.categoryItemView.apply {
                        setTextColor(ContextCompat.getColor(context ,R.color.colorAccent))
                    }
                    holder.leftView.apply {
                        setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent))
                    }
                }

                current.category == "Study" -> {
                    holder.categoryItemView.apply {
                        setTextColor(ContextCompat.getColor(context ,R.color.studyColor))
                    }
                    holder.leftView.apply {
                        setBackgroundColor(ContextCompat.getColor(context, R.color.studyColor))
                    }
                }

                current.category == "Family Affairs" -> {
                    holder.categoryItemView.apply {
                        setTextColor(ContextCompat.getColor(context ,R.color.familyAffairsColor))
                    }
                    holder.leftView.apply {
                        setBackgroundColor(ContextCompat.getColor(context, R.color.familyAffairsColor))
                    }
                }

                current.category == "Personal" -> {
                    holder.categoryItemView.apply {
                        setTextColor(ContextCompat.getColor(context ,R.color.personalColor))
                    }
                    holder.leftView.apply {
                        setBackgroundColor(ContextCompat.getColor(context, R.color.personalColor))
                    }
                }

                current.category == "Other" -> {
                    holder.categoryItemView.apply {
                        setTextColor(ContextCompat.getColor(context ,R.color.otherColor))
                    }
                    holder.leftView.apply {
                        setBackgroundColor(ContextCompat.getColor(context, R.color.otherColor))
                    }
                }
            }
        }
    }

    internal fun setNotes(notes: List<Note>) {
        mNotes = notes
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        return if (mNotes != null)
            mNotes!!.size
        else
            0
    }
}