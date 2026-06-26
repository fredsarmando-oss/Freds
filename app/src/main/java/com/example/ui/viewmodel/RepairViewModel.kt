package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.local.CompletedLesson
import com.example.data.local.QuizScore
import com.example.data.local.RepairNote
import com.example.data.repository.RepairRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RepairViewModel(private val repository: RepairRepository) : ViewModel() {

    // Bottom Navigation tab: 0 = Lessons, 1 = Diagnostics, 2 = Quizzes, 3 = Notes
    private val _currentTab = MutableStateFlow(0)
    val currentTab: StateFlow<Int> = _currentTab.asStateFlow()

    // Active lesson view (null means viewing list)
    private val _activeLessonId = MutableStateFlow<String?>(null)
    val activeLessonId: StateFlow<String?> = _activeLessonId.asStateFlow()

    // Lesson search query
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    // Lesson level filter: "Todos", "Iniciante", "Intermediário", "Avançado"
    private val _levelFilter = MutableStateFlow("Todos")
    val levelFilter: StateFlow<String> = _levelFilter.asStateFlow()

    // Active Quiz Level (null means level selection screen)
    private val _activeQuizLevel = MutableStateFlow<String?>(null)
    val activeQuizLevel: StateFlow<String?> = _activeQuizLevel.asStateFlow()

    // Active quiz question index
    private val _currentQuestionIndex = MutableStateFlow(0)
    val currentQuestionIndex: StateFlow<Int> = _currentQuestionIndex.asStateFlow()

    // Selected option index for the current quiz question
    private val _selectedOptionIndex = MutableStateFlow<Int?>(null)
    val selectedOptionIndex: StateFlow<Int?> = _selectedOptionIndex.asStateFlow()

    // Show feedback for current quiz question (correct/incorrect state)
    private val _showQuizFeedback = MutableStateFlow(false)
    val showQuizFeedback: StateFlow<Boolean> = _showQuizFeedback.asStateFlow()

    // Counter of correct answers in current quiz
    private val _correctAnswersCount = MutableStateFlow(0)
    val correctAnswersCount: StateFlow<Int> = _correctAnswersCount.asStateFlow()

    // Is active quiz complete
    private val _isQuizComplete = MutableStateFlow(false)
    val isQuizComplete: StateFlow<Boolean> = _isQuizComplete.asStateFlow()

    // Active Diagnostic Symptom ID (null means main diagnostic hub)
    private val _activeSymptomId = MutableStateFlow<String?>(null)
    val activeSymptomId: StateFlow<String?> = _activeSymptomId.asStateFlow()

    // Diagnostic Checklist progress (sets of checked steps by symptom)
    private val _checkedSymptomSteps = MutableStateFlow<Map<String, Set<Int>>>(emptyMap())
    val checkedSymptomSteps: StateFlow<Map<String, Set<Int>>> = _checkedSymptomSteps.asStateFlow()

    // Note Editor state
    private val _editingNote = MutableStateFlow<RepairNote?>(null)
    val editingNote: StateFlow<RepairNote?> = _editingNote.asStateFlow()

    // Note form state (used when adding or editing)
    val noteDeviceModel = MutableStateFlow("")
    val noteSymptom = MutableStateFlow("")
    val noteDiagnosis = MutableStateFlow("")
    val noteSolution = MutableStateFlow("")
    val noteStatus = MutableStateFlow("Em Análise") // Default

    // Observables from Database via Repository
    val repairNotes: StateFlow<List<RepairNote>> = repository.allNotes
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val completedLessons: StateFlow<Set<String>> = repository.allCompletedLessons
        .combine(MutableStateFlow(emptySet<String>())) { list, _ ->
            list.map { it.lessonId }.toSet()
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptySet())

    val quizScores: StateFlow<Map<String, QuizScore>> = repository.allQuizScores
        .combine(MutableStateFlow(emptyMap<String, QuizScore>())) { list, _ ->
            list.associateBy { it.level }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyMap())

    // NAVIGATION Actions
    fun selectTab(tab: Int) {
        _currentTab.value = tab
        // Clear sub-states when switching tabs for a clean user experience
        _activeLessonId.value = null
        _activeQuizLevel.value = null
        _activeSymptomId.value = null
        _editingNote.value = null
    }

    // LESSONS Actions
    fun viewLesson(lessonId: String?) {
        _activeLessonId.value = lessonId
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setLevelFilter(filter: String) {
        _levelFilter.value = filter
    }

    fun toggleLessonCompleted(lessonId: String, isCompleted: Boolean) {
        viewModelScope.launch {
            if (isCompleted) {
                repository.completeLesson(lessonId)
            } else {
                repository.uncompleteLesson(lessonId)
            }
        }
    }

    // QUIZ Actions
    fun startQuiz(level: String) {
        _activeQuizLevel.value = level
        _currentQuestionIndex.value = 0
        _selectedOptionIndex.value = null
        _showQuizFeedback.value = false
        _correctAnswersCount.value = 0
        _isQuizComplete.value = false
    }

    fun selectQuizOption(index: Int) {
        if (_showQuizFeedback.value) return // Prevent changing answer after selection
        _selectedOptionIndex.value = index
    }

    fun confirmQuizAnswer(isCorrect: Boolean) {
        _showQuizFeedback.value = true
        if (isCorrect) {
            _correctAnswersCount.value += 1
        }
    }

    fun nextQuizQuestion(totalQuestions: Int) {
        val nextIndex = _currentQuestionIndex.value + 1
        if (nextIndex < totalQuestions) {
            _currentQuestionIndex.value = nextIndex
            _selectedOptionIndex.value = null
            _showQuizFeedback.value = false
        } else {
            // Quiz completed
            _isQuizComplete.value = true
            viewModelScope.launch {
                val level = _activeQuizLevel.value ?: "Iniciante"
                repository.saveQuizScore(level, _correctAnswersCount.value, totalQuestions)
            }
        }
    }

    fun exitQuiz() {
        _activeQuizLevel.value = null
        _isQuizComplete.value = false
    }

    // DIAGNOSTICS Actions
    fun selectSymptom(symptomId: String?) {
        _activeSymptomId.value = symptomId
    }

    fun toggleDiagnosticStep(symptomId: String, stepIndex: Int) {
        val currentMap = _checkedSymptomSteps.value.toMutableMap()
        val currentSet = currentMap[symptomId]?.toMutableSet() ?: mutableSetOf()
        if (currentSet.contains(stepIndex)) {
            currentSet.remove(stepIndex)
        } else {
            currentSet.add(stepIndex)
        }
        currentMap[symptomId] = currentSet
        _checkedSymptomSteps.value = currentMap
    }

    // NOTES Actions
    fun startNewNote() {
        _editingNote.value = RepairNote(id = 0, deviceModel = "", symptom = "", diagnosis = "", solution = "", status = "Em Análise")
        noteDeviceModel.value = ""
        noteSymptom.value = ""
        noteDiagnosis.value = ""
        noteSolution.value = ""
        noteStatus.value = "Em Análise"
    }

    fun startEditNote(note: RepairNote) {
        _editingNote.value = note
        noteDeviceModel.value = note.deviceModel
        noteSymptom.value = note.symptom
        noteDiagnosis.value = note.diagnosis
        noteSolution.value = note.solution
        noteStatus.value = note.status
    }

    fun cancelNoteEdit() {
        _editingNote.value = null
    }

    fun saveNote() {
        val current = _editingNote.value ?: return
        viewModelScope.launch {
            val newOrUpdatedNote = RepairNote(
                id = current.id, // 0 handles auto-generate
                deviceModel = noteDeviceModel.value.trim(),
                symptom = noteSymptom.value.trim(),
                diagnosis = noteDiagnosis.value.trim(),
                solution = noteSolution.value.trim(),
                status = noteStatus.value,
                createdAt = if (current.id != 0) current.createdAt else System.currentTimeMillis()
            )
            if (newOrUpdatedNote.deviceModel.isNotEmpty() && newOrUpdatedNote.symptom.isNotEmpty()) {
                repository.insertNote(newOrUpdatedNote)
                _editingNote.value = null
            }
        }
    }

    fun deleteNote(id: Int) {
        viewModelScope.launch {
            repository.deleteNoteById(id)
        }
    }
}

// ViewModel Factory
class RepairViewModelFactory(private val repository: RepairRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RepairViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RepairViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
