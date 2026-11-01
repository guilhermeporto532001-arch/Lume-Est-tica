package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.Appointment
import com.example.model.AppointmentStatus
import com.example.model.NavigationTab
import com.example.ui.theme.*

import coil.compose.AsyncImage

@Composable
fun LumeTopHeader(
    doctorName: String = "Dra. Mariana",
    doctorPhotoUri: String? = null,
    dateText: String = "Segunda, 21 de Outubro",
    onProfilePhotoClick: () -> Unit = {},
    onSearchClick: () -> Unit = {},
    onNotificationClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Surface(
        color = LumeCanvas.copy(alpha = 0.95f),
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Profile & Greeting
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .clickable { onProfilePhotoClick() }
                        .testTag("header_profile_photo_btn")
                ) {
                    if (doctorPhotoUri != null) {
                        AsyncImage(
                            model = doctorPhotoUri,
                            contentDescription = "Foto da $doctorName",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(46.dp)
                                .clip(CircleShape)
                                .border(2.dp, Color.White, CircleShape)
                                .shadow(2.dp, CircleShape)
                        )
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.dra_camila),
                            contentDescription = "Foto da $doctorName",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(46.dp)
                                .clip(CircleShape)
                                .border(2.dp, Color.White, CircleShape)
                                .shadow(2.dp, CircleShape)
                        )
                    }
                    // Online Badge
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .align(Alignment.BottomEnd)
                            .background(LumeOnlineGreen, CircleShape)
                            .border(2.dp, Color.White, CircleShape)
                    )
                    // Edit Camera Hint Icon
                    Box(
                        modifier = Modifier
                            .size(16.dp)
                            .align(Alignment.TopEnd)
                            .offset(x = 2.dp, y = (-2).dp)
                            .clip(CircleShape)
                            .background(LumePrimarySage)
                            .border(1.5.dp, Color.White, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Edit,
                            contentDescription = "Alterar Foto",
                            tint = Color.White,
                            modifier = Modifier.size(9.dp)
                        )
                    }
                }

                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "Olá, $doctorName",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = LumeNeutralDark
                            )
                        )
                        Text(text = "✨", fontSize = 16.sp)
                    }
                    Text(
                        text = dateText,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = LumeNeutralMuted,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }
            }

            // Action Buttons
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Search Button
                IconButton(
                    onClick = onSearchClick,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.95f))
                        .border(1.dp, Color.Black.copy(alpha = 0.04f), CircleShape)
                        .shadow(2.dp, CircleShape)
                        .testTag("header_search_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Buscar",
                        tint = LumeNeutralMuted,
                        modifier = Modifier.size(20.dp)
                    )
                }

                // Notification Button with Terracotta Alert Dot
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.95f))
                        .border(1.dp, Color.Black.copy(alpha = 0.04f), CircleShape)
                        .shadow(2.dp, CircleShape)
                        .clickable { onNotificationClick() }
                        .testTag("header_notification_button"),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "Notificações",
                        tint = LumeNeutralMuted,
                        modifier = Modifier.size(20.dp)
                    )
                    // Red/Terracotta Badge
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .align(Alignment.TopEnd)
                            .offset(x = (-8).dp, y = 8.dp)
                            .background(LumeTerracottaBlush, CircleShape)
                    )
                }
            }
        }
    }
}

