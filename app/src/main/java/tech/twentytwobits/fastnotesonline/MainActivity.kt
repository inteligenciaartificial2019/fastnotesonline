package tech.twentytwobits.fastnotesonline

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.activity_main.*
import tech.twentytwobits.fastnotesonline.Adapters.NoteAdapter
import tech.twentytwobits.fastnotesonline.interfaces.RecyclerNoteListener
import tech.twentytwobits.fastnotesonline.models.Note
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    // Variables para Firebase Firestore
    private val store: FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var noteDBRef: CollectionReference
    private val noteList: ArrayList<Note> = ArrayList()
    private lateinit var adapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        noteDBRef = store.collection("notes")

        val layoutManager = LinearLayoutManager(this)
        adapter = NoteAdapter(noteList, object: RecyclerNoteListener {
            override fun onClick(note: Note, position: Int) {
                Toast.makeText(applicationContext, "${note.title}", Toast.LENGTH_SHORT).show()
            }

            override fun onLongClick(note: Note, position: Int) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })

        rvNotas.setHasFixedSize(true)
        rvNotas.layoutManager = layoutManager
        rvNotas.itemAnimator = DefaultItemAnimator()
        rvNotas.adapter = adapter

        getNotesFromDB()

        fabAgregarNota.setOnClickListener {
            startActivity(Intent(this, NoteAddActivity::class.java))
        }


    }

    private fun getNotesFromDB() {
        noteDBRef.addSnapshotListener(object: EventListener, com.google.firebase.firestore.EventListener<QuerySnapshot>{
            override fun onEvent(snapshot: QuerySnapshot?, exception: FirebaseFirestoreException?) {
                exception?.let {
                    Toast.makeText(applicationContext, "Error al momento de cargar la información", Toast.LENGTH_SHORT).show()
                    return
                }
                snapshot?.let {
                    val note = it.toObjects(Note::class.java)
                    noteList.addAll(note)
                    adapter.notifyDataSetChanged()
                }
            }

        })
    }
}
