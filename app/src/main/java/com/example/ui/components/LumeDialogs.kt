package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.*
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
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.example.R
import com.example.model.AnamneseForm
import com.example.model.Client
import com.example.ui.theme.*

@Composable
fun NewAppointmentDialog(
    onDismiss: () -> Unit,
    onConfirm: (clientName: String, procedureName: String, time: String, room: String, price: Double, paymentMethod: String, notes: String) -> Unit
) {
    var clientName by remember { mutableStateOf("Mariana Silveira") }
    var procedureName by remember { mutableStateOf("Protocolo Glow Rejuvenescimento") }
    var time by remember { mutableStateOf("11:30") }
    var room by remember { mutableStateOf("Sala 01") }
    var priceText by remember { mutableStateOf("850.00") }
    var paymentMethod by remember { mutableStateOf("PIX") }
    var notes by remember { mutableStateOf("Paciente solicitou reforço na hidratação periocular.") }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            shape = RoundedCornerShape(32.dp),
            color = LumeSurfacePorcelain,
            border = BorderStroke(1.dp, Color.Black.copy(alpha = 0.05f)),
            shadowElevation = 16.dp,
            modifier = Modifier
                .fillMaxWidth(0.94f)
                .padding(vertical = 24.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Novo Agendamento",
                            style = MaterialTheme.typography.headlineSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = LumeNeutralDark
                            )
                        )
                        Text(
                            text = "Adicione um paciente à fila do dia",
                            style = MaterialTheme.typography.bodySmall.copy(color = LumeNeutralMuted)
                        )
                    }

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(LumeCanvas)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Fechar",
                            tint = LumeNeutralMuted,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                HorizontalDivider(color = LumeCanvas)

                // Input fields
                OutlinedTextField(
                    value = clientName,
                    onValueChange = { clientName = it },
                    label = { Text("Nome da Cliente") },
                    shape = CircleShape,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedBorderColor = LumeSageSoft
                    ),
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("novo_agendamento_cliente_input")
                )

                OutlinedTextField(
                    value = procedureName,
                    onValueChange = { procedureName = it },
                    label = { Text("Procedimento Clínico") },
                    shape = CircleShape,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedBorderColor = LumeSageSoft
                    ),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = time,
                        onValueChange = { time = it },
                        label = { Text("Horário") },
                        shape = CircleShape,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedBorderColor = LumeSageSoft
                        ),
                        singleLine = true,
                        modifier = Modifier.weight(1f)
                    )

                    OutlinedTextField(
                        value = room,
                        onValueChange = { room = it },
                        label = { Text("Sala / Consultório") },
                        shape = CircleShape,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedBorderColor = LumeSageSoft
                        ),
                        singleLine = true,
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = priceText,
                        onValueChange = { priceText = it },
                        label = { Text("Valor (R$)") },
                        shape = CircleShape,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedBorderColor = LumeSageSoft
                        ),
                        singleLine = true,
                        modifier = Modifier.weight(1f)
                    )

                    OutlinedTextField(
                        value = paymentMethod,
                        onValueChange = { paymentMethod = it },
                        label = { Text("Pagamento") },
                        shape = CircleShape,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedBorderColor = LumeSageSoft
                        ),
                        singleLine = true,
                        modifier = Modifier.weight(1f)
                    )
                }

                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("Observações Clínicas") },
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedBorderColor = LumeSageSoft
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(6.dp))

                // Actions
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        shape = CircleShape,
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                    ) {
                        Text(
                            text = "Cancelar",
                            style = MaterialTheme.typography.labelLarge.copy(color = LumeNeutralMuted)
                        )
                    }

                    Button(
                        onClick = {
                            val price = priceText.toDoubleOrNull() ?: 0.0
                            onConfirm(clientName, procedureName, time, room, price, paymentMethod, notes)
                        },
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = LumePrimarySage,
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .weight(1.3f)
                            .height(48.dp)
                            .testTag("confirmar_agendamento_btn")
                    ) {
                        Text(
                            text = "Confirmar",
                            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun NewClientDialog(
    onDismiss: () -> Unit,
    onSave: (name: String, phone: String, email: String, birthDate: String, cpf: String, address: String, contactPref: String, notes: String, photoConsent: Boolean) -> Unit
) {
    var step by remember { mutableIntStateOf(1) }
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }
    var cpf by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var contactPref by remember { mutableStateOf("WhatsApp") }
    var notes by remember { mutableStateOf("") }
    var photoConsent by remember { mutableStateOf(true) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            shape = RoundedCornerShape(32.dp),
            color = LumeSurfacePorcelain,
            shadowElevation = 16.dp,
            modifier = Modifier
                .fillMaxWidth(0.94f)
                .padding(vertical = 24.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Header with Step Tracker
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Cadastro de Cliente",
                            style = MaterialTheme.typography.headlineSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = LumeNeutralDark
                            )
                        )
                        Text(
                            text = "Etapa $step de 3 • ${if (step == 1) "Dados Pessoais" else if (step == 2) "Endereço e Contato" else "Consentimento & Notas"}",
                            style = MaterialTheme.typography.bodySmall.copy(color = LumePrimarySage)
                        )
                    }

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(LumeCanvas)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Fechar",
                            tint = LumeNeutralMuted,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                // Progress Bar
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(CircleShape)
                        .background(LumeCanvas)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(step / 3f)
                            .clip(CircleShape)
                            .background(LumePrimarySage)
                    )
                }

                if (step == 1) {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Nome Completo *") },
                        placeholder = { Text("ex: Isabella Rossi") },
                        shape = CircleShape,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedBorderColor = LumeSageSoft
                        ),
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("cadastro_nome_input")
                    )

                    OutlinedTextField(
                        value = phone,
                        onValueChange = { phone = it },
                        label = { Text("Telefone / WhatsApp *") },
                        placeholder = { Text("(11) 98888-7777") },
                        shape = CircleShape,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedBorderColor = LumeSageSoft
                        ),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("E-mail") },
                        shape = CircleShape,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedBorderColor = LumeSageSoft
                        ),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = birthDate,
                            onValueChange = { birthDate = it },
                            label = { Text("Data de Nasc.") },
                            placeholder = { Text("DD/MM/AAAA") },
                            shape = CircleShape,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,
                                focusedBorderColor = LumeSageSoft
                            ),
                            singleLine = true,
                            modifier = Modifier.weight(1f)
                        )
                        OutlinedTextField(
                            value = cpf,
                            onValueChange = { cpf = it },
                            label = { Text("CPF (Opcional)") },
                            shape = CircleShape,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,
                                focusedBorderColor = LumeSageSoft
                            ),
                            singleLine = true,
                            modifier = Modifier.weight(1f)
                        )
                    }
                } else if (step == 2) {
                    OutlinedTextField(
                        value = address,
                        onValueChange = { address = it },
                        label = { Text("Endereço Completo") },
                        placeholder = { Text("Rua, Número, Bairro, Cidade") },
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedBorderColor = LumeSageSoft
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Text(
                        text = "Canal de Contato Preferencial:",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                    )

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        listOf("WhatsApp", "Ligação", "E-mail").forEach { pref ->
                            val isSel = (contactPref == pref)
                            Surface(
                                shape = CircleShape,
                                color = if (isSel) LumePrimarySage else Color.White,
                                border = BorderStroke(1.dp, if (isSel) LumePrimarySage else LumeBorderSubtle),
                                modifier = Modifier.clickable { contactPref = pref }
                            ) {
                                Text(
                                    text = pref,
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        color = if (isSel) Color.White else LumeNeutralDark,
                                        fontWeight = FontWeight.Bold
                                    ),
                                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                                )
                            }
                        }
                    }
                } else {
                    OutlinedTextField(
                        value = notes,
                        onValueChange = { notes = it },
                        label = { Text("Observações Clínicas Iniciais") },
                        placeholder = { Text("Alergias conhecidas, preferências de atendimento...") },
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedBorderColor = LumeSageSoft
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Card(
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(containerColor = LumeSageLight),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Checkbox(
                                checked = photoConsent,
                                onCheckedChange = { photoConsent = it },
                                colors = CheckboxDefaults.colors(checkedColor = LumePrimarySage)
                            )
                            Column {
                                Text(
                                    text = "Autorização de Imagem Clínica",
                                    style = MaterialTheme.typography.titleSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = LumeSageDark
                                    )
                                )
                                Text(
                                    text = "Permitir registro fotográfico sigiloso de antes/depois no prontuário.",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        fontSize = 11.sp,
                                        color = LumeNeutralDark
                                    )
                                )
                            }
                        }
                    }
                }

                // Step Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    if (step > 1) {
                        OutlinedButton(
                            onClick = { step-- },
                            shape = CircleShape,
                            modifier = Modifier
                                .weight(1f)
                                .height(46.dp)
                        ) {
                            Text("Voltar", style = MaterialTheme.typography.labelLarge)
                        }
                    }

                    Button(
                        onClick = {
                            if (step < 3) {
                                step++
                            } else {
                                onSave(name.ifBlank { "Nova Cliente" }, phone, email, birthDate, cpf, address, contactPref, notes, photoConsent)
                            }
                        },
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = LumePrimarySage,
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .weight(1.3f)
                            .height(46.dp)
                            .testTag("cadastro_proximo_salvar_btn")
                    ) {
                        Text(
                            text = if (step < 3) "Próximo" else "Concluir Cadastro",
                            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DigitalAnamneseDialog(
    client: Client?,
    onDismiss: () -> Unit,
    onSave: (AnamneseForm) -> Unit
) {
    var step by remember { mutableIntStateOf(1) }
    var mainComplaint by remember { mutableStateOf("Linhas de expressão na testa e perda de hidratação") }
    var aestheticGoals by remember { mutableStateOf("Rejuvenescimento natural sem efeito artificial") }
    var allergies by remember { mutableStateOf("Nenhuma alergia conhecida") }
    var medications by remember { mutableStateOf("Suplementos de colágeno e vitamina C") }
    var healthHistory by remember { mutableStateOf("Nega hipertensão, diabetes ou cardiopatias.") }
    var surgeries by remember { mutableStateOf("Nenhuma cirurgia prévia na face") }
    var pregnancyOrLactation by remember { mutableStateOf(false) }
    var skinType by remember { mutableStateOf("II") }
    var skinSensitivity by remember { mutableStateOf("Sensível") }
    var termsAccepted by remember { mutableStateOf(true) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            shape = RoundedCornerShape(32.dp),
            color = LumeSurfacePorcelain,
            shadowElevation = 16.dp,
            modifier = Modifier
                .fillMaxWidth(0.94f)
                .padding(vertical = 24.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Anamnese Estética Digital",
                            style = MaterialTheme.typography.headlineSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = LumeNeutralDark
                            )
                        )
                        Text(
                            text = "Paciente: ${client?.name ?: "Mariana Silveira"} • Etapa $step de 3",
                            style = MaterialTheme.typography.bodySmall.copy(color = LumePrimarySage)
                        )
                    }

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(LumeCanvas)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Fechar",
                            tint = LumeNeutralMuted,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                // Progress Bar
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(CircleShape)
                        .background(LumeCanvas)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(step / 3f)
                            .clip(CircleShape)
                            .background(LumePrimarySage)
                    )
                }

                if (step == 1) {
                    OutlinedTextField(
                        value = mainComplaint,
                        onValueChange = { mainComplaint = it },
                        label = { Text("Queixa Principal do Paciente") },
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = LumeSageSoft),
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = aestheticGoals,
                        onValueChange = { aestheticGoals = it },
                        label = { Text("Objetivos & Expectativas com o Tratamento") },
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = LumeSageSoft),
                        modifier = Modifier.fillMaxWidth()
                    )
                } else if (step == 2) {
                    // Highlighted in terracotta if allergy
                    Card(
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = LumeRosePastel),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text(
                                text = "Alergias a Medicamentos / Cosméticos",
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = LumeRoseText
                                )
                            )
                            OutlinedTextField(
                                value = allergies,
                                onValueChange = { allergies = it },
                                shape = CircleShape,
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedContainerColor = Color.White,
                                    unfocusedContainerColor = Color.White,
                                    focusedBorderColor = LumeTerracottaBlush
                                ),
                                singleLine = true,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 6.dp)
                            )
                        }
                    }

                    OutlinedTextField(
                        value = medications,
                        onValueChange = { medications = it },
                        label = { Text("Medicamentos e Suplementos em uso") },
                        shape = CircleShape,
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = LumeSageSoft),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = healthHistory,
                        onValueChange = { healthHistory = it },
                        label = { Text("Histórico Geral de Saúde") },
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = LumeSageSoft),
                        modifier = Modifier.fillMaxWidth()
                    )
                } else {
                    Text(
                        text = "Fototipo de Fitzpatrick da Pele:",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        listOf("I", "II", "III", "IV", "V", "VI").forEach { type ->
                            val isSel = (skinType == type)
                            Box(
                                modifier = Modifier
                                    .size(42.dp)
                                    .clip(CircleShape)
                                    .background(if (isSel) LumePrimarySage else LumeCanvas)
                                    .clickable { skinType = type },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = type,
                                    style = MaterialTheme.typography.titleSmall.copy(
                                        color = if (isSel) Color.White else LumeNeutralDark,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Card(
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(containerColor = LumeSageLight),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Checkbox(
                                checked = termsAccepted,
                                onCheckedChange = { termsAccepted = it },
                                colors = CheckboxDefaults.colors(checkedColor = LumePrimarySage)
                            )
                            Text(
                                text = "Declaro que todas as informações foram prestadas com veracidade e autorizo o início do protocolo clínico.",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 11.sp,
                                    color = LumeSageDark,
                                    fontWeight = FontWeight.Medium
                                )
                            )
                        }
                    }
                }

                // Navigation Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    if (step > 1) {
                        OutlinedButton(
                            onClick = { step-- },
                            shape = CircleShape,
                            modifier = Modifier
                                .weight(1f)
                                .height(46.dp)
                        ) {
                            Text("Voltar")
                        }
                    }

                    Button(
                        onClick = {
                            if (step < 3) {
                                step++
                            } else {
                                val form = AnamneseForm(
                                    id = "an_${System.currentTimeMillis()}",
                                    clientId = client?.id ?: "c1",
                                    clientName = client?.name ?: "Mariana Silveira",
                                    date = "21/10/2024",
                                    mainComplaint = mainComplaint,
                                    aestheticGoals = aestheticGoals,
                                    allergies = allergies,
                                    medications = medications,
                                    healthHistory = healthHistory,
                                    previousSurgeries = surgeries,
                                    pregnancyOrLactation = pregnancyOrLactation,
                                    fitzpatrickSkinType = skinType,
                                    skinSensitivity = skinSensitivity,
                                    hasMelasmaOrAcne = false,
                                    confirmedContraindications = true,
                                    signatureConfirmed = termsAccepted
                                )
                                onSave(form)
                            }
                        },
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = LumePrimarySage,
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .weight(1.3f)
                            .height(46.dp)
                    ) {
                        Text(
                            text = if (step < 3) "Próximo" else "Salvar Anamnese",
                            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun NotificationDialog(
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(28.dp),
            color = LumeSurfacePorcelain,
            shadowElevation = 14.dp,
            modifier = Modifier.fillMaxWidth(0.92f)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Notificações Clínicas",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = LumeNeutralDark
                        )
                    )
                    IconButton(onClick = onDismiss, modifier = Modifier.size(32.dp)) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = null, tint = LumeNeutralMuted)
                    }
                }

                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = LumeRosePastel),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .background(LumeTerracottaBlush, CircleShape)
                        )
                        Column {
                            Text(
                                text = "Estoque Mínimo Atingido",
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = LumeRoseText
                                )
                            )
                            Text(
                                text = "Ácido Hialurônico Ultra Deep possui apenas 2 seringas restantes.",
                                style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp)
                            )
                        }
                    }
                }

                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = LumeChampagneSand),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .background(LumeChampagneGold, CircleShape)
                        )
                        Column {
                            Text(
                                text = "Retorno Recomendado",
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = LumeChampagneText
                                )
                            )
                            Text(
                                text = "Carolina Mendes completa 15 dias de pós-peeling hoje.",
                                style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp)
                            )
                        }
                    }
                }

                Button(
                    onClick = onDismiss,
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(containerColor = LumePrimarySage),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(42.dp)
                ) {
                    Text("OK, ciente")
                }
            }
        }
    }
}

