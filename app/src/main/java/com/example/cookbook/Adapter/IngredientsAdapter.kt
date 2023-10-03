package com.example.cookbook.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cookbook.R

data class RecipeData(val foodName: String, val ingredientsList: ArrayList<String>, val recipeImage: String)

class IngredientsAdapter
    (private val data: List<String>
) : RecyclerView.Adapter<IngredientsAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ingredientsList: TextView = itemView.findViewById(R.id.ingredients)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_ingredients, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ingredient = data[position]
        holder.ingredientsList.setText(ingredient)
    }

}