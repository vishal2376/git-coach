package com.vishal2376.gitcoach.models.quiz

data class Quiz(
    val choices: List<String>,
    val correctAnswer: String,
    val id: Int,
    val question: String
)