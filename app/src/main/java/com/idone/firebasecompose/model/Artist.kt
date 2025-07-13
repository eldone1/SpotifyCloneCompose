package com.idone.firebasecompose.model

data class Artist(
    val name: String,
    val description: String,
    val image: String,
    val songs:List<Song>
)