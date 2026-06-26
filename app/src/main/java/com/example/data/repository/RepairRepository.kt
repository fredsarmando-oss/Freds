package com.example.data.repository

import com.example.data.local.CompletedLesson
import com.example.data.local.QuizScore
import com.example.data.local.RepairDao
import com.example.data.local.RepairNote
import kotlinx.coroutines.flow.Flow

class RepairRepository(private val repairDao: RepairDao) {

    val allNotes: Flow<List<RepairNote>> = repairDao.getAllNotes()
    val allCompletedLessons: Flow<List<CompletedLesson>> = repairDao.getAllCompletedLessons()
    val allQuizScores: Flow<List<QuizScore>> = repairDao.getAllQuizScores()

    suspend fun insertNote(note: RepairNote) {
        repairDao.insertNote(note)
    }

    suspend fun deleteNoteById(id: Int) {
        repairDao.deleteNoteById(id)
    }

    suspend fun completeLesson(lessonId: String) {
        repairDao.insertCompletedLesson(CompletedLesson(lessonId))
    }

    suspend fun uncompleteLesson(lessonId: String) {
        repairDao.removeCompletedLesson(lessonId)
    }

    suspend fun saveQuizScore(level: String, score: Int, total: Int) {
        repairDao.insertQuizScore(QuizScore(level = level, score = score, totalQuestions = total))
    }
}
