package com.example.cookbook.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cookbook.Activity.HomeActivity
import com.example.cookbook.Adapter.CookBookAdapter
import com.example.cookbook.Adapter.CookBookData
import com.example.cookbook.R
import com.google.firebase.firestore.FirebaseFirestore

class CookBookActivity : AppCompatActivity() {
    private lateinit var btnBack: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CookBookAdapter
    private lateinit var db: FirebaseFirestore
    private val originalData = mutableListOf<CookBookData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cookbook_page)

        btnBack = findViewById(R.id.back_button)
        recyclerView = findViewById(R.id.recipe_view)
        db = FirebaseFirestore.getInstance()
        adapter = CookBookAdapter(this, originalData)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this) // Set the layout manager for the RecyclerView

        btnBack.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        fetchInitialData()
    }

    private fun fetchInitialData() {
        db.collection("cookbook")
            .get()
            .addOnSuccessListener { querySnapshot ->
                val snapshotList = querySnapshot.documents
                for (document in snapshotList) {
                    if (document != null && document.exists()) {
                        val nameRef = document.getDocumentReference("name")
                        val imagePathRef = document.getDocumentReference("image")

                        // Fetch the referenced documents
                        nameRef?.get()?.addOnSuccessListener { nameSnapshot ->
                            val name = nameSnapshot.getString("name") ?: ""

                            // Fetch the referenced document for imagePath
                            imagePathRef?.get()?.addOnSuccessListener { imagePathSnapshot ->
                                val imagePath = imagePathSnapshot.getString("image") ?: ""
                                val myData = CookBookData(name, imagePath)
                                originalData.add(myData)
                                adapter.notifyDataSetChanged()
                            }
                        }
                    }
                }
            }
    }
}
