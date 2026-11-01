package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Warning
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
import com.example.model.AnamneseForm
import com.example.model.ClinicalRecord
import com.example.ui.LumeUiState
import com.example.ui.theme.*

@Composable
fun ProntuarioScreen(
    uiState: LumeUiState,
    onOpenNewAnamnese: () -> Unit,
    onSaveClinicalRecord: (ClinicalRecord) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedSection by remember { mutableStateOf("Prontuários") } // "Prontuários" or "Anamneses"

    // State for creating a new session record directly
    var selectedClientName by remember { mutableStateOf("Mariana Silveira") }
    var procedureName by remember { mutableStateOf("Limpeza com Ledterapia") }
    var treatedArea by remember { mutableStateOf("Face completa e mento") }
    var productLot by remember { mutableStateOf("LT-2024-998B") }
    var technicalParams by remember { mutableStateOf("LED Âmbar 590nm, 4.5 J/cm², 15 min") }
    var postCare by remember { mutableStateOf("Água termal e protetor solar FPS 50+ físico") }
    var clinicalNotes by remember { mutableStateOf("Pele com excelente cicatrização pós-extração.") }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(LumeCanvas),
        contentPadding = PaddingValues(start = 18.dp, end = 18.dp, top = 8.dp, bottom = 120.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Title
        item {
            Column {
                Text(
                    text = "Prontuário & Anamnese",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = LumeNeutralDark
                    )
                )
                Text(
                    text = "Registros clínicos, fichas digitais e segurança terapêutica",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = LumeNeutralMuted
                    )
                )
            }
        }

        // Capsule Switcher (Prontuários vs Anamneses)
        item {
            Surface(
                shape = CircleShape,
                color = Color.White,
                border = BorderStroke(1.dp, Color.Black.copy(alpha = 0.04f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                ) {
                    val tabs = listOf("Prontuários", "Fichas de Anamnese")
                    tabs.forEach { tab ->
                        val isSelected = (selectedSection == tab)
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(CircleShape)
                                .background(if (isSelected) LumePrimarySage else Color.Transparent)
                                .clickable { selectedSection = tab }
                                .padding(vertical = 10.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = tab,
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    color = if (isSelected) Color.White else LumeNeutralDark
                                )
                            )
                        }
                    }
                }
            }
        }

        if (selectedSection == "Prontuários") {
            // New Clinical Session Card
            item {
                Card(
                    shape = RoundedCornerShape(26.dp),
                    colors = CardDefaults.cardColors(containerColor = LumeSurfacePorcelain),
                    border = BorderStroke(1.dp, LumeSageSoft.copy(alpha = 0.2f)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(34.dp)
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(LumeSageLight),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.Description,
                                        contentDescription = null,
                                        tint = LumeSageDark,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                                Text(
                                    text = "Evolução do Atendimento",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = LumeNeutralDark
                                    )
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(LumeRosePastel)
                                    .padding(horizontal = 8.dp, vertical = 3.dp)
                            ) {
                                Text(
                                    text = "Sessão Atual",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = LumeRoseText,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }
                        }

                        // Form Inputs for Clinical Record
                        OutlinedTextField(
                            value = selectedClientName,
                            onValueChange = { selectedClientName = it },
                            label = { Text("Paciente") },
                            shape = CircleShape,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = LumeSurfacePorcelain,
                                unfocusedContainerColor = LumeSurfacePorcelain,
                                focusedBorderColor = LumeSageSoft
                            ),
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = procedureName,
                            onValueChange = { procedureName = it },
                            label = { Text("Procedimento Realizado") },
                            shape = CircleShape,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = LumeSurfacePorcelain,
                                unfocusedContainerColor = LumeSurfacePorcelain,
                                focusedBorderColor = LumeSageSoft
                            ),
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            OutlinedTextField(
                                value = treatedArea,
                                onValueChange = { treatedArea = it },
                                label = { Text("Região Tratada") },
                                shape = CircleShape,
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedContainerColor = LumeSurfacePorcelain,
                                    unfocusedContainerColor = LumeSurfacePorcelain,
                                    focusedBorderColor = LumeSageSoft
                                ),
                                singleLine = true,
                                modifier = Modifier.weight(1f)
                            )
                            OutlinedTextField(
                                value = productLot,
                                onValueChange = { productLot = it },
                                label = { Text("Lote / Insumo") },
                                shape = CircleShape,
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedContainerColor = LumeSurfacePorcelain,
                                    unfocusedContainerColor = LumeSurfacePorcelain,
                                    focusedBorderColor = LumeSageSoft
                                ),
                                singleLine = true,
                                modifier = Modifier.weight(1f)
                            )
                        }

                        OutlinedTextField(
                            value = technicalParams,
                            onValueChange = { technicalParams = it },
                            label = { Text("Parâmetros Técnicos (Dose, Energia, Passadas)") },
                            shape = CircleShape,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = LumeSurfacePorcelain,
                                unfocusedContainerColor = LumeSurfacePorcelain,
                                focusedBorderColor = LumeSageSoft
                            ),
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = postCare,
                            onValueChange = { postCare = it },
                            label = { Text("Orientações Pós-Procedimento") },
                            shape = RoundedCornerShape(16.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = LumeSurfacePorcelain,
                                unfocusedContainerColor = LumeSurfacePorcelain,
                                focusedBorderColor = LumeSageSoft
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Button(
                            onClick = {
                                val record = ClinicalRecord(
                                    id = "rec_${System.currentTimeMillis()}",
                                    appointmentId = "a1",
                                    clientId = "c1",
                                    clientName = selectedClientName,
                                    procedureName = procedureName,
                                    date = "21/10/2024",
                                    time = "10:30",
                                    treatedArea = treatedArea,
                                    productsUsed = "Emoliente & LED Âmbar",
                                    batchNumber = productLot,
                                    technicalParams = technicalParams,
                                    clinicalNotes = clinicalNotes,
                                    postProcedureCare = postCare,
                                    returnRecommendationDays = 30
                                )
                                onSaveClinicalRecord(record)
                            },
                            shape = CircleShape,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = LumePrimarySage,
                                contentColor = Color.White
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .testTag("salvar_prontuario_btn")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Salvar e Concluir Atendimento",
                                style = MaterialTheme.typography.labelLarge.copy(
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }
                }
            }

            // Existing Clinical Records List
            item {
                Text(
                    text = "Histórico de Atendimentos Salvos",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = LumeNeutralDark
                    )
                )
            }

            items(uiState.clinicalRecords) { record ->
                Card(
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = LumeSageLight),
                    border = BorderStroke(1.dp, LumeSageDark.copy(alpha = 0.1f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "${record.clientName} • ${record.procedureName}",
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = LumeSageDark
                                )
                            )
                            Text(
                                text = record.date,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = LumeSageDark.copy(alpha = 0.7f)
                                )
                            )
                        }
                        Text(
                            text = "Região: ${record.treatedArea} | Lote: ${record.batchNumber}",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = LumeNeutralDark
                            )
                        )
                        Text(
                            text = "Parâmetros: ${record.technicalParams}",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = LumeNeutralMuted
                            )
                        )
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = Color.White.copy(alpha = 0.85f),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Cuidados: ${record.postProcedureCare}",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 11.sp,
                                    color = LumeSageDark
                                ),
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }
                }
            }
        } else {
            // Anamnese Section
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Fichas de Anamnese Digitais",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = LumeNeutralDark
                        )
                    )

                    Button(
                        onClick = onOpenNewAnamnese,
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = LumePrimarySage,
                            contentColor = Color.White
                        ),
                        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
                        modifier = Modifier.testTag("nova_anamnese_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Nova Ficha",
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
            }

            items(uiState.anamneses) { anamnese ->
                Card(
                    shape = RoundedCornerShape(26.dp),
                    colors = CardDefaults.cardColors(containerColor = LumeSurfacePorcelain),
                    border = BorderStroke(1.dp, Color.Black.copy(alpha = 0.04f)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = anamnese.clientName,
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = LumeNeutralDark
                                    )
                                )
                                Text(
                                    text = "Avaliação realizada em ${anamnese.date}",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = LumeNeutralMuted
                                    )
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(LumeSageLight)
                                    .padding(horizontal = 10.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = "Fitzpatrick ${anamnese.fitzpatrickSkinType}",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = LumeSageDark
                                    )
                                )
                            }
                        }

                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = LumeCanvas,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(10.dp),
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    text = "Queixa: ${anamnese.mainComplaint}",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        fontWeight = FontWeight.Medium,
                                        color = LumeNeutralDark
                                    )
                                )
                                Text(
                                    text = "Objetivo: ${anamnese.aestheticGoals}",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = LumeNeutralMuted
                                    )
                                )
                            }
                        }

                        // Allergies warning if any
                        if (anamnese.allergies.isNotBlank() && anamnese.allergies != "Nenhuma alergia relatada") {
                            Surface(
                                shape = RoundedCornerShape(14.dp),
                                color = LumeRosePastel,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier.padding(10.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.Warning,
                                        contentDescription = "Alerta",
                                        tint = LumeTerracottaBlush,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Text(
                                        text = "Alergias: ${anamnese.allergies}",
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            color = LumeRoseText,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    )
                                }
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.CheckCircle,
                                    contentDescription = null,
                                    tint = LumePrimarySage,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = "Termo assinado digitalmente",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = LumePrimarySage,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                )
                            }

                            Text(
                                text = "Sensibilidade: ${anamnese.skinSensitivity}",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = LumeNeutralMuted
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}