@Composable
fun DatePillSelector(
    selectedDay: Int,
    onDaySelected: (Int) -> Unit,
    monthYearText: String = "Outubro 2024",
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = BorderStroke(1.dp, Color.Black.copy(alpha = 0.03f)),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(14.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp, vertical = 2.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Esta Semana",
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = LumeNeutralDark
                    )
                )
                Text(
                    text = monthYearText,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = LumePrimarySage
                    )
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Week days row (D S T Q Q S S: 20 to 26)
            val weekDays = listOf(
                Pair("D", 20),
                Pair("S", 21),
                Pair("T", 22),
                Pair("Q", 23),
                Pair("Q", 24),
                Pair("S", 25),
                Pair("S", 26)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                weekDays.forEach { (weekdayLetter, dayNumber) ->
                    val isSelected = (dayNumber == selectedDay)
                    val hasAppointments = (dayNumber in listOf(21, 22, 23))

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .clip(RoundedCornerShape(18.dp))
                            .clickable { onDaySelected(dayNumber) }
                            .background(if (isSelected) LumePrimarySage else Color.Transparent)
                            .padding(horizontal = 8.dp, vertical = 8.dp)
                            .widthIn(min = 38.dp)
                            .testTag("day_pill_$dayNumber")
                    ) {
                        Text(
                            text = weekdayLetter,
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = if (isSelected) FontWeight.Medium else FontWeight.SemiBold,
                                color = if (isSelected) Color.White.copy(alpha = 0.85f) else LumeNeutralMuted
                            )
                        )
                        Spacer(modifier = Modifier.height(3.dp))
                        Text(
                            text = dayNumber.toString(),
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.SemiBold,
                                color = if (isSelected) Color.White else LumeNeutralDark
                            )
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        // Dot indicator
                        Box(
                            modifier = Modifier
                                .size(5.dp)
                                .background(
                                    when {
                                        isSelected -> Color(0xFF6EE7B7) // bright emerald for active pill
                                        hasAppointments -> Color(0xFF34D399) // emerald for days with appointments
                                        else -> Color.Transparent
                                    },
                                    CircleShape
                                )
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MetricCard(
    tag: String,
    title: String,
    value: String,
    unit: String,
    progress: Float,
    icon: ImageVector,
    containerColor: Color,
    textColor: Color,
    progressColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor),
        border = BorderStroke(1.dp, textColor.copy(alpha = 0.12f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Top Row: Circular Icon + Tag Pill
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White.copy(alpha = 0.92f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = textColor,
                        modifier = Modifier.size(18.dp)
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.85f))
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = tag,
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = textColor,
                            fontSize = 9.sp
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Label & Value
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = textColor.copy(alpha = 0.75f),
                    letterSpacing = 0.8.sp
                )
            )

            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = value,
                    style = MaterialTheme.typography.displayMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = textColor,
                        fontSize = 26.sp
                    )
                )
                Text(
                    text = unit,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = textColor.copy(alpha = 0.85f),
                        fontWeight = FontWeight.Medium
                    ),
                    modifier = Modifier.padding(bottom = 3.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Rounded Progress Track
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.75f))
                    .padding(1.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(progress.coerceIn(0f, 1f))
                        .clip(CircleShape)
                        .background(progressColor)
                )
            }
        }
    }
}

