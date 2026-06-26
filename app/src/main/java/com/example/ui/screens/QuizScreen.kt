package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.QuizQuestions
import com.example.ui.viewmodel.RepairViewModel

@Composable
fun QuizScreen(viewModel: RepairViewModel) {
    val activeLevel by viewModel.activeQuizLevel.collectAsState()
    val isQuizComplete by viewModel.isQuizComplete.collectAsState()

    if (activeLevel != null) {
        if (isQuizComplete) {
            QuizResultsView(viewModel = viewModel, level = activeLevel!!)
        } else {
            ActiveQuizView(viewModel = viewModel, level = activeLevel!!)
        }
    } else {
        QuizLevelSelector(viewModel = viewModel)
    }
}

@Composable
fun QuizLevelSelector(viewModel: RepairViewModel) {
    val quizScores by viewModel.quizScores.collectAsState()
    val completedLessons by viewModel.completedLessons.collectAsState()

    val levels = listOf(
        Triple("Iniciante", "Desmontagem, cuidados e ferramentas básicas", MaterialTheme.colorScheme.tertiary),
        Triple("Intermediário", "Uso do multímetro, fonte de bancada e conectores", MaterialTheme.colorScheme.secondary),
        Triple("Avançado", "Micro-soldagem BGA, leitura de esquemas e injeção", MaterialTheme.colorScheme.primary)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(12.dp))

        // Quiz Header Banner
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ),
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.secondary,
                    shape = RoundedCornerShape(24.dp)
                )
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "DESAFIOS DE BANCADA",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f),
                    letterSpacing = 1.sp
                )
                Text(
                    text = "Desafios do Mestre Buba",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Black,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Teste suas habilidades e conquiste certificados para provar que você é um técnico de elite!",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Selecione o nível do desafio:",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(10.dp))

        levels.forEach { (level, desc, color) ->
            val scoreInfo = quizScores[level]
            val recordText = if (scoreInfo != null) {
                "Seu Recorde: ${scoreInfo.score}/${scoreInfo.totalQuestions} acertos"
            } else {
                "Não jogado ainda"
            }

            Card(
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .clickable { viewModel.startQuiz(level) }
                    .testTag("quiz_level_card_$level"),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(52.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(color.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = when (level) {
                                "Iniciante" -> Icons.Default.Handyman
                                "Intermediário" -> Icons.Default.ElectricBolt
                                else -> Icons.Default.WorkspacePremium
                            },
                            contentDescription = null,
                            tint = color,
                            modifier = Modifier.size(28.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(14.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = level,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = color
                        )
                        Text(
                            text = desc,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.EmojiEvents,
                                contentDescription = null,
                                tint = if (scoreInfo != null && scoreInfo.score == scoreInfo.totalQuestions) MaterialTheme.colorScheme.primary
                                else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = recordText,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                            )
                        }
                    }

                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Jogar",
                        tint = color
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Badge Showcase
        BadgeShowcase(quizScores = quizScores)

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun BadgeShowcase(quizScores: Map<String, com.example.data.local.QuizScore>) {
    Text(
        text = "Seus Emblemas de Conquistas",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary
    )

    Spacer(modifier = Modifier.height(8.dp))

    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            val badgeList = listOf(
                BadgeData(
                    "Especialista em Desmontagem 🔋",
                    "Acertou 4+ no nível Iniciante",
                    quizScores["Iniciante"]?.let { it.score >= 4 } ?: false
                ),
                BadgeData(
                    "Técnico de Placas Profissional 🛠️",
                    "Acertou 4+ no nível Intermediário",
                    quizScores["Intermediário"]?.let { it.score >= 4 } ?: false
                ),
                BadgeData(
                    "Mestre Soldador Certificado 🏆",
                    "Gabaritou (5/5) no nível Avançado",
                    quizScores["Avançado"]?.let { it.score >= 5 } ?: false
                )
            )

            badgeList.forEach { badge ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                if (badge.isUnlocked) MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                                else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (badge.isUnlocked) Icons.Default.MilitaryTech else Icons.Default.Lock,
                            contentDescription = null,
                            tint = if (badge.isUnlocked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(
                            text = badge.title,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = if (badge.isUnlocked) MaterialTheme.colorScheme.onBackground
                            else MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f)
                        )
                        Text(
                            text = badge.requirement,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
                        )
                    }
                }
            }
        }
    }
}

data class BadgeData(val title: String, val requirement: String, val isUnlocked: Boolean)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActiveQuizView(viewModel: RepairViewModel, level: String) {
    val currentQuestionIndex by viewModel.currentQuestionIndex.collectAsState()
    val selectedOptionIndex by viewModel.selectedOptionIndex.collectAsState()
    val showFeedback by viewModel.showQuizFeedback.collectAsState()

    val questions = remember(level) { QuizQuestions.levelQuestions[level] ?: emptyList() }
    val currentQuestion = questions.getOrNull(currentQuestionIndex)

