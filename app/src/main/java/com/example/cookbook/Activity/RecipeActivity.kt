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
    private var foodName: String? = null // Declare foodName as a member variable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recipe_page)
        foodName = intent.getStringExtra("foodName") // Assign value to foodName member variable

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

        fetchInitialData()
    }

    private fun fetchInitialData() {
        db.collection("recipies")
            .get()
            .addOnSuccessListener { querySnapshot ->
                val snapshotList = querySnapshot.documents
                val newData = mutableListOf<RecipeData>() // Создаем новый экземпляр списка данных
                for (document in snapshotList) {
                    if (document != null && document.exists()) {
                        val foodName = document.getString("name") ?: ""
                        val ingredients = document.get("ingredients") as? ArrayList<String>
                        val recipeImage = document.getString("image") ?: ""
                        if (ingredients != null) {
                            val myData = RecipeData(foodName, ingredients, recipeImage)
                            newData.add(myData) // Добавляем данные в новый список
                        }
                    }
                }
                originalData.clear() // Очищаем старые данные из originalData
                originalData.addAll(newData) // Добавляем новые данные в originalData
                adapter.notifyDataSetChanged() // Обновляем адаптер
                Log.d("Data Size", "originalData Size: ${originalData.size}")
            }
    }
}