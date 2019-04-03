package tech.twentytwobits.fastnotesonline

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import tech.twentytwobits.fastnotesonline.models.Note

class NoteAddActivity : AppCompatActivity(), RadioGroup.OnCheckedChangeListener {

    // Variables para Firebase Firestore
    private val store: FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var noteDBRef: CollectionReference

    private var prioridad = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_add)

        // Establecer la colección a utilizar
        noteDBRef = store.collection("notes")

        val btnAddNote = findViewById<Button>(R.id.btnAgregar)

        val tvTitle = findViewById<TextView>(R.id.etTitulo)
        val tvDescripcion = findViewById<TextView>(R.id.etDescripcion)

        btnAddNote.setOnClickListener {
            val titulo = tvTitle.text
            if (titulo.isNotEmpty()) {
                // Almacenar la información en Firebase
                val note = Note(titulo.toString(), tvDescripcion.text.toString(), prioridad)
                saveNote(note)
            } else {
                Toast.makeText(this, "Debes colocar al menos el título de la nota", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        if (checkedId == R.id.rbMedia) {
            prioridad = 2
        } else if (checkedId == R.id.rbAlta) {
            prioridad = 3
        }
    }

    private fun saveNote(note: Note) {
        val newNote = HashMap<String, Any>()
        newNote["title"] = note.title
        newNote["description"] = note.description
        newNote["priority"] = note.priority

        noteDBRef.add(newNote)
            .addOnCompleteListener {
                Toast.makeText(this, "Nota agregada satisfactoriamente", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Hubo un error al momento de almacenar la nota", Toast.LENGTH_SHORT).show()
            }
    }
}
