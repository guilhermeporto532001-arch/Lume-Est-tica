package com.example

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.model.ActiveSubSection
import com.example.model.NavigationTab
import com.example.ui.MainViewModel
import com.example.ui.components.*
import com.example.ui.screens.*
import com.example.ui.theme.LumeCanvas
import com.example.ui.theme.LumeSageSoft
import com.example.ui.theme.LumeSurfacePorcelain
import com.example.ui.theme.LumeTheme

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            LumeTheme {
                LumeApp(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun LumeApp(viewModel: MainViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    // Show toast messages
    LaunchedEffect(uiState.toastMessage) {
        uiState.toastMessage?.let { msg ->
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
            viewModel.clearToast()
        }
    }

    // Handle back button on sub-screens
    BackHandler(enabled = uiState.activeSubSection != ActiveSubSection.NONE) {
        viewModel.closeSubSection()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = LumeCanvas,
        topBar = {
            Column {
                LumeTopHeader(
                    doctorName = uiState.doctorName,
                    doctorPhotoUri = uiState.doctorPhotoUri,
                    dateText = "Segunda, ${uiState.selectedDay} de Outubro",
                    onProfilePhotoClick = { viewModel.togglePhotoPickerSheet(true) },
                    onSearchClick = { viewModel.toggleSearch() },
                    onNotificationClick = { viewModel.toggleNotificationDialog(true) }
                )

                // Search Overlay if active
                AnimatedVisibility(
                    visible = uiState.isSearchOpen,
                    enter = expandVertically() + fadeIn(),
                    exit = shrinkVertically() + fadeOut()
                ) {
                    Surface(
                        color = LumeCanvas,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 18.dp, vertical = 6.dp)
                    ) {
                        OutlinedTextField(
                            value = uiState.searchQuery,
                            onValueChange = { viewModel.setSearchQuery(it) },
                            placeholder = { Text("Pesquisar pacientes, protocolos, insumos...") },
                            leadingIcon = {
                                Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = LumeSageSoft)
                            },
                            trailingIcon = {
                                IconButton(onClick = { viewModel.toggleSearch() }) {
                                    Icon(imageVector = Icons.Default.Close, contentDescription = "Fechar")
                                }
                            },
                            shape = CircleShape,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = LumeSurfacePorcelain,
                                unfocusedContainerColor = LumeSurfacePorcelain
                            ),
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        },
        bottomBar = {
            // Floating navigation dock (hidden inside full sub-sections if preferred, or always accessible)
            FloatingLumeBottomNav(
                currentTab = uiState.currentTab,
                onTabSelected = { tab ->
                    viewModel.closeSubSection()
                    viewModel.selectTab(tab)
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding())
        ) {
            when (uiState.activeSubSection) {
                ActiveSubSection.PROCEDIMENTOS, ActiveSubSection.PACOTES -> {
                    ProcedimentosScreen(
                        uiState = uiState,
                        onBack = { viewModel.closeSubSection() }
                    )
                }
                ActiveSubSection.FINANCEIRO, ActiveSubSection.RELATORIOS -> {
                    FinanceiroScreen(
                        uiState = uiState
                    )
                }
                ActiveSubSection.ESTOQUE -> {
                    EstoqueScreen(
                        uiState = uiState,
                        onReplenish = { id, qty -> viewModel.replenishInventory(id, qty) }
                    )
                }
                else -> {
                    when (uiState.currentTab) {
                        NavigationTab.INICIO -> {
                            HomeScreen(
                                uiState = uiState,
                                onSelectDay = { viewModel.selectDay(it) },
                                onOpenSchedule = { viewModel.toggleQuickScheduleDialog(true) },
                                onAppointmentClick = { appt ->
                                    viewModel.openSubSection(ActiveSubSection.NONE, appointment = appt)
                                    viewModel.toggleQuickScheduleDialog(true, appt)
                                },
                                onNavigateTab = { tab -> viewModel.selectTab(tab) },
                                onOpenSubSection = { sub -> viewModel.openSubSection(sub) }
                            )
                        }
                        NavigationTab.AGENDA -> {
                            AgendaScreen(
                                uiState = uiState,
                                onSelectDay = { viewModel.selectDay(it) },
                                onFilterChange = { viewModel.setAgendaFilter(it) },
                                onAppointmentClick = { appt ->
                                    viewModel.toggleQuickScheduleDialog(true, appt)
                                },
                                onNewAppointmentClick = { viewModel.toggleQuickScheduleDialog(true) },
                                onStatusChange = { id, status -> viewModel.updateAppointmentStatus(id, status) }
                            )
                        }
                        NavigationTab.CLIENTES -> {
                            ClientsScreen(
                                uiState = uiState,
                                onClientClick = { client -> viewModel.selectClient(client) },
                                onNewClientClick = { viewModel.toggleNewClientDialog(true) },
                                onFilterChange = { viewModel.setClientFilter(it) }
                            )
                        }
                        NavigationTab.PRONTUARIO -> {
                            ProntuarioScreen(
                                uiState = uiState,
                                onOpenNewAnamnese = { viewModel.toggleAnamneseDialog(true) },
                                onSaveClinicalRecord = { record -> viewModel.saveClinicalRecord(record) }
                            )
                        }
                        NavigationTab.AJUSTES -> {
                            AjustesScreen(
                                uiState = uiState,
                                onOpenSubSection = { sub -> viewModel.openSubSection(sub) },
                                onOpenPhotoPicker = { viewModel.togglePhotoPickerSheet(true) }
                            )
                        }
                    }
                }
            }
        }
    }

    // Dialogs
    if (uiState.showPhotoPickerSheet) {
        DoctorProfilePhotoDialog(
            currentName = uiState.doctorName,
            currentPhotoUri = uiState.doctorPhotoUri,
            onDismiss = { viewModel.togglePhotoPickerSheet(false) },
            onSave = { newName, newPhotoUri ->
                viewModel.setDoctorName(newName)
                viewModel.setDoctorPhotoUri(newPhotoUri)
            }
        )
    }

    if (uiState.showQuickScheduleDialog) {
        NewAppointmentDialog(
            onDismiss = { viewModel.toggleQuickScheduleDialog(false) },
            onConfirm = { name, proc, time, room, price, payment, notes ->
                viewModel.addAppointment(name, proc, time, room, price, payment, notes)
            }
        )
    }

    if (uiState.showNewClientDialog) {
        NewClientDialog(
            onDismiss = { viewModel.toggleNewClientDialog(false) },
            onSave = { name, phone, email, birth, cpf, addr, pref, notes, consent ->
                viewModel.addClient(name, phone, email, birth, cpf, addr, pref, notes, consent)
            }
        )
    }

    if (uiState.showAnamneseDialog) {
        DigitalAnamneseDialog(
            client = uiState.selectedClient,
            onDismiss = { viewModel.toggleAnamneseDialog(false) },
            onSave = { anamnese ->
                viewModel.saveAnamnese(anamnese)
            }
        )
    }

    if (uiState.showNotificationDialog) {
        NotificationDialog(
            onDismiss = { viewModel.toggleNotificationDialog(false) }
        )
    }

    if (uiState.activeSubSection == ActiveSubSection.PERFIL_CLIENTE && uiState.selectedClient != null) {
        ClientProfileSheet(
            client = uiState.selectedClient!!,
            onClose = { viewModel.closeSubSection() },
            onScheduleNew = {
                viewModel.closeSubSection()
                viewModel.toggleQuickScheduleDialog(true)
            }
        )
    }
}
