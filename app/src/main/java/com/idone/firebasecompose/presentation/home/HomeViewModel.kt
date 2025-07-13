package com.idone.firebasecompose.presentation.home

import androidx.lifecycle.ViewModel
import com.idone.firebasecompose.presentation.model.Artist
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class HomeViewModel:ViewModel() {

    private var db: FirebaseFirestore = Firebase.firestore


    private val _artists = MutableStateFlow<List<Artist>>(emptyList())
    val artist:StateFlow<List<Artist>> = _artists

    init {
        getArtists()
    }

    private fun getArtists() {
        viewModelScope.launch {
            val result: List<Artist> = withContext(Dispatchers.IO) {
            getAllArtists()
            }
            _artists.value = result
        }
    }

    suspend fun getAllArtists(): List<Artist> {
        return try {
            db.collection("artists")
                .get()
                .await()
                .documents
                .mapNotNull { snapshot ->
                    snapshot.toObject(Artist::class.java)
                }
        }catch (e: Exception){
            emptyList()
        }
    }
}