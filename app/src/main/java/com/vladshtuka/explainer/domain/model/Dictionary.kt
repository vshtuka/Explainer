package com.vladshtuka.explainer.domain.model

data class Dictionary(
    val id: Int? = null,
    val name: String,
    val description: String,
    val language: String,
    val words: MutableList<String>,
    val wordsNumber: String
)
