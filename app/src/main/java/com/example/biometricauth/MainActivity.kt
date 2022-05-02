package com.example.biometricauth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.biometricauth.ui.theme.BiometricAuthTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BiometricAuthTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Auth()
                }
            }
        }
    }
}

@Composable
fun Auth() {
    var auth by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
                //Condicional en el color
            .background(if (auth) Color.Green else Color.Cyan)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        //Conetnidos
        //Condicinal en el text
        Text(if(auth) "Estás autenticado" else "Necesitas autenticarte", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(8.dp))
        Button(onClick = {
            //Button
        }) {
            //Condicional en el texto del boton
            Text(if (auth) "Cerrar" else "Autenticar")
        }
    }
}

@Preview(showSystemUi = true) //completa vista
@Composable
fun DefaultPreview() {
    BiometricAuthTheme {
        Auth()
    }
}