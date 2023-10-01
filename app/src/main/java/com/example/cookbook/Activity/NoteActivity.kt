package com.example.cookbook.Activity

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cookbook.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.example.cookbook.Adapter.NoteAdapter
import com.example.cookbook.Adapter.NoteData

class NoteActivity : AppCompatActivity() {
    private lateinit var etFoodName: EditText

    private lateinit var btnAdd: AppCompatImageButton
    private lateinit var btnDelete: FloatingActionButton
    private lateinit var btnBack: Button

    private lateinit var recyclerView: RecyclerView

    private lateinit var adapter: NoteAdapter
    private lateinit var db: FirebaseFirestore
    private val data = mutableListOf<NoteData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            setContentView(R.layout.note_page)

            etFoodName = findViewById(R.id.name)

            btnAdd = findViewById(R.id.add_button)
            btnDelete = findViewById(R.id.delete_button)
            btnBack = findViewById(R.id.back)

            recyclerView = findViewById(R.id.exercisesView)

            db = FirebaseFirestore.getInstance()
            adapter = NoteAdapter(this, data)

            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = adapter

            fetchData()

            btnAdd.setOnClickListener {
                val foodName = etFoodName.text.toString().trim()

                if (foodName.isNotEmpty()) {
                    val competed = false

                    val foodData = hashMapOf(
                        "userId" to userId,
                        "name" to foodName,
                        "checked" to competed
                    )

                    db.collection("foodNote")
                        .add(foodData)
                        .addOnSuccessListener { documentReference ->
                            val documentId = documentReference.id
                            val newFoodData = NoteData(foodName, competed, documentId)
                            data.add(newFoodData)
                            adapter.notifyDataSetChanged()

                            recyclerView.scrollToPosition(adapter.itemCount - 1)

                            etFoodName.setText("")
                        }
                        .addOnFailureListener { exception ->
                            Log.e(ContentValues.TAG, "Error adding data: $exception")
                        }
                }
            }

            btnBack.setOnClickListener {
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            }

            btnDelete.setOnClickListener {
                auth.signOut()
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                /*deleteCompletedExercises()*/
            }
        }

            val auth = FirebaseAuth.getInstance()
            val currentUser = auth.currentUser
            val userId = currentUser!!.uid
            private fun fetchData() {
                db.collection("foodNote")
                    .whereEqualTo("userId", userId)
                    .get()
                    .addOnSuccessListener { querySnapshot ->
                        val snapshotList = querySnapshot.documents
                        data.clear()
                        for (document in snapshotList) {
                            if (document != null && document.exists()) {
                                val foodData = NoteData(
                                    document["name"] as String,
                                    document["checked"] as? Boolean ?: false,
                                    document.id
                                )
                                data.add(foodData)
                            }
                        }
                        adapter.notifyDataSetChanged()

                        recyclerView.scrollToPosition(adapter.itemCount - 1)
                    }
            }

            private fun deleteCompletedExercises() {
                db.collection("foodNote")
                    .whereEqualTo("checked", true)
                    .get()
                    .addOnSuccessListener { querySnapshot ->
                        for (document in querySnapshot.documents) {
                            db.collection("foodNote").document(document.id)
                                .delete()
                                .addOnSuccessListener {
                                    Log.d(ContentValues.TAG, "Document successfully deleted!")
                                    data.clear() // Очищаем список data
                                    fetchData() // Получаем обновленные данные из Firebase Firestore
                                    adapter.notifyDataSetChanged() // Обновляем отображение в RecyclerView
                                }
                                .addOnFailureListener { e ->
                                    Log.w(ContentValues.TAG, "Error deleting document", e)
                                }
                        }
                    }
                    .addOnFailureListener { e ->
                        Log.w(ContentValues.TAG, "Error retrieving documents", e)
                    }
            }
        }