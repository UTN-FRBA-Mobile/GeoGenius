package com.utnfrba.geogenius.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Icon
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import com.utnfrba.geogenius.R

import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun FilterScreen() {

    var cafeChecked by remember { mutableStateOf(false) }
    var museoChecked by remember { mutableStateOf(false) }
    var parqueChecked by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            CheckboxWithLabel(
                checked = cafeChecked,
                onCheckedChange = { cafeChecked = it },
                label = "Cafés"
            )

            CheckboxWithLabel(
                checked = museoChecked,
                onCheckedChange = { museoChecked = it },
                label = "Museos"
            )

            CheckboxWithLabel(
                checked = parqueChecked,
                onCheckedChange = { parqueChecked = it },
                label = "Parques"
            )
        }
    }
    Box {
        val marsViewModel: MarsViewModel = viewModel()
        when (val now = marsViewModel.marsUiState) {
            is MarsUiState.Loading -> Text("Loading")
            is MarsUiState.Success -> Text(
                "Photos length: " + now.photos.length.toString()
            )
            is MarsUiState.Error -> Text("Error")
        }
    }
}

@Composable
fun CheckboxWithLabel(checked: Boolean, onCheckedChange: (Boolean) -> Unit, label: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
        Text(
            text = label,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar() {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Search") },
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(56.dp),
            textStyle = TextStyle(fontSize = 18.sp),
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = "Search Icon"
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                disabledTextColor = Color.Gray,
                errorTextColor = Color.Red,
                containerColor = Color.Transparent,
                cursorColor = Color.Black,
                focusedBorderColor = Color.Gray,
                unfocusedBorderColor = Color.LightGray,
                focusedPlaceholderColor = Color.Gray,
                unfocusedPlaceholderColor = Color.Gray
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SearchBarPreview() {
    SearchBar()
}

@Preview(showBackground = true)
@Composable
fun FilterScreenPreview() {
    FilterScreen()
}