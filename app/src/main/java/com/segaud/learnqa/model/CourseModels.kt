package com.segaud.learnqa.model

data class Subject(
    val id: String,
    val title: String,
    val description: String,
    val units: List<LearningUnit>
)

data class LearningUnit(
    val id: String,
    val title: String,
    val description: String,
    val lessons: List<Lesson>
)

data class Lesson(
    val id: String,
    val title: String,
    val description: String,
    val exercises: List<Exercise>
)