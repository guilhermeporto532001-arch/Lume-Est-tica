package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Layers
import androidx.compose.material.icons.outlined.Spa
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.PackageItem
import com.example.model.Procedure
import com.example.ui.LumeUiState
import com.example.ui.theme.*

@Composable
fun ProcedimentosScreen(
    uiState: LumeUiState,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedCategory by remember { mutableStateOf("Todos") }
    var showPackagesTab by remember { mutableStateOf(false) }

    val categories = listOf("Todos", "Facial", "Laser & Tecnologia", "Injetáveis", "Corporal")

    val filteredProcedures = uiState.procedures.filter {
        selectedCategory == "Todos" || it.category == selectedCategory
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(LumeCanvas),
        contentPadding = PaddingValues(start = 18.dp, end = 18.dp, top = 8.dp, bottom = 120.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Catálogo & Protocolos",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = LumeNeutralDark
                        )
                    )
                    Text(
                        text = "Procedimentos de alta performance e pacotes terapêuticos",
                        style = MaterialTheme.typography.bodySmall.copy(color = LumeNeutralMuted)
                    )
                }
            }
        }

        // Pill Switcher: Procedimentos vs Pacotes
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
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(CircleShape)
                            .background(if (!showPackagesTab) LumePrimarySage else Color.Transparent)
                            .clickable { showPackagesTab = false }
                            .padding(vertical = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Procedimentos",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = if (!showPackagesTab) FontWeight.Bold else FontWeight.Medium,
                                color = if (!showPackagesTab) Color.White else LumeNeutralDark
                            )
                        )
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(CircleShape)
                            .background(if (showPackagesTab) LumePrimarySage else Color.Transparent)
                            .clickable { showPackagesTab = true }
                            .padding(vertical = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Pacotes de Sessões",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = if (showPackagesTab) FontWeight.Bold else FontWeight.Medium,
                                color = if (showPackagesTab) Color.White else LumeNeutralDark
                            )
                        )
                    }
                }
            }
        }

        if (!showPackagesTab) {
            // Category Filters
            item {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(categories) { cat ->
                        val isSelected = (cat == selectedCategory)
                        Surface(
                            shape = CircleShape,
                            color = if (isSelected) LumePrimarySage else Color.White,
                            border = BorderStroke(
                                1.dp,
                                if (isSelected) LumePrimarySage else Color.Black.copy(alpha = 0.05f)
                            ),
                            modifier = Modifier.clickable { selectedCategory = cat }
                        ) {
                            Text(
                                text = cat,
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    color = if (isSelected) Color.White else LumeNeutralDark
                                ),
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                            )
                        }
                    }
                }
            }

            items(filteredProcedures) { proc ->
                ProcedureCardItem(proc)
            }
        } else {
            // Packages List
            items(uiState.packages) { pkg ->
                PackageCardItem(pkg)
            }
        }
    }
}

@Composable
private fun ProcedureCardItem(procedure: Procedure) {
    val (bgColor, tint) = when (procedure.category) {
        "Facial" -> Pair(LumeRosePastel, LumeRoseText)
        "Laser & Tecnologia" -> Pair(LumePurplePastel, LumePurpleText)
        "Injetáveis" -> Pair(LumeSageLight, LumeSageDark)
        else -> Pair(LumeChampagneSand, LumeChampagneText)
    }

    Card(
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.cardColors(containerColor = bgColor),
        border = BorderStroke(1.dp, tint.copy(alpha = 0.15f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.95f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Spa,
                            contentDescription = null,
                            tint = tint,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Column {
                        Text(
                            text = procedure.name,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = LumeNeutralDark
                            )
                        )
                        Text(
                            text = "${procedure.category} • ${procedure.durationMinutes} minutos",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = tint,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.95f))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "R$ ${"%.2f".format(procedure.price)}",
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.ExtraBold,
                            color = LumeNeutralDark
                        )
                    )
                }
            }

            Text(
                text = procedure.description,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = LumeNeutralDark.copy(alpha = 0.85f),
                    lineHeight = 18.sp
                )
            )

            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Color.White.copy(alpha = 0.8f),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Cuidados: ${procedure.postCare}",
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 11.sp,
                        color = tint
                    ),
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}

@Composable
private fun PackageCardItem(pkg: PackageItem) {
    Card(
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.cardColors(containerColor = LumeChampagneSand),
        border = BorderStroke(1.dp, LumeChampagneGold.copy(alpha = 0.3f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.95f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Layers,
                            contentDescription = null,
                            tint = LumeChampagneText,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Column {
                        Text(
                            text = pkg.title,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = LumeNeutralDark
                            )
                        )
                        Text(
                            text = "Cliente: ${pkg.clientName}",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = LumeChampagneText,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.9f))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "R$ ${"%.2f".format(pkg.price)}",
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = LumeChampagneText
                        )
                    )
                }
            }

            // Progress Bar for Sessions
            val progressFrac = pkg.completedSessions.toFloat() / pkg.totalSessions.toFloat()
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${pkg.completedSessions} de ${pkg.totalSessions} sessões concluídas",
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = LumeNeutralDark
                    )
                )
                Text(
                    text = "Válido até ${pkg.expiryDate}",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = LumeNeutralMuted
                    )
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.7f))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(progressFrac.coerceIn(0f, 1f))
                        .clip(CircleShape)
                        .background(LumeChampagneGold)
                )
            }
        }
    }
}
