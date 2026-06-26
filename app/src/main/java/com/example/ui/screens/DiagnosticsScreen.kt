package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.viewmodel.RepairViewModel

data class DiagnosticStep(
    val title: String,
    val level: String, // "Iniciante", "Intermediário", "Avançado"
    val instruction: String,
    val tip: String
)

data class SymptomDiagnostic(
    val id: String,
    val title: String,
    val description: String,
    val icon: @Composable (Color) -> Unit,
    val steps: List<DiagnosticStep>
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiagnosticsScreen(viewModel: RepairViewModel) {
    val activeSymptomId by viewModel.activeSymptomId.collectAsState()
    val checkedStepsMap by viewModel.checkedSymptomSteps.collectAsState()

    val diagnostics = remember { getDiagnosticsList() }

    if (activeSymptomId != null) {
        val symptom = diagnostics.find { it.id == activeSymptomId }
        if (symptom != null) {
            val checkedSteps = checkedStepsMap[symptom.id] ?: emptySet()
            SymptomDetailView(
                symptom = symptom,
                checkedSteps = checkedSteps,
                onToggleStep = { index -> viewModel.toggleDiagnosticStep(symptom.id, index) },
                onBack = { viewModel.selectSymptom(null) }
            )
        } else {
            viewModel.selectSymptom(null)
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(12.dp))

            // Hero Diagnostic Card
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Troubleshoot,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(32.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Guia de Diagnósticos",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "Siga o fluxo lógico passo a passo para encontrar e consertar o defeito com precisão.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Selecione o sintoma relatado:",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(10.dp))

            // List of Symptoms
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                itemsIndexed(diagnostics) { _, item ->
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { viewModel.selectSymptom(item.id) }
                            .testTag("symptom_card_${item.id}"),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        )
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            item.icon(MaterialTheme.colorScheme.primary)

                            Spacer(modifier = Modifier.width(14.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = item.title,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = item.description,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                                )
                            }

                            Icon(
                                imageVector = Icons.Default.ChevronRight,
                                contentDescription = "Ver fluxo",
                                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SymptomDetailView(
    symptom: SymptomDiagnostic,
    checkedSteps: Set<Int>,
    onToggleStep: (Int) -> Unit,
    onBack: () -> Unit
) {
    val scrollState = rememberScrollState()
    val progress = if (symptom.steps.isNotEmpty()) checkedSteps.size.toFloat() / symptom.steps.size else 0f

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Análise de Falha",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack, modifier = Modifier.testTag("symptom_back_button")) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Main Content Area (Scrollable)
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(scrollState)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                // Header card
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            symptom.icon(MaterialTheme.colorScheme.primary)
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = symptom.title,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = symptom.description,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        // Progress Indicator
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Etapas de Análise: ${checkedSteps.size} de ${symptom.steps.size}",
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "${(progress * 100).toInt()}%",
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        LinearProgressIndicator(
                            progress = { progress },
                            color = MaterialTheme.colorScheme.primary,
                            trackColor = MaterialTheme.colorScheme.surfaceVariant,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.dp)
                                .clip(RoundedCornerShape(3.dp))
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "FLUXOGRAMA DE VERIFICAÇÃO",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    letterSpacing = 1.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Steps Rendering
                symptom.steps.forEachIndexed { index, step ->
                    val isChecked = checkedSteps.contains(index)
                    val stepColor = when (step.level) {
                        "Iniciante" -> MaterialTheme.colorScheme.tertiary
                        "Intermediário" -> MaterialTheme.colorScheme.secondary
                        "Avançado" -> MaterialTheme.colorScheme.primary
                        else -> MaterialTheme.colorScheme.primary
                    }

                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isChecked) MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)
                            else MaterialTheme.colorScheme.surface
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                            .clickable { onToggleCompleted(onToggleStep, index) }
                            .testTag("diagnostic_step_card_$index")
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            // Checkbox and index
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.padding(top = 2.dp)
                            ) {
                                Checkbox(
                                    checked = isChecked,
                                    onCheckedChange = { onToggleCompleted(onToggleStep, index) },
                                    modifier = Modifier.testTag("diagnostic_step_check_$index")
                                )
                                Text(
                                    text = "PASSO ${index + 1}",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                                )
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            // Step Details
                            Column(modifier = Modifier.weight(1f)) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = step.title,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isChecked) MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                        else MaterialTheme.colorScheme.onSurface
                                    )
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(6.dp))
                                            .background(stepColor.copy(alpha = 0.15f))
                                            .padding(horizontal = 6.dp, vertical = 2.dp)
                                    ) {
                                        Text(
                                            text = step.level.uppercase(),
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = stepColor,
                                            fontSize = 8.sp
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(4.dp))

                                Text(
                                    text = step.instruction,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = if (isChecked) MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                                    else MaterialTheme.colorScheme.onSurface,
                                    lineHeight = 20.sp
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                // Informational Tip Block
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(MaterialTheme.colorScheme.background)
                                        .padding(10.dp)
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.Default.Info,
                                            contentDescription = "Dica",
                                            tint = stepColor,
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = step.tip,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)
                                        )
                                    }
                                }

                                // Interactive diagram for advanced steps
                                if (index == 2 && symptom.id == "sym_dead_phone") {
                                    Spacer(modifier = Modifier.height(12.dp))
                                    MultimeterDiagram()
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

// Checkbox helper callback
private fun onToggleCompleted(onToggleStep: (Int) -> Unit, index: Int) {
    onToggleStep(index)
}

@Composable
fun MultimeterDiagram() {
    Card(
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E222A)),
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp)
            .padding(top = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "ESQUEMÁTICO: Teste de Condução Reversa",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFFC107)
            )

            Spacer(modifier = Modifier.height(6.dp))

            Canvas(modifier = Modifier
                .fillMaxWidth()
                .weight(1f)) {
                val w = size.width
                val h = size.height

                // Ground plane (Terra)
                drawRect(
                    color = Color(0xFF37474F),
                    topLeft = Offset(10f, h - 30f),
                    size = androidx.compose.ui.geometry.Size(60f, 20f)
                )
                // Text for GND
                // Draw Board connections
                drawLine(
                    color = Color.Green,
                    start = Offset(200f, h/2),
                    end = Offset(w - 100f, h/2),
                    strokeWidth = 4f
                )

                // Capacitor on board
                drawRect(
                    color = Color(0xFF8D6E63),
                    topLeft = Offset(w / 2 - 20f, h / 2 - 30f),
                    size = androidx.compose.ui.geometry.Size(40f, 60f)
                )

                // Probe points
                // Red Probe (Ponta Vermelha) connected to GND
                drawCircle(color = Color.Red, radius = 6f, center = Offset(40f, h - 20f))
                drawLine(color = Color.Red, start = Offset(40f, h-20f), end = Offset(80f, h/2 - 20f), strokeWidth = 3f)

                // Black Probe (Ponta Preta) connected to Active Pad
                drawCircle(color = Color.DarkGray, radius = 6f, center = Offset(w/2 + 50f, h/2))
                drawLine(color = Color.White, start = Offset(w/2 + 50f, h/2), end = Offset(w - 80f, h/2 - 20f), strokeWidth = 3f)

                // Multimeter screen representation
                drawRect(color = Color.Black, topLeft = Offset(w/2 - 50f, 5f), size = androidx.compose.ui.geometry.Size(100f, 30f))
                // Draw schematic lines
                drawCircle(color = Color.Yellow, radius = 8f, center = Offset(w/2, 20f), style = Stroke(width = 2f))
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Ponta VERMELHA vai no TERRA (GND) • Ponta PRETA mede o PINO ativo",
                style = MaterialTheme.typography.bodySmall,
                color = Color.LightGray,
                fontSize = 10.sp
            )
        }
    }
}

