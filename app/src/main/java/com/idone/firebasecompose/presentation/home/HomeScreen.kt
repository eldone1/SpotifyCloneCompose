package com.idone.firebasecompose.presentation.home

import android.util.Log
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.google.android.play.core.integrity.d
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore


@Composable
fun HomeScreen(db: FirebaseFirestore){


    Button(onClick = {
        createArtist(db)
    }){
        Text(text = "Create Artist")
    }
}

data class Artist(
    val name: String,
    val numberOfSongs: Int
)


fun createArtist(db: FirebaseFirestore){
    val random = (1..10000).random()
    val artist = Artist(name =  "Random $random", numberOfSongs = random)

    db.collection("artists")
        .add(artist)
        .addOnSuccessListener {
            Log.i("done", "SUCCESS")
        }
        .addOnFailureListener {
            Log.i("done", "FAILURE")
        }
        .addOnCompleteListener {
            Log.i("done", "COMPLETE")
        }
}