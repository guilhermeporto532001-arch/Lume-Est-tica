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
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ActiveSubSection
import com.example.ui.LumeUiState
import com.example.ui.theme.*

@Composable
fun AjustesScreen(
    uiState: LumeUiState,
    onOpenSubSection: (ActiveSubSection) -> Unit,
    onOpenPhotoPicker: () -> Unit = {},
    modifier: Modifier = Modifier
) {
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
                    text = "Ajustes & Sistema",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = LumeNeutralDark
                    )
                )
                Text(
                    text = "Configurações da clínica, equipe e privacidade de dados",
                    style = MaterialTheme.typography.bodySmall.copy(color = LumeNeutralMuted)
                )
            }
        }

        // Clinic Card with Profile Photo Edit Shortcut
        item {
            Card(
                shape = RoundedCornerShape(26.dp),
                colors = CardDefaults.cardColors(containerColor = LumeSurfacePorcelain),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                border = BorderStroke(1.dp, Color.Black.copy(alpha = 0.04f)),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onOpenPhotoPicker() }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(54.dp)
                            .clip(RoundedCornerShape(18.dp))
                            .background(LumePrimarySage),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "LUME",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.White,
                                letterSpacing = 1.sp
                            )
                        )
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Lume Esthétique & Soin",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = LumeNeutralDark
                            )
                        )
                        Text(
                            text = "Resp. Técnica: ${uiState.doctorName} • CRM 142.980-SP",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = LumeNeutralMuted,
                                fontSize = 11.sp
                            )
                        )
                        Text(
                            text = "Toque para alterar foto de perfil e nome ✨",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = LumePrimarySage,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 11.sp
                            )
                        )
                    }

                    Icon(
                        imageVector = Icons.Outlined.Edit,
                        contentDescription = "Editar Perfil",
                        tint = LumeSageSoft,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }

        // Clinical Modules Access
        item {
            Text(
                text = "Módulos de Gestão Clínica",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = LumeNeutralDark
                )
            )
        }

        item {
            Card(
                shape = RoundedCornerShape(26.dp),
                colors = CardDefaults.cardColors(containerColor = LumeSurfacePorcelain),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                border = BorderStroke(1.dp, Color.Black.copy(alpha = 0.04f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(6.dp)) {
                    SettingsRowItem(
                        icon = Icons.Outlined.Spa,
                        title = "Catálogo de Procedimentos",
                        subtitle = "Protocolos, dosagens e orientações home care",
                        onClick = { onOpenSubSection(ActiveSubSection.PROCEDIMENTOS) }
                    )
                    HorizontalDivider(modifier = Modifier.padding(horizontal = 14.dp), color = LumeCanvas)
                    SettingsRowItem(
                        icon = Icons.Outlined.Layers,
                        title = "Pacotes & Sessões",
                        subtitle = "Controle de planos de tratamentos ativos",
                        onClick = { onOpenSubSection(ActiveSubSection.PACOTES) }
                    )
                    HorizontalDivider(modifier = Modifier.padding(horizontal = 14.dp), color = LumeCanvas)
                    SettingsRowItem(
                        icon = Icons.Outlined.AccountBalanceWallet,
                        title = "Módulo Financeiro & Caixa",
                        subtitle = "Recebimentos, contas a pagar e fluxo semanal",
                        onClick = { onOpenSubSection(ActiveSubSection.FINANCEIRO) }
                    )
                    HorizontalDivider(modifier = Modifier.padding(horizontal = 14.dp), color = LumeCanvas)
                    SettingsRowItem(
                        icon = Icons.Outlined.Inventory2,
                        title = "Estoque de Insumos & Toxinas",
                        subtitle = "Controle de lotes, validades e níveis mínimos",
                        onClick = { onOpenSubSection(ActiveSubSection.ESTOQUE) }
                    )
                    HorizontalDivider(modifier = Modifier.padding(horizontal = 14.dp), color = LumeCanvas)
                    SettingsRowItem(
                        icon = Icons.Outlined.Assessment,
                        title = "Relatórios de Desempenho",
                        subtitle = "Taxa de comparecimento e procedimentos mais procurados",
                        onClick = { onOpenSubSection(ActiveSubSection.RELATORIOS) }
                    )
                }
            }
        }

        // Compliance & Security Card
        item {
            Text(
                text = "Segurança & Conformidade LGPD",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = LumeNeutralDark
                )
            )
        }

        item {
            Card(
                shape = RoundedCornerShape(26.dp),
                colors = CardDefaults.cardColors(containerColor = LumeSurfacePorcelain),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                border = BorderStroke(1.dp, Color.Black.copy(alpha = 0.04f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(LumeSageLight),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Shield,
                                contentDescription = null,
                                tint = LumeSageDark,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        Column {
                            Text(
                                text = "Prontuário Eletrônico & Dados Sensíveis",
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = LumeNeutralDark
                                )
                            )
                            Text(
                                text = "Em conformidade com a LGPD e normas do CFM",
                                style = MaterialTheme.typography.bodySmall.copy(color = LumeNeutralMuted)
                            )
                        }
                    }

                    Text(
                        text = "Todas as fichas de anamnese e fotos autorizadas de antes/depois possuem consentimento formal arquivado e registro imutável de evolução terapêutica.",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = LumeNeutralDark.copy(alpha = 0.8f),
                            lineHeight = 18.sp
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun SettingsRowItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .clickable { onClick() }
            .padding(horizontal = 14.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(LumeCanvas),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = LumePrimarySage,
                    modifier = Modifier.size(20.dp)
                )
            }

            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = LumeNeutralDark
                    )
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = LumeNeutralMuted,
                        fontSize = 11.sp
                    )
                )
            }
        }

        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = null,
            tint = LumeNeutralMuted,
            modifier = Modifier.size(16.dp)
        )
    }
}
