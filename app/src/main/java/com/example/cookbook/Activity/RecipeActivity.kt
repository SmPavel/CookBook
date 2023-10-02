package com.example.cookbook.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cookbook.Adapter.RecipeAdapter
import com.example.cookbook.Adapter.RecipeData
import com.example.cookbook.R
import com.google.firebase.firestore.FirebaseFirestore


class RecipeActivity : AppCompatActivity() {
    private lateinit var btnBack: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecipeAdapter
    private lateinit var db: FirebaseFirestore
    private val originalData = mutableListOf<RecipeData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recipe_page)

        recyclerView = findViewById(R.id.ingredients_view)
        db = FirebaseFirestore.getInstance()
        adapter = RecipeAdapter(this, originalData)
        recyclerView.adapter = adapter

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        btnBack = findViewById(R.id.back_button)
        btnBack.setOnClickListener {
            val intent = Intent(this, CookBookActivity::class.java)
            startActivity(intent)
        }
        val recipeName = intent.getStringExtra("recipeName")
        val recipeImage = intent.getStringExtra("recipeImage")
        fetchInitialData(recipeName!!, recipeImage!!)
    }

    private fun fetchInitialData(recipeName: String, recipeImage: String) {
        db.collection("recipies")
            .whereEqualTo("name", recipeName)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val snapshotList = querySnapshot.documents
                for (document in snapshotList) {
                    if (document != null && document.exists()) {
                        val ingredients = document.get("ingredients") as List<*>
                        val myData = RecipeData(recipeName, ingredients, recipeImage)

                        originalData.clear()
                        originalData.add(myData)
                        adapter.notifyDataSetChanged()
                        Log.d("Data Size", "originalData Size: ${originalData.size}")
                    }
                }
            }
    }
}