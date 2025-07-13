package com.idone.firebasecompose.presentation.initial

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.idone.firebasecompose.R
import com.idone.firebasecompose.ui.theme.BackgroundButton
import com.idone.firebasecompose.ui.theme.Black
import com.idone.firebasecompose.ui.theme.Gray
import com.idone.firebasecompose.ui.theme.Green
import com.idone.firebasecompose.ui.theme.ShapeButton

@Preview
@Composable
fun InitialScreen(navigateToLogin: () -> Unit = {}, navigateToSignUp: () -> Unit = {}) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Gray, Black),
                    startY = 0f, endY = 700f
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(
            modifier = Modifier
                .weight(1f)
        )

        Image(
            painter = painterResource(id = R.drawable.spotify),
            contentDescription = null,
            modifier = Modifier.clip(CircleShape)
        )

        Spacer(
            modifier = Modifier
                .weight(1f)
        )

        Text(
            text = "Millions of Songs.",
            color = Color.White,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Free on Spotify.",
            color = Color.White,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier
                .weight(1f)
        )

        Button(
            onClick = { navigateToSignUp() }, modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(horizontal = 32.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Green)
        ){
            Text(
                text = "Sign up free",
                color = Black
            )
        }
        Spacer(
            modifier = Modifier
                .height(8.dp)
        )
        CustomButton(Modifier.clickable{}, painterResource(id = R.drawable.google), "Continue with Google")
        Spacer(
            modifier = Modifier
                .height(8.dp)
        )
        CustomButton(Modifier.clickable{}, painterResource(id = R.drawable.facebook), "Continue with Facebook")
        Text(
            text = "Log in",
            color = Color.White,
            modifier = Modifier
                .padding(24.dp)
                .clickable { navigateToLogin() },
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier
                .weight(1f)
        )
    }
}

@Composable
fun CustomButton(modifier: Modifier, painter: Painter, title: String){
    Box(modifier
        .fillMaxWidth()
        .height(48.dp)
        .padding(horizontal = 32.dp)
        .background(BackgroundButton)
        .border(2.dp, ShapeButton, CircleShape),
        contentAlignment = Alignment.CenterStart
    ){
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .padding(start = 16.dp)
                .size(16.dp)
        )
        Text(
            text = title,
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )

    }
}
