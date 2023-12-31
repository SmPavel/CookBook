package com.example.cookbook.Adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.cookbook.Activity.RecipeActivity
import com.example.cookbook.R

class CookBookAdapter(
    private val context: Context,
    private val data: List<RecipeData>
) : RecyclerView.Adapter<CookBookAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvFoodName: TextView = itemView.findViewById(R.id.name)
        val btnRecipeImage: AppCompatImageButton =
            itemView.findViewById(R.id.recipe_image_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_cookbook, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.tvFoodName.text = item.foodName
        Glide.with(context)
            .load(item.recipeImage)
            .apply(RequestOptions())
            .into(holder.btnRecipeImage)

        holder.btnRecipeImage.setOnClickListener {
            val intent = Intent(context, RecipeActivity::class.java)
            intent.putExtra("recipeName", item.foodName)
            intent.putExtra("recipeImage", item.recipeImage)
            intent.putStringArrayListExtra("ingredientsList", item.ingredientsList)
            context.startActivity(intent)
        }
    }
}
