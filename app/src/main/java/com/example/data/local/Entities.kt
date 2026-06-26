package com.example.data.local

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

// 1. Repair Note Entity
@Entity(tableName = "repair_notes")
data class RepairNote(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val deviceModel: String,
    val symptom: String,
    val diagnosis: String,
    val solution: String,
    val status: String, // "Em Análise", "Concluído", "Sem Reparo"
    val createdAt: Long = System.currentTimeMillis()
)

// 2. Completed Lesson Entity
@Entity(tableName = "completed_lessons")
data class CompletedLesson(
    @PrimaryKey val lessonId: String,
    val completedAt: Long = System.currentTimeMillis()
)

// 3. Quiz Score Entity
@Entity(tableName = "quiz_scores")
data class QuizScore(
    @PrimaryKey val level: String, // "Iniciante", "Intermediário", "Avançado"
    val score: Int,
    val totalQuestions: Int,
    val updatedAt: Long = System.currentTimeMillis()
)

// DAO Interface
@Dao
interface RepairDao {
    // Repair Notes
    @Query("SELECT * FROM repair_notes ORDER BY createdAt DESC")
    fun getAllNotes(): Flow<List<RepairNote>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: RepairNote)

    @Query("DELETE FROM repair_notes WHERE id = :id")
    suspend fun deleteNoteById(id: Int)

    // Completed Lessons
    @Query("SELECT * FROM completed_lessons")
    fun getAllCompletedLessons(): Flow<List<CompletedLesson>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompletedLesson(lesson: CompletedLesson)

    @Query("DELETE FROM completed_lessons WHERE lessonId = :lessonId")
    suspend fun removeCompletedLesson(lessonId: String)

    // Quiz Scores
    @Query("SELECT * FROM quiz_scores")
    fun getAllQuizScores(): Flow<List<QuizScore>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuizScore(score: QuizScore)
}
