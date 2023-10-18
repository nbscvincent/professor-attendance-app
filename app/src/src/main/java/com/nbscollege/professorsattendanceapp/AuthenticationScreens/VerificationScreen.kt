@file:OptIn(ExperimentalMaterial3Api::class)

package com.nbscollege.professorsattendanceapp.AuthenticationScreens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Normal
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OTPScreen() {
    var otp by remember { mutableStateOf("") }
    val inputFields = (0 until 6).map { index -> otp.getOrNull(index)?.toString() ?: "" }

    Scaffold(
        modifier = Modifier.background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(start = 25.dp, end = 25.dp, top = 35.dp, bottom = 35.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(45.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Almost there",
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 55.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )

                Text(buildAnnotatedString {
                    withStyle(style = SpanStyle(
                        fontWeight = FontWeight.Normal,
                        fontSize = 24.sp)) {
                        append("Please enter the 6-digit code sent to your email ")
                    }
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 24.sp
                        )
                    ) {
                        append("test@gmail.com")
                    }
                    withStyle(style = SpanStyle(
                        fontWeight = FontWeight.Normal,
                        fontSize = 24.sp)) {
                        append(" for verification. ")
                    }
                }
                )
            }

            Row(
                modifier = Modifier
                    .padding(vertical = 40.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                for (i in 0 until 6) {
                    OTPDigitField(
                        value = inputFields[i],
                        onValueChange = { input ->
                            if (input.length <= 1) {
                                  otp = otp.toMutableList().apply {
                                }.joinToString(separator = "")
                            }
                        }
                    )
                }
            }

            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(15),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red,
                    contentColor = Color.White
                ),
                onClick = {}
            ){
                Text(
                    modifier = Modifier.padding(vertical = 4.dp, horizontal = 0.dp),
                    text = "Verify",
                    color = Color.White,
                    fontSize = 30.sp,
                    fontWeight = SemiBold
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 35.dp, bottom = 2.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "Didn't receive any code?",
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold
                    )
                )


                ClickableTextWithCountdown(timerInSeconds = 45)



            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OTPDigitField(
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        modifier = Modifier
            .size(56.dp)
            .padding(horizontal = 2.dp),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number),
        textStyle = LocalTextStyle.current.copy(
            fontSize = 24.sp,
            textAlign = TextAlign.Center)
    )
}

@Composable
fun ClickableTextWithCountdown(timerInSeconds: Int) {
    var countdownTime by remember { mutableStateOf(timerInSeconds) }
    val timerState = remember { mutableStateOf("00:${countdownTime.toString().padStart(2, '0')}") }
    LaunchedEffect(countdownTime) {
        while (countdownTime > 0) {
            delay(1000)
            countdownTime--
            timerState.value = "00:${countdownTime.toString().padStart(2, '0')}"
        }
    }

    Text(
        buildAnnotatedString {
            withStyle(style = SpanStyle(color = Color.Black, fontWeight = Normal)) {
                append("Send new code ")
            }
            withStyle(style = SpanStyle(color = Color.Black, fontWeight = SemiBold)) {
                append(timerState.value + "s")
            }
            Modifier.clickable {
                // Handle click action here

            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun OTPScreenPreview() {
}
