package com.example.ui.screens

import android.text.format.DateFormat
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.RepairNote
import com.example.ui.viewmodel.RepairViewModel
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(viewModel: RepairViewModel) {
    val editingNote by viewModel.editingNote.collectAsState()
    val notes by viewModel.repairNotes.collectAsState()

    var searchQuery by remember { mutableStateOf("") }

    if (editingNote != null) {
        NoteEditorView(viewModel = viewModel)
    } else {
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { viewModel.startNewNote() },
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.Black,
                    modifier = Modifier.testTag("add_note_fab")
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Adicionar Reparo")
                }
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = Modifier.height(8.dp))

                // Search Bar
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    label = { Text("Pesquisar reparos...") },
                    placeholder = { Text("Ex: iPhone, conector, curto...") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Pesquisar") },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { searchQuery = "" }) {
                                Icon(Icons.Default.Clear, contentDescription = "Limpar")
                            }
                        }
                    },
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("notes_search_input")
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Filtered Notes
                val filteredNotes = remember(notes, searchQuery) {
                    notes.filter { note ->
                        note.deviceModel.contains(searchQuery, ignoreCase = true) ||
                                note.symptom.contains(searchQuery, ignoreCase = true) ||
                                note.diagnosis.contains(searchQuery, ignoreCase = true) ||
                                note.solution.contains(searchQuery, ignoreCase = true)
                    }
                }

                if (filteredNotes.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(32.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ReceiptLong,
                                contentDescription = "Lista Vazia",
                                tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f),
                                modifier = Modifier.size(72.dp)
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "Nenhuma ordem de reparo registrada",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "Toque no botão + no canto inferior para registrar suas ordens de serviço e controlar seu laboratório!",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )
                        }
                    }
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.weight(1f),
                        contentPadding = PaddingValues(bottom = 80.dp)
                    ) {
                        items(filteredNotes, key = { it.id }) { note ->
                            RepairNoteCard(
                                note = note,
                                onEdit = { viewModel.startEditNote(note) },
                                onDelete = { viewModel.deleteNote(note.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}

// Helper to instantiate local state flow
@Composable
private fun mutableStateFlowText() = remember { mutableStateOf("") }

@Composable
fun RepairNoteCard(
    note: RepairNote,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    val statusColor = when (note.status) {
        "Concluído" -> MaterialTheme.colorScheme.tertiary
        "Sem Reparo" -> MaterialTheme.colorScheme.error
        else -> MaterialTheme.colorScheme.primary // "Em Análise"
    }

    val formattedDate = remember(note.createdAt) {
        val calendar = Calendar.getInstance().apply { timeInMillis = note.createdAt }
        DateFormat.format("dd/MM/yyyy • HH:mm", calendar).toString()
    }

    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("repair_note_card_${note.id}")
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header: Device Model and Status Badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Smartphone,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = note.deviceModel,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(statusColor.copy(alpha = 0.15f))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = note.status.uppercase(),
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = statusColor,
                        fontSize = 9.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = formattedDate,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Symptom & Diagnostic details
            TextLabelValue(label = "Sintoma:", value = note.symptom)
            if (note.diagnosis.isNotEmpty()) {
                TextLabelValue(label = "Diagnóstico:", value = note.diagnosis)
            }
            if (note.solution.isNotEmpty()) {
                TextLabelValue(label = "Solução:", value = note.solution)
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Action row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(
                    onClick = onEdit,
                    modifier = Modifier.testTag("edit_note_button_${note.id}")
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Editar Registro",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                }

                IconButton(
                    onClick = onDelete,
                    modifier = Modifier.testTag("delete_note_button_${note.id}")
                ) {
                    Icon(
                        imageVector = Icons.Default.DeleteOutline,
                        contentDescription = "Excluir Registro",
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun TextLabelValue(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            modifier = Modifier.width(100.dp)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteEditorView(viewModel: RepairViewModel) {
    val editingNote by viewModel.editingNote.collectAsState()
    val deviceModel by viewModel.noteDeviceModel.collectAsState()
    val symptom by viewModel.noteSymptom.collectAsState()
    val diagnosis by viewModel.noteDiagnosis.collectAsState()
    val solution by viewModel.noteSolution.collectAsState()
    val status by viewModel.noteStatus.collectAsState()

    val isNew = editingNote?.id == 0
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (isNew) "Novo Registro de Reparo" else "Editar Registro",
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { viewModel.cancelNoteEdit() }, modifier = Modifier.testTag("cancel_note_back")) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
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
                .verticalScroll(scrollState)
        ) {
            // Card Input Container
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    // Title
                    Text(
                        text = "Ordem de Serviço (Laboratório)",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Device Model Input
                    OutlinedTextField(
                        value = deviceModel,
                        onValueChange = { viewModel.noteDeviceModel.value = it },
                        label = { Text("Modelo do Celular *") },
                        placeholder = { Text("Ex: iPhone 12 Pro Max, Samsung S21") },
                        shape = RoundedCornerShape(10.dp),
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_device_model")
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Symptom Input
                    OutlinedTextField(
                        value = symptom,
                        onValueChange = { viewModel.noteSymptom.value = it },
                        label = { Text("Sintoma Relatado *") },
                        placeholder = { Text("Ex: Não carrega, tela piscando verde") },
                        shape = RoundedCornerShape(10.dp),
                        singleLine = false,
                        minLines = 2,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_symptom")
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Diagnosis Input
                    OutlinedTextField(
                        value = diagnosis,
                        onValueChange = { viewModel.noteDiagnosis.value = it },
                        label = { Text("Diagnóstico Encontrado") },
                        placeholder = { Text("Ex: Pino VBUS do conector Tipo-C quebrado") },
                        shape = RoundedCornerShape(10.dp),
                        singleLine = false,
                        minLines = 2,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_diagnosis")
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Solution Input
                    OutlinedTextField(
                        value = solution,
                        onValueChange = { viewModel.noteSolution.value = it },
                        label = { Text("Solução / Ação Aplicada") },
                        placeholder = { Text("Ex: Feito soldagem de novo conector Tipo-C e limpeza") },
                        shape = RoundedCornerShape(10.dp),
                        singleLine = false,
                        minLines = 2,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_solution")
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Status selection title
                    Text(
                        text = "Status do Reparo:",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Status selectable row
                    val statuses = listOf("Em Análise", "Concluído", "Sem Reparo")
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        statuses.forEach { item ->
                            val isSelected = status == item
                            val statusColor = when (item) {
                                "Concluído" -> MaterialTheme.colorScheme.tertiary
                                "Sem Reparo" -> MaterialTheme.colorScheme.error
                                else -> MaterialTheme.colorScheme.primary
                            }

                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(
                                        if (isSelected) statusColor.copy(alpha = 0.15f)
                                        else MaterialTheme.colorScheme.background
                                    )
                                    .border(
                                        width = if (isSelected) 2.dp else 1.dp,
                                        color = if (isSelected) statusColor else MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .clickable { viewModel.noteStatus.value = item }
                                    .padding(vertical = 10.dp)
                                    .testTag("status_chip_$item"),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = item,
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isSelected) statusColor else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Action Save/Cancel Buttons
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedButton(
                    onClick = { viewModel.cancelNoteEdit() },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .testTag("note_cancel_button")
                ) {
                    Text("Cancelar", fontWeight = FontWeight.Bold)
                }

                Button(
                    onClick = { viewModel.saveNote() },
                    enabled = deviceModel.trim().isNotEmpty() && symptom.trim().isNotEmpty(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .testTag("note_save_button")
                ) {
                    Text("Salvar Reparo", fontWeight = FontWeight.Bold, color = Color.Black)
                }
            }
        }
    }
}
