package tech.twentytwobits.fastnotesonline.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.template_note_items.view.*
import tech.twentytwobits.fastnotesonline.R
import tech.twentytwobits.fastnotesonline.interfaces.RecyclerNoteListener
import tech.twentytwobits.fastnotesonline.models.Note

class NoteAdapter(private val notes: List<Note>, private val listener: RecyclerNoteListener)
    : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.template_note_items, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount() = notes.size

    override fun onBindViewHolder(holder: NoteAdapter.ViewHolder, position: Int) = holder.bind(notes[position], listener)

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(note: Note, listener: RecyclerNoteListener) = with(itemView) {
            tvTitulo.text = note.title
            tvPrimeraLetra.text = note.title.first().toUpperCase().toString()
            ivPrioridad.setImageResource(getPrioridad(note.priority))

            // Implementar los eventos
            setOnClickListener { listener.onClick(note, adapterPosition) }
        }

        private fun getPrioridad(prioridad: Int)
                = if (prioridad == 1) R.drawable.low_priority else if (prioridad == 2) R.drawable.medium_priority else R.drawable.high_priority
    }
}