package com.example.biometricauth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
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
import androidx.core.content.ContextCompat
import com.example.biometricauth.ui.theme.BiometricAuthTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BiometricAuthTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) { //Llamarlo y reflejarlo en el DefaultPreview
                    Auth()
                }
            }
        }
        //Llamar Setup
        setupAuth()
    }
    //PRIVATE METHODS
    private var canAuthenticate = false
    private lateinit var promptInfo: BiometricPrompt.PromptInfo //Ya que es una variable en que definiremos en tiempo de ejecución
    //se le pondrá un lateinit, como los 'late' en Flutter

    private fun setupAuth(){ //BiometricManager debe ser de Androidx..
        if (BiometricManager.from(this).canAuthenticate(
                BiometricManager.Authenticators.BIOMETRIC_STRONG
            or BiometricManager.Authenticators.DEVICE_CREDENTIAL) == BiometricManager.BIOMETRIC_SUCCESS){
            canAuthenticate = true

            promptInfo = BiometricPrompt.PromptInfo.Builder()
                .setTitle("Autenticación biométrica")
                .setSubtitle("Autenticate utilizando el sensor biométrico")
                .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG
                        or BiometricManager.Authenticators.DEVICE_CREDENTIAL)
                .build()
        }
    }
    private fun authenticate(auth: (auth:Boolean) -> Unit){
        if (canAuthenticate){
            BiometricPrompt(this, ContextCompat.getMainExecutor(this),
            object : BiometricPrompt.AuthenticationCallback(){
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    auth(true)
                }
            }).authenticate(promptInfo)
        } else{
            auth(true)
        }
    }

//COMPOSABLE
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
                if (auth){
                    auth = false
                }
                authenticate {
                    auth = it
                }
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
}
