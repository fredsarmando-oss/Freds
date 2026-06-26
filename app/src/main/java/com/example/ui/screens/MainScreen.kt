package com.example.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.viewmodel.RepairViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun MainScreen(viewModel: RepairViewModel) {
    val currentTab by viewModel.currentTab.collectAsState()
    val completedLessons by viewModel.completedLessons.collectAsState()
    val quizScores by viewModel.quizScores.collectAsState()

    // Dynamic workshop rank calculation
    val rankText = remember(completedLessons.size, quizScores) {
        val completedCount = completedLessons.size
        val hasAdvancedGabarito = quizScores["Avançado"]?.let { it.score == it.totalQuestions } ?: false

        when {
            completedCount >= 9 && hasAdvancedGabarito -> "MESTRE BUBA 🏆"
            completedCount >= 6 -> "Especialista de Bancada ⚡"
            completedCount >= 3 -> "Técnico Auxiliar 🛠️"
            else -> "Aprendiz de Bancada 🔋"
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        // Small icon
                        Icon(
                            imageVector = Icons.Default.Hardware,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Mestre Buba",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Black,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            Text(
                                text = "Nível: $rankText",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp,
                windowInsets = WindowInsets.navigationBars
            ) {
                NavigationBarItem(
                    selected = currentTab == 0,
                    onClick = { viewModel.selectTab(0) },
                    label = { Text("Aulas", fontSize = 11.sp) },
                    icon = {
                        Icon(
                            imageVector = if (currentTab == 0) Icons.Filled.MenuBook else Icons.Outlined.MenuBook,
                            contentDescription = "Aulas"
                        )
                    },
                    modifier = Modifier.testTag("nav_tab_lessons")
                )

                NavigationBarItem(
                    selected = currentTab == 1,
                    onClick = { viewModel.selectTab(1) },
                    label = { Text("Diagnósticos", fontSize = 11.sp) },
                    icon = {
                        Icon(
                            imageVector = if (currentTab == 1) Icons.Filled.Troubleshoot else Icons.Outlined.Troubleshoot,
                            contentDescription = "Diagnósticos"
                        )
                    },
                    modifier = Modifier.testTag("nav_tab_diagnostics")
                )

                NavigationBarItem(
                    selected = currentTab == 2,
                    onClick = { viewModel.selectTab(2) },
                    label = { Text("Quizzes", fontSize = 11.sp) },
                    icon = {
                        Icon(
                            imageVector = if (currentTab == 2) Icons.Filled.EmojiEvents else Icons.Outlined.EmojiEvents,
                            contentDescription = "Quizzes"
                        )
                    },
                    modifier = Modifier.testTag("nav_tab_quizzes")
                )

                NavigationBarItem(
                    selected = currentTab == 3,
                    onClick = { viewModel.selectTab(3) },
                    label = { Text("Reparos", fontSize = 11.sp) },
                    icon = {
                        Icon(
                            imageVector = if (currentTab == 3) Icons.Filled.ReceiptLong else Icons.Outlined.ReceiptLong,
                            contentDescription = "Reparos"
                        )
                    },
                    modifier = Modifier.testTag("nav_tab_notes")
                )
            }
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            AnimatedContent(
                targetState = currentTab,
                transitionSpec = {
                    fadeIn() with fadeOut()
                },
                label = "ScreenTransition"
            ) { targetTab ->
                when (targetTab) {
                    0 -> LessonsScreen(viewModel = viewModel)
                    1 -> DiagnosticsScreen(viewModel = viewModel)
                    2 -> QuizScreen(viewModel = viewModel)
                    3 -> NotesScreen(viewModel = viewModel)
                }
            }
        }
    }
}