fun getDiagnosticsList(): List<SymptomDiagnostic> {
    return listOf(
        SymptomDiagnostic(
            id = "sym_dead_phone",
            title = "Celular Não Liga (Totalmente Morto)",
            description = "O aparelho não exibe sinal de carga, não acende, não vibra e não liga.",
            icon = { color -> Icon(Icons.Default.PowerOff, null, tint = color, modifier = Modifier.size(32.dp)) },
            steps = listOf(
                DiagnosticStep(
                    title = "Teste de Reativação e Carga",
                    level = "Iniciante",
                    instruction = "Conecte outro carregador e cabo USB originais e deixe na tomada por 30 minutos. Se a bateria estiver com descarga profunda, ela precisa de tempo para ser reconhecida. Experimente pressionar Power + Volume Down por 15 segundos para forçar o reset do processador.",
                    tip = "Baterias descarregadas abaixo de 3.0V bloqueiam o circuito de carga interno, parecendo que o celular está morto."
                ),
                DiagnosticStep(
                    title = "Medição Física de Tensão",
                    level = "Intermediário",
                    instruction = "Abra o aparelho seguindo as regras de segurança. Desconecte a bateria e meça a tensão nos polos com o multímetro em escala de Tensão Contínua (DCV). Se marcar abaixo de 3.2V, use a fonte de bancada em 4.2V para dar um 'choque' rápido de 5 minutos na bateria até subir para 3.7V.",
                    tip = "A bateria precisa de no mínimo 3.7V para fazer o circuito de boot inicial funcionar e exibir imagem de carregamento."
                ),
                DiagnosticStep(
                    title = "Análise de Corrente na Fonte",
                    level = "Avançado",
                    instruction = "Conecte a placa do celular nos cabos da fonte de bancada ajustada em 4.2V e limite de 2.0A. Verifique se há consumo imediato de corrente antes de apertar o botão power. Se houver (curto primário), use a técnica do breu na linha VBAT ou VPH_PWR para identificar o capacitor em curto-circuito.",
                    tip = "Se após apertar o Power o consumo subir apenas a 50mA e cair a zero, o problema é solda fria na CPU ou PMIC secundário danificado."
                )
            )
        ),
        SymptomDiagnostic(
            id = "sym_not_charging",
            title = "Não Carrega / Conector Defeituoso",
            description = "O celular liga, mas não reconhece o cabo ou mostra carga lenta.",
            icon = { color -> Icon(Icons.Default.BatteryAlert, null, tint = color, modifier = Modifier.size(32.dp)) },
            steps = listOf(
                DiagnosticStep(
                    title = "Limpeza Mecânica de Porta",
                    level = "Iniciante",
                    instruction = "Use uma agulha de precisão ou pinça extrafina com cuidado dentro do conector de carga Tipo-C ou Lightning. Retire fiapos de poeira compactada. Limpe com uma escova de dentes macia umedecida em álcool isopropílico.",
                    tip = "Cerca de 40% dos casos de celulares que 'não carregam' são causados apenas por fiapos de bolso compactados no conector."
                ),
                DiagnosticStep(
                    title = "Verificação de Linha VBUS",
                    level = "Intermediário",
                    instruction = "Insira um cabo USB energizado. Com o multímetro em Tensão Contínua (DC 20V), coloque a ponta preta no terra e meça o ponto de teste correspondente à linha VBUS perto do conector de carga ou no conector FPC da placa-mãe. Deve marcar 5.0 Volts estáveis.",
                    tip = "Se marcar 0V, o conector de carga está quebrado ou dessoldado internamente. Se marcar 5V na sub-placa mas não na principal, troque o flex de ligação."
                ),
                DiagnosticStep(
                    title = "Diagnóstico OVP e Linhas de Dados",
                    level = "Avançado",
                    instruction = "Faça o teste de condução reversa nas trilhas de dados USB (D+ e D-) e canais de configuração CC1/CC2 no conector. Os valores devem rondar 400mV. Se houver linha aberta (OL), a CPU não libera carga rápida. Verifique e substitua o circuito integrado de OVP ou o CI de carga principal.",
                    tip = "O circuito de OVP (Overvoltage Protection) queima com picos de tensão do carregador para salvar a placa principal."
                )
            )
        ),
        SymptomDiagnostic(
            id = "sym_dark_screen",
            title = "Tela Escura (Backlight Queimado)",
            description = "O celular vibra e toca notificações, mas a tela continua totalmente apagada.",
            icon = { color -> Icon(Icons.Default.PhonelinkErase, null, tint = color, modifier = Modifier.size(32.dp)) },
            steps = listOf(
                DiagnosticStep(
                    title = "Teste de Imagem de Fundo (Lanterna)",
                    level = "Iniciante",
                    instruction = "Ligue o celular. Aponte a lanterna de outro celular bem de perto na tela escura. Se você conseguir ver de fundo e muito fraca a imagem ou o logotipo do sistema, a tela está funcionando mas a iluminação traseira (backlight) está queimada.",
                    tip = "Isso descarta falha de processamento e foca o problema estritamente no circuito de luz da tela."
                ),
                DiagnosticStep(
                    title = "Inspeção e Limpeza do FPC",
                    level = "Intermediário",
                    instruction = "Desconecte a bateria. Desencaixe o cabo flex da tela. Use um microscópio ou lupa para verificar se existem pinos pretos (oxidados), pinos amassados ou sinais de curtos causados por umidade no conector FPC da placa. Limpe-o bem com álcool isopropílico.",
                    tip = "Pinos do backlight carregam altas tensões (até 35V) e são os primeiros a queimar ou corroer se entrarem em contato com humidade."
                ),
                DiagnosticStep(
                    title = "Mapeamento do Circuito Boost de Luz",
                    level = "Avançado",
                    instruction = "Utilize o esquema elétrico para mapear a linha de alta tensão do Backlight (LED_ANODE). Faça teste de condução reversa nesse pino. Se der OL, o micro-filtro fusível em série na linha está aberto. Se der 0V, há curto em algum capacitor de filtro. Se as medições estiverem certas, substitua o diodo Schottky ou o CI de backlight.",
                    tip = "O circuito de backlight eleva a tensão da bateria para até 35V para acender os LEDs traseiros da tela LCD."
                )
            )
        )
    );
}
