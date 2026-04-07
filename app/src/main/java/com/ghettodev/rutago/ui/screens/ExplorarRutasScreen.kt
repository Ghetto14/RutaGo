package com.ghettodev.rutago.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ghettodev.rutago.ui.components.MapItem

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun ParadasCercanas(){
    //atributos o variables de la clase
    var text by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    //estructura general
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        //mapa
        MapItem()
        //estructura vertical
        Column(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center
        )
        {
            SearchBar(
                query = text,
                onQueryChange = { text = it },
                onSearch = {
                    active = false
                    //ejecutar logica de busqueda
                },
                active = active,
                onActiveChange = { active = it},
                placeholder = { Text("Buscar destino") },
                leadingIcon = {
                    Icon(Icons.Default.Search,
                        contentDescription = "Barra de busqueda")
                },
                trailingIcon = {
                    if (active) {
                        IconButton(onClick = { if (text.isNotEmpty()) text = "" else active = false }) {
                            Icon(Icons.Default.Close, contentDescription = "Cerrar")
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
            ){
                //contenido que se muestra debajo de la barra cuando 'active' es true

            }



        }
    }
}