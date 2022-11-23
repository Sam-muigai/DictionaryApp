package com.example.dictionaryapp.model.wordContent

data class Phonetic(
    val audio: String,
    val license: License,
    val sourceUrl: String,
    val text: String
)