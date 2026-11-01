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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.Phone
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
import com.example.model.Client
import com.example.ui.LumeUiState
import com.example.ui.theme.*

@Composable
fun ClientsScreen(
    uiState: LumeUiState,
    onClientClick: (Client) -> Unit,
    onNewClientClick: () -> Unit,
    onFilterChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    val filters = listOf("Todas", "Ativas", "Retornos pendentes", "VIP Champagne")

    val filteredClients = uiState.clients.filter { client ->
        val matchesSearch = client.name.contains(searchQuery, ignoreCase = true) ||
                client.phone.contains(searchQuery) ||
                client.notes.contains(searchQuery, ignoreCase = true)

        val matchesFilter = when (uiState.clientFilterTab) {
            "Ativas" -> client.status == "Ativa"
            "Retornos pendentes" -> client.status == "Retorno pendente"
            "VIP Champagne" -> client.vipTier == "Champagne VIP"
            else -> true
        }

        matchesSearch && matchesFilter
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(LumeCanvas),
        contentPadding = PaddingValues(start = 18.dp, end = 18.dp, top = 8.dp, bottom = 120.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Title & Add Client Button
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Gestão de Clientes",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = LumeNeutralDark
                        )
                    )
                    Text(
                        text = "${uiState.clients.size} clientes cadastradas",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = LumeNeutralMuted
                        )
                    )
                }

                Button(
                    onClick = onNewClientClick,
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = LumePrimarySage,
                        contentColor = Color.White
                    ),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    modifier = Modifier.testTag("novo_cliente_btn")
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Cadastrar",
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }

        // Search Input in Cushion-Pill Style
        item {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = {
                    Text(
                        text = "Buscar por nome, telefone ou protocolo...",
                        style = MaterialTheme.typography.bodySmall.copy(color = LumeNeutralMuted)
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Buscar",
                        tint = LumeNeutralMuted
                    )
                },
                shape = CircleShape,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = LumeSurfacePorcelain,
                    unfocusedContainerColor = LumeSurfacePorcelain,
                    focusedBorderColor = LumeSageSoft,
                    unfocusedBorderColor = LumeBorderSubtle
                ),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("client_search_field")
            )
        }

        // Filter Chips Row
        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(filters) { filter ->
                    val isSelected = (filter == uiState.clientFilterTab)
                    Surface(
                        shape = CircleShape,
                        color = if (isSelected) LumePrimarySage else Color.White,
                        border = BorderStroke(
                            1.dp,
                            if (isSelected) LumePrimarySage else Color.Black.copy(alpha = 0.05f)
                        ),
                        modifier = Modifier
                            .clickable { onFilterChange(filter) }
                            .testTag("filter_chip_$filter")
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

        // Client Cards List
        items(filteredClients) { client ->
            ClientCardItem(
                client = client,
                onClick = { onClientClick(client) }
            )
        }
    }
}

@Composable
private fun ClientCardItem(
    client: Client,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.cardColors(containerColor = LumeSurfacePorcelain),
        border = BorderStroke(1.dp, Color.Black.copy(alpha = 0.04f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .testTag("client_item_${client.id}")
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
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(LumeSageLight),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = client.initials,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = LumeSageDark
                            )
                        )
                    }

                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = client.name,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = LumeNeutralDark
                                )
                            )
                            if (client.vipTier == "Champagne VIP") {
                                Box(
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .background(LumeChampagneSand)
                                        .padding(horizontal = 8.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = "VIP",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = LumeChampagneText,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 9.sp
                                        )
                                    )
                                }
                            }
                        }

                        Text(
                            text = client.phone,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = LumeNeutralMuted
                            )
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(CircleShape)
                        .background(LumeCanvas),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Ver Perfil",
                        tint = LumeNeutralMuted,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            if (client.notes.isNotBlank()) {
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = LumeCanvas,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = client.notes,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = LumeNeutralDark,
                            fontSize = 12.sp
                        ),
                        modifier = Modifier.padding(10.dp),
                        maxLines = 2
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Último: ${client.lastAppointmentDate} • Próx: ${client.nextAppointmentDate}",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = LumeNeutralMuted,
                        fontWeight = FontWeight.Medium
                    )
                )

                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(CircleShape)
                            .background(LumeSageLight)
                            .clickable { /* WhatsApp action */ },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.ChatBubbleOutline,
                            contentDescription = "WhatsApp",
                            tint = LumeSageDark,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(CircleShape)
                            .background(LumeBluePastel)
                            .clickable { /* Call action */ },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Phone,
                            contentDescription = "Ligar",
                            tint = LumeBlueText,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }
            }
        }
    }
}
