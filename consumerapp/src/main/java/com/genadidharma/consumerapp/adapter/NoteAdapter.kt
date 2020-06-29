package com.genadidharma.consumerapp.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.genadidharma.consumerapp.R
import com.genadidharma.consumerapp.entity.Note
import com.genadidharma.consumerapp.ui.NoteAddUpdateActivity
import kotlinx.android.synthetic.main.item_note.view.*

class NoteAdapter(private val activity: Activity) : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {
    var listNotes = ArrayList<Note>()
        set(listNotes) {
            if (listNotes.size > 0) {
                this.listNotes.clear()
            }
            this.listNotes.addAll(listNotes)
            notifyDataSetChanged()
        }

    fun addItem(note: Note?) {
        note?.let { this.listNotes.add(it) }
        notifyItemInserted(this.listNotes.size - 1)
    }

    fun updateItem(position: Int, note: Note?) {
        if (note != null) {
            this.listNotes[position] = note
        }
        notifyItemChanged(position, note)
    }

    fun removeItem(position: Int) {
        this.listNotes.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.listNotes.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int =
        this.listNotes.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listNotes[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(note: Note) {
            with(itemView) {
                tv_item_title.text = note.title
                tv_item_date.text = note.date
                tv_item_description.text = note.description
                cv_item_note.setOnClickListener(
                    CustomOnItemClickListener(
                        adapterPosition,
                        object :
                            CustomOnItemClickListener.OnItemClickCallback {
                            override fun onItemClicked(view: View, position: Int) {
                                val intent = Intent(activity, NoteAddUpdateActivity::class.java)
                                intent.putExtra(NoteAddUpdateActivity.EXTRA_POSITION, position)
                                intent.putExtra(NoteAddUpdateActivity.EXTRA_NOTE, note)
                                activity.startActivityForResult(
                                    intent,
                                    NoteAddUpdateActivity.REQUEST_UPDATE
                                )
                            }
                        })
                )
            }
        }
    }

}