package com.example.cookbook.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cookbook.R

class IngredientsAdapter(
    private val context: Context,
    private val data: List<String>
) : RecyclerView.Adapter<IngredientsAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ingredientText: TextView = itemView.findViewById(R.id.ingredients)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_ingredients, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ingredient = data[position]
        holder.ingredientText.text = ingredient
    }
}