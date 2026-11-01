package com.example.ui

import androidx.lifecycle.ViewModel
import com.example.data.SampleData
import com.example.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class LumeUiState(
    val currentTab: NavigationTab = NavigationTab.INICIO,
    val activeSubSection: ActiveSubSection = ActiveSubSection.NONE,
    val selectedDay: Int = 21, // 21 is Monday (screen reference)
    val selectedMonthYear: String = "Outubro 2024",
    val searchQuery: String = "",
    val isSearchOpen: Boolean = false,
    val showNotificationDialog: Boolean = false,
    val showQuickScheduleDialog: Boolean = false,
    val showNewClientDialog: Boolean = false,
    val showAnamneseDialog: Boolean = false,
    val selectedClient: Client? = null,
    val selectedAppointment: Appointment? = null,
    val clients: List<Client> = SampleData.initialClients,
    val appointments: List<Appointment> = SampleData.initialAppointments,
    val procedures: List<Procedure> = SampleData.initialProcedures,
    val packages: List<PackageItem> = SampleData.initialPackages,
    val anamneses: List<AnamneseForm> = SampleData.initialAnamneses,
    val clinicalRecords: List<ClinicalRecord> = SampleData.initialClinicalRecords,
    val transactions: List<FinancialTransaction> = SampleData.initialTransactions,
    val inventory: List<InventoryItem> = SampleData.initialInventory,
    val clientFilterTab: String = "Todas", // Todas, Ativas, Retornos
    val agendaFilterStatus: String = "Todos", // Todos, Confirmados, Em andamento, Concluídos
    val toastMessage: String? = null,
    val doctorName: String = "Dra. Mariana",
    val doctorPhotoUri: String? = null,
    val showPhotoPickerSheet: Boolean = false
)

class MainViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(LumeUiState())
    val uiState: StateFlow<LumeUiState> = _uiState.asStateFlow()

    fun selectTab(tab: NavigationTab) {
        _uiState.update {
            it.copy(
                currentTab = tab,
                activeSubSection = ActiveSubSection.NONE
            )
        }
    }

    fun openSubSection(subSection: ActiveSubSection, client: Client? = null, appointment: Appointment? = null) {
        _uiState.update {
            it.copy(
                activeSubSection = subSection,
                selectedClient = client ?: it.selectedClient,
                selectedAppointment = appointment ?: it.selectedAppointment
            )
        }
    }

    fun closeSubSection() {
        _uiState.update {
            it.copy(activeSubSection = ActiveSubSection.NONE)
        }
    }

    fun selectDay(day: Int) {
        _uiState.update { it.copy(selectedDay = day) }
    }

    fun setSearchQuery(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun toggleSearch() {
        _uiState.update { it.copy(isSearchOpen = !it.isSearchOpen) }
    }

    fun toggleNotificationDialog(show: Boolean) {
        _uiState.update { it.copy(showNotificationDialog = show) }
    }

    fun toggleQuickScheduleDialog(show: Boolean, appointment: Appointment? = null) {
        _uiState.update {
            it.copy(
                showQuickScheduleDialog = show,
                selectedAppointment = appointment
            )
        }
    }

    fun toggleNewClientDialog(show: Boolean) {
        _uiState.update { it.copy(showNewClientDialog = show) }
    }

    fun toggleAnamneseDialog(show: Boolean, client: Client? = null) {
        _uiState.update {
            it.copy(
                showAnamneseDialog = show,
                selectedClient = client ?: it.selectedClient
            )
        }
    }

    fun selectClient(client: Client) {
        _uiState.update {
            it.copy(
                selectedClient = client,
                activeSubSection = ActiveSubSection.PERFIL_CLIENTE
            )
        }
    }

    fun setClientFilter(filter: String) {
        _uiState.update { it.copy(clientFilterTab = filter) }
    }

    fun setAgendaFilter(filter: String) {
        _uiState.update { it.copy(agendaFilterStatus = filter) }
    }

    fun addAppointment(
        clientName: String,
        procedureName: String,
        time: String,
        room: String,
        price: Double,
        paymentMethod: String,
        notes: String
    ) {
        val initials = clientName.split(" ")
            .filter { it.isNotBlank() }
            .take(2)
            .map { it.first().uppercaseChar() }
            .joinToString("")
            .ifEmpty { "PA" }

        val newAppt = Appointment(
            id = "a_${System.currentTimeMillis()}",
            clientId = "c_auto",
            clientName = clientName,
            clientInitials = initials,
            procedureName = procedureName,
            date = "${_uiState.value.selectedDay}/10/2024",
            time = time,
            room = room,
            price = price,
            status = AppointmentStatus.CONFIRMADO,
            paymentMethod = paymentMethod,
            notes = notes
        )

        val newTx = FinancialTransaction(
            id = "tx_${System.currentTimeMillis()}",
            clientName = clientName,
            description = procedureName,
            date = "${_uiState.value.selectedDay}/10/2024",
            amount = price,
            isIncome = true,
            status = "Pendente",
            paymentMethod = paymentMethod
        )

        _uiState.update {
            it.copy(
                appointments = listOf(newAppt) + it.appointments,
                transactions = listOf(newTx) + it.transactions,
                showQuickScheduleDialog = false,
                toastMessage = "Atendimento agendado com sucesso para $clientName"
            )
        }
    }

    fun updateAppointmentStatus(appointmentId: String, newStatus: AppointmentStatus) {
        _uiState.update { state ->
            val updated = state.appointments.map { appt ->
                if (appt.id == appointmentId) appt.copy(status = newStatus) else appt
            }
            state.copy(
                appointments = updated,
                toastMessage = "Status atualizado para: ${newStatus.label}"
            )
        }
    }

    fun addClient(
        name: String,
        phone: String,
        email: String,
        birthDate: String,
        cpf: String,
        address: String,
        contactPref: String,
        notes: String,
        photoConsent: Boolean
    ) {
        val initials = name.split(" ")
            .filter { it.isNotBlank() }
            .take(2)
            .map { it.first().uppercaseChar() }
            .joinToString("")
            .ifEmpty { "CL" }

        val newClient = Client(
            id = "c_${System.currentTimeMillis()}",
            name = name,
            initials = initials,
            phone = phone,
            email = email,
            birthDate = birthDate,
            cpf = cpf,
            address = address,
            contactPreference = contactPref,
            status = "Ativa",
            notes = notes,
            photoConsent = photoConsent,
            lastAppointmentDate = "Hoje",
            nextAppointmentDate = "A definir",
            totalSpent = 0.0,
            vipTier = "Standard"
        )

        _uiState.update {
            it.copy(
                clients = listOf(newClient) + it.clients,
                showNewClientDialog = false,
                toastMessage = "Cliente $name cadastrada com sucesso!"
            )
        }
    }

    fun saveAnamnese(anamnese: AnamneseForm) {
        _uiState.update {
            it.copy(
                anamneses = listOf(anamnese) + it.anamneses.filter { a -> a.id != anamnese.id },
                showAnamneseDialog = false,
                toastMessage = "Ficha de Anamnese salva com sucesso!"
            )
        }
    }

    fun saveClinicalRecord(record: ClinicalRecord) {
        _uiState.update { state ->
            val updatedAppointments = state.appointments.map {
                if (it.id == record.appointmentId) it.copy(status = AppointmentStatus.CONCLUIDO) else it
            }
            state.copy(
                clinicalRecords = listOf(record) + state.clinicalRecords,
                appointments = updatedAppointments,
                activeSubSection = ActiveSubSection.NONE,
                toastMessage = "Atendimento concluído e registrado no prontuário!"
            )
        }
    }

    fun replenishInventory(itemId: String, amountToAdd: Int) {
        _uiState.update { state ->
            val updated = state.inventory.map { item ->
                if (item.id == itemId) item.copy(quantity = item.quantity + amountToAdd) else item
            }
            state.copy(
                inventory = updated,
                toastMessage = "Estoque atualizado com sucesso!"
            )
        }
    }

    fun setDoctorPhotoUri(uriString: String?) {
        _uiState.update {
            it.copy(
                doctorPhotoUri = uriString,
                showPhotoPickerSheet = false,
                toastMessage = "Foto de perfil da ${_uiState.value.doctorName} atualizada!"
            )
        }
    }

    fun setDoctorName(name: String) {
        _uiState.update {
            it.copy(
                doctorName = name,
                toastMessage = "Nome atualizado para $name"
            )
        }
    }

    fun togglePhotoPickerSheet(show: Boolean) {
        _uiState.update { it.copy(showPhotoPickerSheet = show) }
    }

    fun clearToast() {
        _uiState.update { it.copy(toastMessage = null) }
    }
}
