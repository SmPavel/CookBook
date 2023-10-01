package com.example.cookbook.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class GetUrlActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Get a reference to the Firebase Storage service
        val storage = FirebaseStorage.getInstance()

        // Create a reference to the image file
        val imageRef = storage.getReferenceFromUrl("gs://cookbook-5dc21.appspot.com/chebureki.jpeg")

        // Get the download URL of the image
        imageRef.downloadUrl.addOnSuccessListener { uri ->
            val imageUrl = uri.toString()

            // Store the URL in Firestore
            val db = FirebaseFirestore.getInstance()
            val imageDocRef = db.collection("recipies").document("NJ0NeNfigSPkjwmzZQUZ")

            val newImageUrl = hashMapOf<String, Any>("image" to imageUrl)

            imageDocRef.update(newImageUrl).addOnSuccessListener {
                // Handle successful update in Firestore if needed
            }.addOnFailureListener { e ->
                // Handle failure while updating Firestore if needed
            }
        }
    }
}
