package tech.twentytwobits.fastnotesonline.interfaces

import tech.twentytwobits.fastnotesonline.models.Note

interface RecyclerNoteListener {
    fun onClick(note: Note, position: Int)
    fun onLongClick(note: Note, position: Int)
}