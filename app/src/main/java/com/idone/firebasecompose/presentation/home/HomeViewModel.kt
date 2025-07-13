package com.idone.firebasecompose.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import com.idone.firebasecompose.presentation.model.Artist
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore

import kotlinx.coroutines.flow.callbackFlow
import com.google.firebase.ktx.Firebase
import com.idone.firebasecompose.domain.CanAccessToApp
import com.idone.firebasecompose.presentation.model.Player
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import okhttp3.internal.platform.ConscryptPlatform

class HomeViewModel:ViewModel() {

    private var canAccessToApp: CanAccessToApp = CanAccessToApp()
    private val database = Firebase.database
    private var db: FirebaseFirestore = Firebase.firestore



    private val _artists = MutableStateFlow<List<Artist>>(emptyList())
    val artist:StateFlow<List<Artist>> = _artists

    private val _player = MutableStateFlow<Player?>(null)
    val player: StateFlow<Player?> = _player

    private val _blockVersion = MutableStateFlow<Boolean>(false)
    val blockVersion:StateFlow<Boolean> = _blockVersion

    init {
        checkUserVersion()
        getArtists()
        getPlayer()

    }

    private fun checkUserVersion() {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                canAccessToApp()
            }
            _blockVersion.value = !result
        }
    }

    private fun getPlayer() {
        viewModelScope.launch {
            collectPlayer().collect {
                val player = it.getValue(Player::class.java)
                _player.value = player
            }
        }
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

    private fun collectPlayer(): Flow<DataSnapshot> = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                trySend(snapshot).isSuccess
            }

            override fun onCancelled(error: DatabaseError) {
                Log.i("Player", "Error: ${error.message}")
                close(error.toException())
            }
        }
        val ref = database.reference.child("player")
        ref.addValueEventListener(listener)

        awaitClose {
            ref.removeEventListener(listener)
        }
    }

    fun onPlaySelected(){
        if(player.value != null) {
            val currentPlayer: Player? = _player.value?.copy(play = !player.value?.play!!)
            val ref : DatabaseReference = database.reference.child("player")
            ref.setValue(currentPlayer)
        }
    }

    fun onCancelSelected(){
        val ref = database.reference.child("player")
        ref.setValue(null)

    }

    fun addPlayer(artist: Artist){

        val ref = database.reference.child("player")
        val player = Player(artist = artist, play = true)
        ref.setValue(player)


    }
}