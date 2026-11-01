package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.model.AppointmentStatus
import com.example.model.NavigationTab
import com.example.ui.MainViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("Lume Esthétique", appName)
  }

  @Test
  fun `verify initial view model state`() {
    val viewModel = MainViewModel()
    val state = viewModel.uiState.value

    assertEquals(NavigationTab.INICIO, state.currentTab)
    assertEquals(21, state.selectedDay)
    assertEquals(6, state.clients.size)
    assertEquals(5, state.appointments.size)
    assertEquals(6, state.procedures.size)
    assertEquals(5, state.inventory.size)
  }

  @Test
  fun `verify appointment creation and status update`() {
    val viewModel = MainViewModel()
    viewModel.addAppointment(
      clientName = "Isabella Rossi",
      procedureName = "Harmonização Facial",
      time = "17:00",
      room = "Sala 01",
      price = 1500.0,
      paymentMethod = "PIX",
      notes = "Primeira sessão"
    )

    val updatedState = viewModel.uiState.value
    assertEquals(6, updatedState.appointments.size)
    val newAppt = updatedState.appointments.first()
    assertEquals("Isabella Rossi", newAppt.clientName)
    assertEquals(AppointmentStatus.CONFIRMADO, newAppt.status)

    viewModel.updateAppointmentStatus(newAppt.id, AppointmentStatus.EM_ANDAMENTO)
    val inProgressState = viewModel.uiState.value
    val updatedAppt = inProgressState.appointments.first { it.id == newAppt.id }
    assertEquals(AppointmentStatus.EM_ANDAMENTO, updatedAppt.status)
  }
}
