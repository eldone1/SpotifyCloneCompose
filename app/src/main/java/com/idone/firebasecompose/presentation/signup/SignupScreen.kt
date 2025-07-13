package com.idone.firebasecompose.presentation.signup

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.idone.firebasecompose.R
import com.idone.firebasecompose.ui.theme.Black
import com.idone.firebasecompose.ui.theme.SelectedField
import com.idone.firebasecompose.ui.theme.UnselectedField


@Composable
fun SignupScreen(auth : FirebaseAuth){

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Black)
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row (){
            Icon(painter = painterResource(id = R.drawable.ic_back ),
                contentDescription = null,
                modifier = Modifier
                    .padding(vertical = 24.dp)
                    .size(24.dp),
                tint = White

            )
            Spacer(modifier = Modifier.weight(1f))
        }
        Text(
            text = "Email or username",
            color = White,
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp
        )
        TextField(value = email,
            onValueChange = {email = it},
            modifier = Modifier
                .fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = UnselectedField,
                focusedContainerColor = SelectedField
            )
        )

        Spacer(Modifier.height(48.dp))

        Text(
            text = "Password",
            color = White,
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp
        )
        TextField(value = password,
            onValueChange = {password = it},
            modifier = Modifier
                .fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = UnselectedField,
                focusedContainerColor = SelectedField
            ))
        Spacer(Modifier.height(48.dp))
        Button(onClick = {
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if(task.isSuccessful){
                    //navegar
                    Log.i("done", "Registro correcto")
                }else{
                    //error
                    Log.i("done", "Registro incorrecto")
                }
            }
        }, modifier = Modifier,
            colors = ButtonDefaults.buttonColors(
                containerColor = UnselectedField)
        ){
            Text(
                text = "Sign Up",
            )
        }
    }


}