package com.black.note.org.adapter

import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.black.note.org.R
import com.black.note.org.model.Note
import com.black.note.org.databinding.NoteItemBinding
import com.daimajia.swipe.SwipeLayout
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter
import com.daimajia.swipe.implments.SwipeItemRecyclerMangerImpl

internal class NoteAdapter : RecyclerSwipeAdapter<NoteAdapter.NoteViewHolder>() {

    private var mNotes: List<Note>? = null
    private var listener: OnItemClickListener? = null

    internal class NoteViewHolder
    private constructor(private val binding: NoteItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            @LayoutRes
            val layout = R.layout.note_item

            fun from(parent: ViewGroup): NoteViewHolder {
                val withDataBinding: NoteItemBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    layout,
                    parent,
                    false
                )
                return NoteViewHolder(withDataBinding)
            }
        }

        fun bind(note: Note?, listener: OnItemClickListener) {
            val mItemManger = SwipeItemRecyclerMangerImpl(NoteAdapter())
            binding.also {
                it.note = note
                it.executePendingBindings()
            }

            binding.swipe.showMode = SwipeLayout.ShowMode.PullOut
            binding.swipe.isClickToClose = true
            binding.swipe.addDrag(
                SwipeLayout.DragEdge.Right,
                binding.swipe.findViewById(R.id.bottom1)
            )
            binding.swipe.addDrag(
                SwipeLayout.DragEdge.Left,
                binding.swipe.findViewById(R.id.bottom2)
            )
            binding.swipe.addSwipeListener(object : SwipeLayout.SwipeListener {
                override fun onOpen(layout: SwipeLayout?) {}

                override fun onUpdate(layout: SwipeLayout?, leftOffset: Int, topOffset: Int) {}

                override fun onStartOpen(layout: SwipeLayout?) {
                    mItemManger.closeAllExcept(layout)
                }

                override fun onStartClose(layout: SwipeLayout?) {}

                override fun onHandRelease(layout: SwipeLayout?, xvel: Float, yvel: Float) {}

                override fun onClose(layout: SwipeLayout?) {}

            })

            binding.llSurfaceView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(note)
                }
            }

            binding.deleteNote.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onDeleteClick(position)
                }
            }

            binding.editNote.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onEditClick(note)
                }
            }

            when {
                binding.tvCategory.text == "Work" -> {
                    binding.tvCategory.apply {
                        setTextColor(ContextCompat.getColor(context, R.color.colorAccent))
                    }
                    binding.vLeft.apply {
                        setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent))
                    }
                }

                binding.tvCategory.text == "Study" -> {
                    binding.tvCategory.apply {
                        setTextColor(ContextCompat.getColor(context, R.color.studyColor))
                    }
                    binding.vLeft.apply {
                        setBackgroundColor(ContextCompat.getColor(context, R.color.studyColor))
                    }
                }

                binding.tvCategory.text == "Family Affairs" -> {
                    binding.tvCategory.apply {
                        setTextColor(ContextCompat.getColor(context, R.color.familyAffairsColor))
                    }
                    binding.vLeft.apply {
                        setBackgroundColor(
                            ContextCompat.getColor(
                                context,
                                R.color.familyAffairsColor
                            )
                        )
                    }
                }

                binding.tvCategory.text == "Personal" -> {
                    binding.tvCategory.apply {
                        setTextColor(ContextCompat.getColor(context, R.color.personalColor))
                    }
                    binding.vLeft.apply {
                        setBackgroundColor(ContextCompat.getColor(context, R.color.personalColor))
                    }
                }

                binding.tvCategory.text == "Other" -> {
                    binding.tvCategory.apply {
                        setTextColor(ContextCompat.getColor(context, R.color.otherColor))
                    }
                    binding.vLeft.apply {
                        setBackgroundColor(ContextCompat.getColor(context, R.color.otherColor))
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        listener?.let { holder.bind(mNotes?.get(position), it) }
        mItemManger.bindView(holder.itemView, position)
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