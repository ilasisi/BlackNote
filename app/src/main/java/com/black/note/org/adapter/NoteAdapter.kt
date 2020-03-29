package com.black.note.org.adapter

import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.black.note.org.R
import com.black.note.org.data.Note
import com.daimajia.swipe.SwipeLayout
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter
import java.text.SimpleDateFormat
import java.util.*

class NoteAdapter : RecyclerSwipeAdapter<NoteAdapter.NoteViewHolder>() {

    private var mNotes: List<Note>? = null
    private var listener: OnItemClickListener? = null

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val swipeLayout: SwipeLayout =  itemView.findViewById(R.id.swipe)
        val noteItemView: TextView = itemView.findViewById(R.id.tv_note)
        val categoryItemView: TextView = itemView.findViewById(R.id.tv_category)
        val dateItemView: TextView = itemView.findViewById(R.id.tv_date)
        val leftView: View = itemView.findViewById(R.id.v_left)
        private val deleteBtn: ImageButton = itemView.findViewById(R.id.delete_note)
        private val editBtn: ImageButton = itemView.findViewById(R.id.edit_note)
        private val llSurfaceView: LinearLayout = itemView.findViewById(R.id.ll_surfaceView)

        init {
            llSurfaceView.setOnClickListener {
                val position = adapterPosition
                if (position!= RecyclerView.NO_POSITION) {
                    listener?.onItemClick(mNotes?.get(position))
                }
            }

            deleteBtn.setOnClickListener {
                val position = adapterPosition
                if (position!= RecyclerView.NO_POSITION) {
                    listener?.onDeleteClick(position)
                }
            }

            editBtn.setOnClickListener {
                val position = adapterPosition
                if (position!= RecyclerView.NO_POSITION) {
                    listener?.onEditClick(mNotes?.get(position))
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.note_item, parent, false)
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

            holder.swipeLayout.showMode = SwipeLayout.ShowMode.LayDown
            holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, holder.swipeLayout.findViewById(R.id.bottom1))
            holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left, holder.swipeLayout.findViewById(R.id.bottom2))

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

    fun getNoteAt(position: Int): Note? {
        return mNotes?.get(position)
    }

    interface OnItemClickListener {
        fun onItemClick(note: Note?)
        fun onDeleteClick(position: Int)
        fun onEditClick(note: Note?)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
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

    override fun getSwipeLayoutResourceId(position: Int): Int {
        return R.id.swipe
    }
}