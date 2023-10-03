package com.example.cookbook.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cookbook.Adapter.CookBookAdapter
import com.example.cookbook.Adapter.RecipeData
import com.example.cookbook.R
import com.google.firebase.firestore.FirebaseFirestore

class CookBookActivity : AppCompatActivity() {
    private lateinit var btnBack: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CookBookAdapter
    private lateinit var db: FirebaseFirestore
    private val originalData = mutableListOf<RecipeData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cookbook_page)

        db = FirebaseFirestore.getInstance()

        btnBack = findViewById(R.id.back_button)
        recyclerView = findViewById(R.id.recipe_view)

        val layoutManager = GridLayoutManager(this, 2)
        recyclerView.layoutManager = layoutManager

        adapter = CookBookAdapter(this, originalData)
        recyclerView.adapter = adapter


        btnBack.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        fetchInitialData()
    }

    private fun fetchInitialData() {
        db.collection("recipies")
            .get()
            .addOnSuccessListener { querySnapshot ->
                val snapshotList = querySnapshot.documents
                originalData.clear()
                for (document in snapshotList) {
                    if (document != null && document.exists()) {
                        val foodData = RecipeData(
                            document["name"] as String,
                            document["ingredients"] as ArrayList<String>,
                            document["image"] as String
                        )
                        originalData.add(foodData)
                    }
                }
                adapter.notifyDataSetChanged()
            }
    }
}