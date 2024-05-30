package com.example.userinfocollector.Presentation.main

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.userinfocollector.Presentation.ViewModel.UserViewModel
import com.example.userinfocollector.Presentation.theme.UserInfoCollectorTheme
import java.text.SimpleDateFormat
import java.util.*
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("SimpleDateFormat")
@Composable
fun UserFormScreen(
    viewModel: UserViewModel,
    onItemClick: (clicked: Boolean) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    val showCalender = remember { mutableStateOf(false) }

    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    Column(
        modifier = Modifier
            .padding(30.dp, 70.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Header("User info collector")

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            )
        )

        OutlinedTextField(
            value = age,
            onValueChange = { age = it },
            label = { Text("Age") },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Number
            ),
            keyboardActions = KeyboardActions(onNext = {
                keyboardController?.hide()
            }),
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            )
        )

        OutlinedTextField(
            enabled = false,
            value = dob,
            onValueChange = { dob = it },
            label = { Text("Date of Birth") },
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = {
                    showCalender.value = true
                }),
            colors = OutlinedTextFieldDefaults.colors(
                disabledBorderColor = MaterialTheme.colorScheme.outline,
                disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                disabledTextColor = Color.Black
            )
        )

        OutlinedTextField(
            value = address,
            onValueChange = { address = it },
            label = { Text("Address") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            )
        )

        if (showCalender.value) {
            showCalender.value = false
            DatePicker(
                context = context,
                onDateSelected = { selectedDate ->
                    dob = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(selectedDate)
                }
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Button(
                onClick = {
                    if (name.isNotEmpty() || age.isNotEmpty() || address.isNotEmpty()) {
                        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(dob)
                        val date =
                            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(dob)!!.time
                        viewModel.insert(name, age.toInt(), date, address)
                    } else {
                        Toast.makeText(context, "Some Fields are Missing", Toast.LENGTH_SHORT)
                            .show()
                    }
                },
            ) {
                Text("Save in DB")
            }

            Button(
                onClick = {
                    viewModel.getUsers()
                    onItemClick(true)
                },
            ) {
                Text("View Users")
            }
        }

    }

    // Collect toast events
    LaunchedEffect(Unit) {
        viewModel.toastEvents.collectLatest { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
}

@Composable
fun Header(title: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
fun DatePicker(
    context: android.content.Context,
    onDateSelected: (Date) -> Unit
) {
    var isDatePickerVisible = remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf(Calendar.getInstance().time) }

    if (!isDatePickerVisible.value) {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                val calendar = Calendar.getInstance()
                calendar.set(year, month, dayOfMonth)
                selectedDate = calendar.time
                onDateSelected(selectedDate)
                isDatePickerVisible.value = true
            },
            Calendar.getInstance().get(Calendar.YEAR),
            Calendar.getInstance().get(Calendar.MONTH),
            Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        ).show()
    }
}

@Preview(showBackground = true)
@Composable
fun UserFormScreenPreview() {
    UserInfoCollectorTheme {
        var viewModel = UserViewModel(applicationContext = LocalContext.current)
        UserFormScreen(viewModel, onItemClick = {})
    }
}