@Composable
fun ClientProfileSheet(
    client: Client,
    onClose: () -> Unit,
    onScheduleNew: () -> Unit
) {
    var selectedTab by remember { mutableStateOf("Resumo") }
    val tabs = listOf("Resumo", "Histórico", "Financeiro")

    Dialog(
        onDismissRequest = onClose,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            shape = RoundedCornerShape(32.dp),
            color = LumeSurfacePorcelain,
            shadowElevation = 16.dp,
            modifier = Modifier
                .fillMaxWidth(0.94f)
                .padding(vertical = 24.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(22.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Top Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .clip(RoundedCornerShape(18.dp))
                            .background(LumeSageLight),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = client.initials,
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.ExtraBold,
                                    color = LumeSageDark
                                )
                            )
                        }

                        Column {
                            Text(
                                text = client.name,
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = LumeNeutralDark
                                )
                            )
                            Text(
                                text = "${client.phone} • ${client.vipTier}",
                                style = MaterialTheme.typography.bodySmall.copy(color = LumeNeutralMuted)
                            )
                        }
                    }

                    IconButton(onClick = onClose) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = null, tint = LumeNeutralMuted)
                    }
                }

                // Capsule Tabs
                Surface(
                    shape = CircleShape,
                    color = LumeCanvas,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(modifier = Modifier.padding(3.dp)) {
                        tabs.forEach { tab ->
                            val isSel = (tab == selectedTab)
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(CircleShape)
                                    .background(if (isSel) LumePrimarySage else Color.Transparent)
                                    .clickable { selectedTab = tab }
                                    .padding(vertical = 8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = tab,
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        color = if (isSel) Color.White else LumeNeutralDark,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }
                        }
                    }
                }

                if (selectedTab == "Resumo") {
                    Card(
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = LumeCanvas),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text(
                                text = "Observações Dermatológicas",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                            )
                            Text(
                                text = client.notes.ifBlank { "Sem anotações clínicas registradas." },
                                style = MaterialTheme.typography.bodySmall.copy(color = LumeNeutralDark)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Endereço: ${client.address}",
                                style = MaterialTheme.typography.bodySmall.copy(color = LumeNeutralMuted, fontSize = 11.sp)
                            )
                            Text(
                                text = "CPF: ${client.cpf} • Preferência: ${client.contactPreference}",
                                style = MaterialTheme.typography.bodySmall.copy(color = LumeNeutralMuted, fontSize = 11.sp)
                            )
                        }
                    }
                } else if (selectedTab == "Histórico") {
                    Card(
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = LumeSageLight),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(
                                text = "Último Atendimento: ${client.lastAppointmentDate}",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = LumeSageDark)
                            )
                            Text(
                                text = "Próxima Sessão Programada: ${client.nextAppointmentDate}",
                                style = MaterialTheme.typography.bodySmall.copy(color = LumeNeutralDark)
                            )
                            Text(
                                text = "Autorização de Fotos Clínicas: ${if (client.photoConsent) "Sim (Concedida)" else "Não"}",
                                style = MaterialTheme.typography.bodySmall.copy(color = LumeSageDark, fontSize = 11.sp)
                            )
                        }
                    }
                } else {
                    Card(
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = LumeChampagneSand),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(
                                text = "Total em Tratamentos Acumulado",
                                style = MaterialTheme.typography.labelSmall.copy(color = LumeChampagneText)
                            )
                            Text(
                                text = "R$ ${"%.2f".format(client.totalSpent)}",
                                style = MaterialTheme.typography.headlineSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = LumeNeutralDark
                                )
                            )
                            Text(
                                text = "Classificação de Fidelidade: ${client.vipTier}",
                                style = MaterialTheme.typography.bodySmall.copy(color = LumeChampagneText, fontWeight = FontWeight.SemiBold)
                            )
                        }
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        onClick = onClose,
                        shape = CircleShape,
                        modifier = Modifier
                            .weight(1f)
                            .height(46.dp)
                    ) {
                        Text("Fechar")
                    }

                    Button(
                        onClick = {
                            onClose()
                            onScheduleNew()
                        },
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(containerColor = LumePrimarySage),
                        modifier = Modifier
                            .weight(1.3f)
                            .height(46.dp)
                    ) {
                        Text("Agendar Sessão", style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold))
                    }
                }
            }
        }
    }
}

