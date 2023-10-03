package com.example.cookbook.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.cookbook.Adapter.IngredientsAdapter
import com.example.cookbook.Adapter.RecipeData
import com.example.cookbook.R
import com.google.firebase.firestore.FirebaseFirestore

class RecipeActivity : AppCompatActivity() {
    private lateinit var btnBack: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var db: FirebaseFirestore
    private val originalData = mutableListOf<RecipeData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recipe_page)

        recyclerView = findViewById(R.id.ingredients_view)
        db = FirebaseFirestore.getInstance()

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        btnBack = findViewById(R.id.btnBack)
        btnBack.setOnClickListener(null)
        btnBack.setOnClickListener {
            val intent = Intent(this, CookBookActivity::class.java)
            startActivity(intent)
            finish()
        }

        val name: TextView = findViewById(R.id.food_name)
        val ingredients: RecyclerView = findViewById(R.id.ingredients_view)
        val image: ImageView = findViewById(R.id.recipe_image)

        val recipeName = intent.getStringExtra("recipeName")!!
        val ingredientsList = intent.getStringArrayListExtra("ingredientsList")!!
        val recipeImage = intent.getStringExtra("recipeImage")!!

        name.text = recipeName

        Glide.with(this)
            .load(recipeImage)
            .apply(RequestOptions().centerCrop().placeholder(R.drawable.ic_food))
            .into(image)

        val recipeData = RecipeData(recipeName, ingredientsList, recipeImage)
        originalData.add(recipeData)

        val ingredientsAdapter = IngredientsAdapter(ingredientsList)
        recyclerView.adapter = ingredientsAdapter
    }
}
