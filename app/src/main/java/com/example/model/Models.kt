package com.example.model

enum class NavigationTab(val label: String) {
    INICIO("Início"),
    AGENDA("Agenda"),
    CLIENTES("Clientes"),
    PRONTUARIO("Prontuário"),
    AJUSTES("Ajustes")
}

enum class ActiveSubSection {
    NONE,
    PROCEDIMENTOS,
    PACOTES,
    FINANCEIRO,
    ESTOQUE,
    RELATORIOS,
    NOVA_ANAMNESE,
    NOVO_CLIENTE,
    NOVO_AGENDAMENTO,
    PERFIL_CLIENTE,
    DETALHE_ATENDIMENTO
}

enum class AppointmentStatus(val label: String) {
    EM_ANDAMENTO("Em andamento"),
    CONFIRMADO("Confirmado"),
    AGUARDANDO("Aguardando"),
    CONCLUIDO("Concluído"),
    CANCELADO("Cancelado")
}

data class Client(
    val id: String,
    val name: String,
    val initials: String,
    val phone: String,
    val email: String,
    val birthDate: String,
    val cpf: String = "",
    val address: String = "",
    val contactPreference: String = "WhatsApp",
    val status: String = "Ativa",
    val notes: String = "",
    val photoConsent: Boolean = true,
    val lastAppointmentDate: String = "",
    val nextAppointmentDate: String = "",
    val totalSpent: Double = 0.0,
    val vipTier: String = "Standard" // Standard, Gold, Champagne VIP
)

data class Appointment(
    val id: String,
    val clientId: String,
    val clientName: String,
    val clientInitials: String,
    val procedureName: String,
    val doctorName: String = "Dra. Mariana",
    val date: String,
    val time: String,
    val room: String,
    val durationMinutes: Int = 60,
    val price: Double = 0.0,
    val status: AppointmentStatus = AppointmentStatus.CONFIRMADO,
    val paymentMethod: String = "PIX",
    val notes: String = ""
)

data class Procedure(
    val id: String,
    val name: String,
    val category: String, // Facial, Laser, Corporal, Injetáveis
    val durationMinutes: Int,
    val price: Double,
    val description: String,
    val contraindications: String,
    val postCare: String,
    val popular: Boolean = true
)

data class PackageItem(
    val id: String,
    val clientId: String,
    val clientName: String,
    val title: String,
    val procedureName: String,
    val totalSessions: Int,
    val completedSessions: Int,
    val price: Double,
    val expiryDate: String,
    val status: String = "Em andamento"
)

data class AnamneseForm(
    val id: String,
    val clientId: String,
    val clientName: String,
    val date: String,
    val mainComplaint: String,
    val aestheticGoals: String,
    val allergies: String,
    val medications: String,
    val healthHistory: String,
    val previousSurgeries: String,
    val pregnancyOrLactation: Boolean,
    val fitzpatrickSkinType: String, // I, II, III, IV, V, VI
    val skinSensitivity: String,     // Normal, Sensível, Muito Reativa
    val hasMelasmaOrAcne: Boolean,
    val confirmedContraindications: Boolean,
    val signatureConfirmed: Boolean
)

data class ClinicalRecord(
    val id: String,
    val appointmentId: String,
    val clientId: String,
    val clientName: String,
    val procedureName: String,
    val date: String,
    val time: String,
    val treatedArea: String,
    val productsUsed: String,
    val batchNumber: String,
    val technicalParams: String,
    val clinicalNotes: String,
    val postProcedureCare: String,
    val returnRecommendationDays: Int
)

data class FinancialTransaction(
    val id: String,
    val clientName: String,
    val description: String,
    val date: String,
    val amount: Double,
    val isIncome: Boolean,
    val status: String, // Recebido, Pendente, Pago
    val paymentMethod: String
)

data class InventoryItem(
    val id: String,
    val name: String,
    val category: String,
    val quantity: Int,
    val unit: String,
    val batchLot: String,
    val expiryDate: String,
    val minThreshold: Int,
    val unitCost: Double,
    val supplier: String
) {
    val isLowStock: Boolean get() = quantity <= minThreshold
}
