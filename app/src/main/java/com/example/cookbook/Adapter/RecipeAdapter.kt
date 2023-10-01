package com.example.cookbook.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.cookbook.R

data class RecipeData(val foodName: String, val ingredients: List<String>, val recipeImage: String)

class RecipeAdapter(
    private val context: Context,
    private val data: List<RecipeData>
) : RecyclerView.Adapter<RecipeAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val foodNameText: TextView = itemView.findViewById(R.id.food_name)
        val ingredientsRecyclerView: RecyclerView = itemView.findViewById(R.id.ingredients_view)
        val recipeImage: ImageView = itemView.findViewById(R.id.recipe_image)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_ingredients, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.foodNameText.text = item.foodName

        // Создайте и настроьте адаптер для списка ингредиентов
        val ingredientsAdapter = IngredientsAdapter(context, item.ingredients)
        holder.ingredientsRecyclerView.adapter = ingredientsAdapter

        // Установите LayoutManager для RecyclerView
        val layoutManager = LinearLayoutManager(context)
        holder.ingredientsRecyclerView.layoutManager = layoutManager

        Glide.with(context)
            .load(item.recipeImage)
            .apply(RequestOptions().centerCrop().placeholder(R.drawable.ic_food))
            .into(holder.recipeImage)
    }


}