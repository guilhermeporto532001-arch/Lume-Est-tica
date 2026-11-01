package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Inventory2
import androidx.compose.material.icons.outlined.WarningAmber
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.InventoryItem
import com.example.ui.LumeUiState
import com.example.ui.theme.*

@Composable
fun EstoqueScreen(
    uiState: LumeUiState,
    onReplenish: (String, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val lowStockCount = uiState.inventory.count { it.isLowStock }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(LumeCanvas),
        contentPadding = PaddingValues(start = 18.dp, end = 18.dp, top = 8.dp, bottom = 120.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Column {
                Text(
                    text = "Controle de Estoque & Insumos",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = LumeNeutralDark
                    )
                )
                Text(
                    text = "Rastreabilidade de lotes, datas de validade e alertas de reposição",
                    style = MaterialTheme.typography.bodySmall.copy(color = LumeNeutralMuted)
                )
            }
        }

        // Summary Alert Card
        if (lowStockCount > 0) {
            item {
                Card(
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF7F5)),
                    border = BorderStroke(1.dp, LumeTerracottaBlush.copy(alpha = 0.35f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(LumeTerracottaBlush.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.WarningAmber,
                                contentDescription = null,
                                tint = LumeTerracottaBlush,
                                modifier = Modifier.size(22.dp)
                            )
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Atenção: $lowStockCount produto(s) abaixo do estoque mínimo",
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = LumeNeutralDark
                                )
                            )
                            Text(
                                text = "Programe a reposição com os distribuidores oficiais.",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = LumeNeutralMuted
                                )
                            )
                        }
                    }
                }
            }
        }

        // Inventory Items List
        items(uiState.inventory) { item ->
            InventoryItemCard(
                item = item,
                onReplenishOne = { onReplenish(item.id, 1) },
                onReplenishFive = { onReplenish(item.id, 5) }
            )
        }
    }
}

@Composable
private fun InventoryItemCard(
    item: InventoryItem,
    onReplenishOne: () -> Unit,
    onReplenishFive: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.cardColors(containerColor = LumeSurfacePorcelain),
        border = BorderStroke(
            1.dp,
            if (item.isLowStock) LumeTerracottaBlush.copy(alpha = 0.4f) else Color.Black.copy(alpha = 0.04f)
        ),
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
                verticalAlignment = Alignment.Top
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(if (item.isLowStock) LumeRosePastel else LumeSageLight),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Inventory2,
                            contentDescription = null,
                            tint = if (item.isLowStock) LumeRoseText else LumeSageDark,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Column {
                        Text(
                            text = item.name,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = LumeNeutralDark
                            )
                        )
                        Text(
                            text = "${item.category} • Fornecedor: ${item.supplier}",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = LumeNeutralMuted
                            )
                        )
                    }
                }

                // Status Chip
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(
                            if (item.isLowStock) LumeRosePastel else LumeSageLight
                        )
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = if (item.isLowStock) "Baixo Estoque" else "Disponível",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = if (item.isLowStock) LumeRoseText else LumeSageDark
                        )
                    )
                }
            }

            // Specs row (Lote, Validade, Mínimo)
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = LumeCanvas,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Lote: ${item.batchLot}",
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontSize = 11.sp,
                            color = LumeNeutralDark
                        )
                    )
                    Text(
                        text = "Validade: ${item.expiryDate}",
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontSize = 11.sp,
                            color = LumeNeutralDark
                        )
                    )
                    Text(
                        text = "Mínimo: ${item.minThreshold}",
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontSize = 11.sp,
                            color = LumeNeutralMuted
                        )
                    )
                }
            }

            // Bottom row: Quantity counter & Quick Replenish buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = item.quantity.toString(),
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.ExtraBold,
                            color = if (item.isLowStock) Color(0xFFC53030) else LumeNeutralDark
                        )
                    )
                    Text(
                        text = item.unit,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = LumeNeutralMuted,
                            fontWeight = FontWeight.Medium
                        ),
                        modifier = Modifier.padding(bottom = 2.dp)
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(
                        onClick = onReplenishOne,
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = LumeSageLight,
                            contentColor = LumeSageDark
                        ),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                        modifier = Modifier
                            .height(32.dp)
                            .testTag("repor_1_${item.id}")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                            modifier = Modifier.size(12.dp)
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(
                            text = "1 un",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }

                    Button(
                        onClick = onReplenishFive,
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = LumePrimarySage,
                            contentColor = Color.White
                        ),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                        modifier = Modifier
                            .height(32.dp)
                            .testTag("repor_5_${item.id}")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                            modifier = Modifier.size(12.dp)
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(
                            text = "+5 lote",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
            }
        }
    }
}
