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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.PlayArrow
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
import com.example.model.Appointment
import com.example.model.AppointmentStatus
import com.example.ui.LumeUiState
import com.example.ui.components.DatePillSelector
import com.example.ui.theme.*

@Composable
fun AgendaScreen(
    uiState: LumeUiState,
    onSelectDay: (Int) -> Unit,
    onFilterChange: (String) -> Unit,
    onAppointmentClick: (Appointment) -> Unit,
    onNewAppointmentClick: () -> Unit,
    onStatusChange: (String, AppointmentStatus) -> Unit,
    modifier: Modifier = Modifier
) {
    val filters = listOf("Todos", "Em andamento", "Confirmados", "Concluídos", "Aguardando")

    val filteredAppointments = uiState.appointments.filter { appt ->
        when (uiState.agendaFilterStatus) {
            "Em andamento" -> appt.status == AppointmentStatus.EM_ANDAMENTO
            "Confirmados" -> appt.status == AppointmentStatus.CONFIRMADO
            "Concluídos" -> appt.status == AppointmentStatus.CONCLUIDO
            "Aguardando" -> appt.status == AppointmentStatus.AGUARDANDO
            else -> true
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(LumeCanvas),
        contentPadding = PaddingValues(start = 18.dp, end = 18.dp, top = 8.dp, bottom = 120.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Date Pill Selector
        item {
            DatePillSelector(
                selectedDay = uiState.selectedDay,
                onDaySelected = onSelectDay,
                monthYearText = uiState.selectedMonthYear
            )
        }

        // Header Title with Action Button
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Agenda Clínica",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = LumeNeutralDark
                        )
                    )
                    Text(
                        text = "${filteredAppointments.size} atendimentos programados",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = LumeNeutralMuted
                        )
                    )
                }

                Button(
                    onClick = onNewAppointmentClick,
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = LumePrimarySage,
                        contentColor = Color.White
                    ),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    modifier = Modifier.testTag("agenda_novo_agendamento_btn")
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Novo",
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }

        // Filter Chips Row
        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(filters) { filter ->
                    val isSelected = (filter == uiState.agendaFilterStatus)
                    Surface(
                        shape = CircleShape,
                        color = if (isSelected) LumePrimarySage else Color.White,
                        border = BorderStroke(
                            1.dp,
                            if (isSelected) LumePrimarySage else Color.Black.copy(alpha = 0.06f)
                        ),
                        modifier = Modifier
                            .clickable { onFilterChange(filter) }
                            .testTag("agenda_filter_$filter")
                    ) {
                        Text(
                            text = filter,
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

        // Timeline Schedule Items
        if (filteredAppointments.isEmpty()) {
            item {
                Card(
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, Color.Black.copy(alpha = 0.04f)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 20.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(28.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.CalendarMonth,
                            contentDescription = null,
                            tint = LumeSageSoft,
                            modifier = Modifier.size(40.dp)
                        )
                        Text(
                            text = "Nenhum atendimento neste filtro",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = LumeNeutralDark
                            )
                        )
                        Text(
                            text = "Selecione outro dia ou limpe os filtros para visualizar a grade completa.",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = LumeNeutralMuted
                            )
                        )
                    }
                }
            }
        } else {
            items(filteredAppointments) { appt ->
                AgendaTimelineCard(
                    appointment = appt,
                    onClick = { onAppointmentClick(appt) },
                    onStartSession = { onStatusChange(appt.id, AppointmentStatus.EM_ANDAMENTO) },
                    onCompleteSession = { onStatusChange(appt.id, AppointmentStatus.CONCLUIDO) }
                )
            }
        }
    }
}

@Composable
private fun AgendaTimelineCard(
    appointment: Appointment,
    onClick: () -> Unit,
    onStartSession: () -> Unit,
    onCompleteSession: () -> Unit
) {
    val (bgColor, textColor, statusText) = when (appointment.status) {
        AppointmentStatus.EM_ANDAMENTO -> Triple(LumeRosePastel, LumeRoseText, "Em andamento")
        AppointmentStatus.CONFIRMADO -> Triple(LumeChampagneSand, LumeChampagneText, "Confirmado")
        AppointmentStatus.AGUARDANDO -> Triple(LumePurplePastel, LumePurpleText, "Aguardando")
        AppointmentStatus.CONCLUIDO -> Triple(LumeSageLight, LumeSageDark, "Concluído")
        AppointmentStatus.CANCELADO -> Triple(Color(0xFFFDE8E8), Color(0xFF9B1C1C), "Cancelado")
    }

    Card(
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.cardColors(containerColor = bgColor),
        border = BorderStroke(1.dp, textColor.copy(alpha = 0.15f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Time Badge & Status
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
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.White.copy(alpha = 0.95f))
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = appointment.time,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = LumeNeutralDark
                            )
                        )
                    }

                    Text(
                        text = "${appointment.room} • ${appointment.durationMinutes} min",
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.Medium,
                            color = textColor
                        )
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.92f))
                        .padding(horizontal = 10.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = statusText,
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = textColor
                        )
                    )
                }
            }

            // Client Info & Procedure
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.95f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = appointment.clientInitials,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = textColor
                        )
                    )
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = appointment.clientName,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = LumeNeutralDark
                        )
                    )
                    Text(
                        text = appointment.procedureName,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = textColor.copy(alpha = 0.9f),
                            fontWeight = FontWeight.Medium
                        )
                    )
                }
            }

            // Quick Actions Bar
            HorizontalDivider(
                color = textColor.copy(alpha = 0.12f),
                thickness = 1.dp
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "R$ ${"%.2f".format(appointment.price)} • ${appointment.paymentMethod}",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = textColor
                    )
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    if (appointment.status != AppointmentStatus.EM_ANDAMENTO && appointment.status != AppointmentStatus.CONCLUIDO) {
                        Button(
                            onClick = onStartSession,
                            shape = CircleShape,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White.copy(alpha = 0.95f),
                                contentColor = LumePrimarySage
                            ),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                            modifier = Modifier.height(32.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.PlayArrow,
                                contentDescription = null,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Iniciar",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }

                    if (appointment.status == AppointmentStatus.EM_ANDAMENTO) {
                        Button(
                            onClick = onCompleteSession,
                            shape = CircleShape,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = LumePrimarySage,
                                contentColor = Color.White
                            ),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                            modifier = Modifier.height(32.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.CheckCircle,
                                contentDescription = null,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Concluir",
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
}