    if (currentQuestion == null) {
        viewModel.exitQuiz()
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Desafio - $level", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { viewModel.exitQuiz() }, modifier = Modifier.testTag("quiz_exit_button")) {
                        Icon(Icons.Default.Close, contentDescription = "Sair do Quiz")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Question Progress Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Pergunta ${currentQuestionIndex + 1} de ${questions.size}",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${((currentQuestionIndex.toFloat() / questions.size) * 100).toInt()}%",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            LinearProgressIndicator(
                progress = { (currentQuestionIndex.toFloat() / questions.size) },
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp))
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Question Box
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = currentQuestion.question,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 28.sp,
                    modifier = Modifier.padding(20.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Option Cards
            currentQuestion.options.forEachIndexed { index, option ->
                val isSelected = selectedOptionIndex == index
                val optionColor = when {
                    showFeedback && index == currentQuestion.correctAnswerIndex -> MaterialTheme.colorScheme.tertiary
                    showFeedback && isSelected -> MaterialTheme.colorScheme.error
                    isSelected -> MaterialTheme.colorScheme.primary
                    else -> MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
                }

                val optionBackground = when {
                    showFeedback && index == currentQuestion.correctAnswerIndex -> MaterialTheme.colorScheme.tertiary.copy(alpha = 0.1f)
                    showFeedback && isSelected -> MaterialTheme.colorScheme.error.copy(alpha = 0.1f)
                    isSelected -> MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)
                    else -> MaterialTheme.colorScheme.surface
                }

                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = optionBackground),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .border(
                            width = if (isSelected || (showFeedback && index == currentQuestion.correctAnswerIndex)) 2.dp else 1.dp,
                            color = optionColor,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clickable(enabled = !showFeedback) { viewModel.selectQuizOption(index) }
                        .testTag("quiz_option_$index")
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Letter option index
                        Box(
                            modifier = Modifier
                                .size(30.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .background(optionColor.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = ('A' + index).toString(),
                                fontWeight = FontWeight.Bold,
                                color = optionColor
                            )
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Text(
                            text = option,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.weight(1f)
                        )

                        // Status Icon in feedback mode
                        if (showFeedback) {
                            if (index == currentQuestion.correctAnswerIndex) {
                                Icon(Icons.Default.Check, "Correto", tint = MaterialTheme.colorScheme.tertiary)
                            } else if (isSelected) {
                                Icon(Icons.Default.Close, "Incorreto", tint = MaterialTheme.colorScheme.error)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Explanation & Confirm Buttons
            AnimatedVisibility(
                visible = showFeedback,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Analytics,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "EXPLICAÇÃO DO MESTRE",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary,
                                letterSpacing = 1.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = currentQuestion.explanation,
                            style = MaterialTheme.typography.bodyMedium,
                            lineHeight = 20.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            if (!showFeedback) {
                Button(
                    onClick = {
                        val isCorrect = selectedOptionIndex == currentQuestion.correctAnswerIndex
                        viewModel.confirmQuizAnswer(isCorrect)
                    },
                    enabled = selectedOptionIndex != null,
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("quiz_confirm_button")
                ) {
                    Text("Confirmar Resposta", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
            } else {
                Button(
                    onClick = { viewModel.nextQuizQuestion(questions.size) },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("quiz_next_button")
                ) {
                    Text(
                        text = if (currentQuestionIndex + 1 == questions.size) "Finalizar Desafio" else "Próxima Pergunta",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}

@Composable
fun QuizResultsView(viewModel: RepairViewModel, level: String) {
    val correctCount by viewModel.correctAnswersCount.collectAsState()
    val questions = remember(level) { QuizQuestions.levelQuestions[level] ?: emptyList() }
    val totalQuestions = questions.size

    val ratio = correctCount.toFloat() / totalQuestions

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = if (ratio >= 0.8f) Icons.Default.WorkspacePremium else Icons.Default.School,
            contentDescription = null,
            tint = if (ratio >= 0.8f) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary,
            modifier = Modifier.size(96.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = if (ratio >= 0.8f) "Parabéns, Mestre!" else "Bom Esforço!",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.ExtraBold,
            color = if (ratio >= 0.8f) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
        )

        Text(
            text = if (ratio >= 0.8f) "Você demonstrou conhecimentos sólidos de alto nível!"
            else "Continue revisando o conteúdo das aulas para aperfeiçoar sua bancada.",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
            modifier = Modifier.padding(horizontal = 24.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "RESULTADO FINAL",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "$correctCount / $totalQuestions",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Black,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 42.sp
                )

                Text(
                    text = "${(ratio * 100).toInt()}% de Acerto",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Show badge unlocked if appropriate
                if (ratio >= 0.8f) {
                    val badgeName = when (level) {
                        "Iniciante" -> "Especialista em Desmontagem 🔋"
                        "Intermediário" -> "Técnico de Placas Profissional 🛠️"
                        else -> "Mestre Soldador Certificado 🏆"
                    }
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f))
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.MilitaryTech, null, tint = MaterialTheme.colorScheme.primary)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Desbloqueou: $badgeName",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { viewModel.exitQuiz() },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(48.dp)
                .testTag("quiz_finish_back_button")
        ) {
            Text("Voltar ao Menu", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
    }
}