@Composable
fun DoctorProfilePhotoDialog(
    currentName: String,
    currentPhotoUri: String?,
    onDismiss: () -> Unit,
    onSave: (newName: String, newPhotoUri: String?) -> Unit
) {
    var doctorName by remember { mutableStateOf(currentName) }
    var selectedPhotoUri by remember { mutableStateOf(currentPhotoUri) }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            selectedPhotoUri = uri.toString()
        }
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            shape = RoundedCornerShape(32.dp),
            color = LumeSurfacePorcelain,
            shadowElevation = 18.dp,
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .padding(vertical = 24.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Foto de Perfil & Dados",
                            style = MaterialTheme.typography.headlineSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = LumeNeutralDark
                            )
                        )
                        Text(
                            text = "Personalize sua foto e nome no aplicativo",
                            style = MaterialTheme.typography.bodySmall.copy(color = LumeNeutralMuted)
                        )
                    }

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(LumeCanvas)
                    ) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Fechar", tint = LumeNeutralMuted)
                    }
                }

                HorizontalDivider(color = LumeCanvas)

                // Large Avatar Preview with Online Badge
                Box(
                    modifier = Modifier.size(110.dp),
                    contentAlignment = Alignment.Center
                ) {
                    if (selectedPhotoUri != null) {
                        AsyncImage(
                            model = selectedPhotoUri,
                            contentDescription = "Foto da $doctorName",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(110.dp)
                                .clip(CircleShape)
                                .border(4.dp, LumeSageLight, CircleShape)
                        )
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.dra_camila),
                            contentDescription = "Foto da $doctorName",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(110.dp)
                                .clip(CircleShape)
                                .border(4.dp, LumeSageLight, CircleShape)
                        )
                    }

                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .align(Alignment.BottomEnd)
                            .background(LumeOnlineGreen, CircleShape)
                            .border(3.dp, Color.White, CircleShape)
                    )
                }

                // Photo actions
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = {
                            photoPickerLauncher.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        },
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = LumePrimarySage,
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .height(44.dp)
                            .testTag("escolher_foto_galeria_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.PhotoCamera,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Galeria",
                            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
                        )
                    }

                    if (selectedPhotoUri != null) {
                        OutlinedButton(
                            onClick = { selectedPhotoUri = null },
                            shape = CircleShape,
                            modifier = Modifier.height(44.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.RestartAlt,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp),
                                tint = LumeNeutralMuted
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Padrão",
                                style = MaterialTheme.typography.labelSmall.copy(color = LumeNeutralMuted)
                            )
                        }
                    }
                }

                // Name input
                OutlinedTextField(
                    value = doctorName,
                    onValueChange = { doctorName = it },
                    label = { Text("Nome da Profissional") },
                    placeholder = { Text("ex: Dra. Mariana") },
                    shape = CircleShape,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedBorderColor = LumeSageSoft
                    ),
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("nome_profissional_input")
                )

                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = LumeCanvas,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.CheckCircle,
                            contentDescription = null,
                            tint = LumePrimarySage,
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = "A foto escolhida é atualizada instantaneamente no cabeçalho e perfil da clínica.",
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontSize = 11.sp,
                                color = LumeNeutralDark
                            )
                        )
                    }
                }

                // Save actions
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        shape = CircleShape,
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                    ) {
                        Text("Cancelar", style = MaterialTheme.typography.labelLarge)
                    }

                    Button(
                        onClick = {
                            onSave(doctorName.ifBlank { "Dra. Mariana" }, selectedPhotoUri)
                        },
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = LumePrimarySage,
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .weight(1.3f)
                            .height(48.dp)
                            .testTag("salvar_foto_perfil_btn")
                    ) {
                        Text(
                            text = "Salvar Alterações",
                            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                }
            }
        }
    }
}
