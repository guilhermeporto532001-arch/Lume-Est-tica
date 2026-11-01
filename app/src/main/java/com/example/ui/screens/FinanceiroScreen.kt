package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBalanceWallet
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.FinancialTransaction
import com.example.ui.LumeUiState
import com.example.ui.theme.*

@Composable
fun FinanceiroScreen(
    uiState: LumeUiState,
    modifier: Modifier = Modifier
) {
    val totalReceived = uiState.transactions.filter { it.isIncome && it.status == "Recebido" }.sumOf { it.amount }
    val totalPending = uiState.transactions.filter { it.isIncome && it.status == "Pendente" }.sumOf { it.amount }
    val totalExpenses = uiState.transactions.filter { !it.isIncome }.sumOf { it.amount }
    val netBalance = totalReceived - totalExpenses

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
                    text = "Painel Financeiro",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = LumeNeutralDark
                    )
                )
                Text(
                    text = "Fluxo de caixa, recebíveis e conciliação de procedimentos",
                    style = MaterialTheme.typography.bodySmall.copy(color = LumeNeutralMuted)
                )
            }
        }

        // 2x2 Metric Cards Grid
        item {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    FinancialMetricCard(
                        title = "VALORES RECEBIDOS",
                        amount = "R$ ${"%.2f".format(totalReceived)}",
                        status = "Confirmado em Caixa",
                        containerColor = LumeSageLight,
                        textColor = LumeSageDark,
                        modifier = Modifier.weight(1f)
                    )
                    FinancialMetricCard(
                        title = "PAGAMENTOS PENDENTES",
                        amount = "R$ ${"%.2f".format(totalPending)}",
                        status = "A receber",
                        containerColor = LumeChampagneSand,
                        textColor = LumeChampagneText,
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    FinancialMetricCard(
                        title = "DESPESAS & INSUMOS",
                        amount = "R$ ${"%.2f".format(totalExpenses)}",
                        status = "Saídas pagas",
                        containerColor = LumeRosePastel,
                        textColor = LumeRoseText,
                        modifier = Modifier.weight(1f)
                    )
                    FinancialMetricCard(
                        title = "SALDO LÍQUIDO",
                        amount = "R$ ${"%.2f".format(netBalance)}",
                        status = "Resultado do período",
                        containerColor = LumeBluePastel,
                        textColor = LumeBlueText,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // Weekly Cash Flow Chart representation
        item {
            Card(
                shape = RoundedCornerShape(26.dp),
                colors = CardDefaults.cardColors(containerColor = LumeSurfacePorcelain),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                border = BorderStroke(1.dp, Color.Black.copy(alpha = 0.04f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Fluxo Semanal de Entradas",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = LumeNeutralDark
                        )
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        val days = listOf("Seg", "Ter", "Qua", "Qui", "Sex", "Sáb")
                        val fractions = listOf(0.75f, 0.45f, 0.9f, 0.6f, 0.85f, 0.5f)

                        days.forEachIndexed { i, day ->
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Bottom,
                                modifier = Modifier.weight(1f)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .width(22.dp)
                                        .fillMaxHeight(fractions[i])
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(if (i == 2) LumePrimarySage else LumeSageLight)
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = day,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = LumeNeutralMuted,
                                        fontSize = 10.sp
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }

        // Transactions Header
        item {
            Text(
                text = "Transações Recentes",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = LumeNeutralDark
                )
            )
        }

        items(uiState.transactions) { tx ->
            TransactionRowItem(tx)
        }
    }
}

@Composable
private fun FinancialMetricCard(
    title: String,
    amount: String,
    status: String,
    containerColor: Color,
    textColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor),
        border = BorderStroke(1.dp, textColor.copy(alpha = 0.12f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = textColor.copy(alpha = 0.75f),
                    fontSize = 9.sp
                )
            )
            Text(
                text = amount,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.ExtraBold,
                    color = textColor
                )
            )
            Text(
                text = status,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = textColor.copy(alpha = 0.85f),
                    fontSize = 11.sp
                )
            )
        }
    }
}

@Composable
private fun TransactionRowItem(tx: FinancialTransaction) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = LumeSurfacePorcelain),
        border = BorderStroke(1.dp, Color.Black.copy(alpha = 0.04f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(if (tx.isIncome) LumeSageLight else LumeRosePastel),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (tx.status == "Recebido" || tx.status == "Pago")
                            Icons.Outlined.CheckCircle else Icons.Outlined.Schedule,
                        contentDescription = null,
                        tint = if (tx.isIncome) LumeSageDark else LumeRoseText,
                        modifier = Modifier.size(18.dp)
                    )
                }

                Column {
                    Text(
                        text = tx.clientName,
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = LumeNeutralDark
                        )
                    )
                    Text(
                        text = "${tx.description} • ${tx.paymentMethod}",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = LumeNeutralMuted,
                            fontSize = 11.sp
                        )
                    )
                }
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "${if (tx.isIncome) "+" else "-"} R$ ${"%.2f".format(tx.amount)}",
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = if (tx.isIncome) LumeSageDark else Color(0xFFC53030)
                    )
                )
                Text(
                    text = tx.status,
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = if (tx.status == "Recebido") LumeSageDark else LumeChampagneText,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 10.sp
                    )
                )
            }
        }
    }
}