@Composable
fun QuickActionScheduleBanner(
    onScheduleClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = BorderStroke(1.dp, Color.Black.copy(alpha = 0.03f)),
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(LumePrimarySage.copy(alpha = 0.12f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Adicionar",
                        tint = LumePrimarySage,
                        modifier = Modifier.size(22.dp)
                    )
                }

                Column {
                    Text(
                        text = "Novo Atendimento",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = LumeNeutralDark
                        )
                    )
                    Text(
                        text = "Adicione paciente à fila de hoje",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = LumeNeutralMuted
                        )
                    )
                }
            }

            Button(
                onClick = onScheduleClick,
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = LumePrimarySage,
                    contentColor = Color.White
                ),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                modifier = Modifier
                    .height(38.dp)
                    .testTag("banner_agendar_button")
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "Agendar",
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun AppointmentItemCard(
    appointment: Appointment,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Choose pastel colors per appointment status
    val (bgColor, textColor, titleColor, statusLabel) = when (appointment.status) {
        AppointmentStatus.EM_ANDAMENTO -> Quadruple(
            LumeRosePastel,
            LumeRoseText,
            LumeRoseDark,
            "Em andamento"
        )
        AppointmentStatus.CONFIRMADO -> Quadruple(
            LumeChampagneSand,
            LumeChampagneText,
            Color(0xFF4B340B),
            "Confirmado"
        )
        AppointmentStatus.AGUARDANDO -> Quadruple(
            LumePurplePastel,
            LumePurpleText,
            LumePurpleDark,
            "Aguardando"
        )
        AppointmentStatus.CONCLUIDO -> Quadruple(
            LumeSageLight,
            LumeSageDark,
            LumeNeutralDark,
            "Concluído"
        )
        AppointmentStatus.CANCELADO -> Quadruple(
            Color(0xFFFDE8E8),
            Color(0xFF9B1C1C),
            Color(0xFF771D1D),
            "Cancelado"
        )
    }

    Card(
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.cardColors(containerColor = bgColor),
        border = BorderStroke(1.dp, textColor.copy(alpha = 0.15f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .testTag("appointment_card_${appointment.id}")
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Top Row: Avatar Initials + Client Name + Status Badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    // Initials Badge
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color.White.copy(alpha = 0.95f))
                            .shadow(1.dp, RoundedCornerShape(16.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = appointment.clientInitials,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = textColor
                            )
                        )
                    }

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = appointment.clientName,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = titleColor
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = appointment.procedureName,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = textColor.copy(alpha = 0.85f),
                                fontWeight = FontWeight.Medium
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Status Chip
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.92f))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        if (appointment.status == AppointmentStatus.EM_ANDAMENTO) {
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .background(Color(0xFFF43F5E), CircleShape)
                            )
                        } else if (appointment.status == AppointmentStatus.CONFIRMADO || appointment.status == AppointmentStatus.CONCLUIDO) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                tint = textColor,
                                modifier = Modifier.size(12.dp)
                            )
                        }
                        Text(
                            text = statusLabel,
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = textColor,
                                fontSize = 11.sp
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Divider
            HorizontalDivider(
                color = textColor.copy(alpha = 0.12f),
                thickness = 1.dp
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Bottom row: Time & Room + Arrow Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Schedule,
                        contentDescription = null,
                        tint = textColor,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = "${appointment.time} • ${appointment.room}",
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = textColor
                        )
                    )
                }

                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.92f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Detalhes",
                        tint = textColor,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

private data class Quadruple<A, B, C, D>(val first: A, val second: B, val third: C, val fourth: D)

@Composable
fun FloatingLumeBottomNav(
    currentTab: NavigationTab,
    onTabSelected: (NavigationTab) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            shape = CircleShape,
            color = Color.White.copy(alpha = 0.96f),
            shadowElevation = 10.dp,
            border = BorderStroke(1.dp, Color(0xFFF1F5F9)),
            modifier = Modifier
                .widthIn(max = 480.dp)
                .fillMaxWidth()
                .height(64.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                NavigationTab.entries.forEach { tab ->
                    val isSelected = (tab == currentTab)
                    val icon = when (tab) {
                        NavigationTab.INICIO -> Icons.Outlined.Home
                        NavigationTab.AGENDA -> Icons.Outlined.CalendarMonth
                        NavigationTab.CLIENTES -> Icons.Outlined.People
                        NavigationTab.PRONTUARIO -> Icons.Outlined.Description
                        NavigationTab.AJUSTES -> Icons.Outlined.Settings
                    }

                    if (isSelected) {
                        // Active Pill Container
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(LumePrimarySage)
                                .clickable { onTabSelected(tab) }
                                .padding(horizontal = 14.dp, vertical = 9.dp)
                                .testTag("nav_tab_${tab.name.lowercase()}")
                        ) {
                            Icon(
                                imageVector = icon,
                                contentDescription = tab.label,
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = tab.label,
                                style = MaterialTheme.typography.titleSmall.copy(
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    } else {
                        // Inactive Item
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .clip(CircleShape)
                                .clickable { onTabSelected(tab) }
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                                .testTag("nav_tab_${tab.name.lowercase()}")
                        ) {
                            Icon(
                                imageVector = icon,
                                contentDescription = tab.label,
                                tint = LumeNeutralMuted,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = tab.label,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontSize = 10.sp,
                                    color = LumeNeutralMuted,
                                    fontWeight = FontWeight.Medium
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}
