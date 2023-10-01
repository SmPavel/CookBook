package com.example.cookbook.Adapter

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cookbook.R
import com.google.firebase.firestore.FirebaseFirestore

data class NoteData(val exerName: String, var checkBox: Boolean, val documentId: String)

class NoteAdapter(private val context: Context, private val data: List<NoteData>) :
    RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_note, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvFoodName: TextView = itemView.findViewById(R.id.name)
        private val checkbox: CheckBox = itemView.findViewById(R.id.checkbox)
        private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

        fun bind(item: NoteData) {
            tvFoodName.text = item.exerName
            checkbox.isChecked = item.checkBox

            checkbox.setOnCheckedChangeListener(null)
            checkbox.setOnCheckedChangeListener { _, isChecked ->
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = data[position]
                    item.checkBox = isChecked
                    db.collection("foodNote").document(item.documentId)
                        .update("completed", isChecked)
                        .addOnSuccessListener {
                            Log.d(TAG, "DocumentSnapshot successfully updated!")
                        }
                        .addOnFailureListener { e ->
                            Log.w(TAG, "Error updating document", e)
                        }
                }
            }
        }
    }
}