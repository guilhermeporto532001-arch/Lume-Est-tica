package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material.icons.outlined.*
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
import com.example.model.ActiveSubSection
import com.example.model.Appointment
import com.example.model.NavigationTab
import com.example.ui.LumeUiState
import com.example.ui.components.*
import com.example.ui.theme.*

@Composable
fun HomeScreen(
    uiState: LumeUiState,
    onSelectDay: (Int) -> Unit,
    onOpenSchedule: () -> Unit,
    onAppointmentClick: (Appointment) -> Unit,
    onNavigateTab: (NavigationTab) -> Unit,
    onOpenSubSection: (ActiveSubSection) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(LumeCanvas),
        contentPadding = PaddingValues(start = 18.dp, end = 18.dp, top = 8.dp, bottom = 120.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        // 1. Date Pill Selector (Reference Style)
        item {
            DatePillSelector(
                selectedDay = uiState.selectedDay,
                onDaySelected = onSelectDay,
                monthYearText = uiState.selectedMonthYear
            )
        }

        // 2. Metric Cards Side by Side (Soft Vivid Pastels)
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Card 1: Pastel Blue (#E8F1FC) - Atendimentos Hoje
                MetricCard(
                    tag = "HOJE",
                    title = "ATENDIMENTOS",
                    value = "8",
                    unit = "agendados",
                    progress = 0.65f,
                    icon = Icons.Outlined.CalendarToday,
                    containerColor = LumeBluePastel,
                    textColor = LumeBlueText,
                    progressColor = LumeBlueText,
                    modifier = Modifier.weight(1f)
                )

                // Card 2: Pastel Sage (#E8F8F0) - Meta Semanal
                MetricCard(
                    tag = "META",
                    title = "SEMANAL",
                    value = "82%",
                    unit = "concluído",
                    progress = 0.82f,
                    icon = Icons.Outlined.TrendingUp,
                    containerColor = LumeSageLight,
                    textColor = LumeSageDark,
                    progressColor = LumePrimarySage,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // 3. Quick Action Banner: Novo Atendimento
        item {
            QuickActionScheduleBanner(
                onScheduleClick = onOpenSchedule
            )
        }

        // 4. Section: Próximos Atendimentos Header
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Próximos Atendimentos",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = LumeNeutralDark,
                            fontSize = 16.sp
                        )
                    )
                    Box(
                        modifier = Modifier
                            .size(22.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFE2E8F0)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = uiState.appointments.take(3).size.toString(),
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = LumeNeutralMuted,
                                fontSize = 11.sp
                            )
                        )
                    }
                }

                TextButton(
                    onClick = { onNavigateTab(NavigationTab.AGENDA) },
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        text = "Ver agenda",
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = LumePrimarySage
                        )
                    )
                }
            }
        }

        // 5. List of Next Appointments (Pastel Cards)
        val nextAppointments = uiState.appointments.take(3)
        items(nextAppointments.size) { index ->
            val appt = nextAppointments[index]
            AppointmentItemCard(
                appointment = appt,
                onClick = { onAppointmentClick(appt) }
            )
        }

        // 6. Resumo Financeiro & Faturamento do Mês
        item {
            Spacer(modifier = Modifier.height(4.dp))
            Card(
                shape = RoundedCornerShape(26.dp),
                colors = CardDefaults.cardColors(containerColor = LumeSurfacePorcelain),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                border = BorderStroke(1.dp, Color.Black.copy(alpha = 0.04f)),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onOpenSubSection(ActiveSubSection.FINANCEIRO) }
            ) {
                Column(
                    modifier = Modifier.padding(18.dp)
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
                                    .size(36.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(LumeChampagneSand),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.AccountBalanceWallet,
                                    contentDescription = null,
                                    tint = LumeChampagneText,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                            Column {
                                Text(
                                    text = "Faturamento do Mês",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = LumeNeutralDark
                                    )
                                )
                                Text(
                                    text = "R$ 42.850 recebidos • 92% da meta",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = LumeNeutralMuted
                                    )
                                )
                            }
                        }

                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null,
                            tint = LumeNeutralMuted,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Soft Bars Graphic representation
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(34.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        val barValues = listOf(0.4f, 0.65f, 0.5f, 0.85f, 0.7f, 0.95f, 0.6f)
                        val days = listOf("S1", "S2", "S3", "S4", "S5", "S6", "S7")
                        barValues.forEachIndexed { idx, frac ->
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Bottom,
                                modifier = Modifier.weight(1f)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .width(18.dp)
                                        .fillMaxHeight(frac)
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(if (idx == 5) LumePrimarySage else LumeSageLight)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = days[idx],
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontSize = 9.sp,
                                        color = LumeNeutralMuted
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }

        // 7. Alertas de Insumos & Retornos
        item {
            Card(
                shape = RoundedCornerShape(26.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF9F5)),
                border = BorderStroke(1.dp, LumeTerracottaBlush.copy(alpha = 0.25f)),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onOpenSubSection(ActiveSubSection.ESTOQUE) }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(LumeTerracottaBlush.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.WarningAmber,
                            contentDescription = "Alerta",
                            tint = LumeTerracottaBlush,
                            modifier = Modifier.size(22.dp)
                        )
                    }

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "Alerta de Estoque Clínico",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = LumeNeutralDark
                            )
                        )
                        Text(
                            text = "Ácido Hialurônico Ultra Deep (apenas 2 seringas restantes)",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = LumeNeutralMuted
                            )
                        )
                    }

                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(LumeTerracottaBlush.copy(alpha = 0.15f))
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "Repor",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFC53030)
                            )
                        )
                    }
                }
            }
        }

        // 8. Quick Shortcuts Pills (Procedimentos, Pacotes, Financeiro, Estoque)
        item {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "Acesso Rápido aos Módulos",
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = LumeNeutralDark
                    ),
                    modifier = Modifier.padding(horizontal = 4.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    ShortcutPill(
                        icon = Icons.Outlined.Spa,
                        label = "Procedimentos",
                        containerColor = LumeSageLight,
                        tint = LumeSageDark,
                        onClick = { onOpenSubSection(ActiveSubSection.PROCEDIMENTOS) },
                        modifier = Modifier.weight(1f)
                    )
                    ShortcutPill(
                        icon = Icons.Outlined.Layers,
                        label = "Pacotes",
                        containerColor = LumeChampagneSand,
                        tint = LumeChampagneText,
                        onClick = { onOpenSubSection(ActiveSubSection.PACOTES) },
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    ShortcutPill(
                        icon = Icons.Outlined.Inventory2,
                        label = "Estoque Insumos",
                        containerColor = LumePurplePastel,
                        tint = LumePurpleText,
                        onClick = { onOpenSubSection(ActiveSubSection.ESTOQUE) },
                        modifier = Modifier.weight(1f)
                    )
                    ShortcutPill(
                        icon = Icons.Outlined.Assessment,
                        label = "Relatórios",
                        containerColor = LumeBluePastel,
                        tint = LumeBlueText,
                        onClick = { onOpenSubSection(ActiveSubSection.RELATORIOS) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
private fun ShortcutPill(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    containerColor: Color,
    tint: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = containerColor,
        border = BorderStroke(1.dp, tint.copy(alpha = 0.15f)),
        modifier = modifier
            .height(52.dp)
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = tint,
                modifier = Modifier.size(18.dp)
            )
            Text(
                text = label,
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = tint,
                    fontSize = 12.sp
                )
            )
        }
    }
}
