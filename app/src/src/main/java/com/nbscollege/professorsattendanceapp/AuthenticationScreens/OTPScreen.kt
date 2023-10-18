@file:OptIn(ExperimentalMaterial3Api::class)

package com.shin.mydrafts.AuthenticationScreens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OTPScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(25.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 25.dp),
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
            PleaseEnter()
        }
        OtpInputRow()
        VerifyButton()
        Text(
            text = "Didn't recieve any code?",
            style = TextStyle(fontSize = 17.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier
                .padding(horizontal = 25.dp, vertical = 5.dp)
        )
        SendNewCode()
    }
}


@Composable
fun PleaseEnter(){
    val EnterOTPText = buildAnnotatedString {
        withStyle(style = SpanStyle(color = Color.Black, fontSize = 17.sp)) {
            append("Please enter 6-digit code sent to your email ")
        }
        pushStyle(style = SpanStyle(color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 17.sp))
        append("test@gmai.com ")
        pushStyle(style = SpanStyle(color = Color.Black, fontSize = 17.sp))
        append("for verification.")
    }

    Text(
        text = EnterOTPText
    )
}


@Composable
fun OtpInputRow() {
    val otp = remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .padding(horizontal = 25.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        repeat(6) { index ->
            val borderColor = if (otp.value.length > index) Color.Red else Color.Gray

            OutlinedTextField(
                value = otp.value.getOrNull(index)?.toString() ?: "",
                onValueChange = { newOtp ->
                    if (newOtp.length <= 1 && index < otp.value.length) {
                        otp.value = otp.value.replaceRange(index, index + 1, newOtp)
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 4.dp, vertical = 25.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                keyboardActions = KeyboardActions(onDone = { /* Handle done action if needed */ }),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Red,
                    unfocusedBorderColor = Color.Gray
                )
            )
        }
    }
}


@Composable
fun VerifyButton() {
    Button(
        onClick = { /* Handle button click here */ },
        colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .width(450.dp)
            .padding(vertical = 5.dp, horizontal = 25.dp)
    ) {
        Row(modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Next", color = Color.White, fontSize = 14.sp)
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Next",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}


@Composable
fun SendNewCode() {
    var cooldownSeconds by remember { mutableStateOf(0) }
    var cooldownText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 25.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ClickableText(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = if (cooldownSeconds <= 0) Color.Red else Color.Gray)) {
                    append("Send new code")
                }
            },
            onClick = {
                if (cooldownSeconds <= 0) {
                    cooldownSeconds = 30
                } else if (cooldownSeconds <= 120) {
                    cooldownSeconds += 15
                } else {
                    cooldownText = "Try again in one hour"
                }
            }
        )
        Text(text = cooldownText)
    }

    LaunchedEffect(cooldownSeconds) {
        if (cooldownSeconds > 0) {
            while (cooldownSeconds > 0) {
                delay(1000)
                cooldownSeconds--
                if (cooldownSeconds > 0) {
                    cooldownText = "Cooldown: $cooldownSeconds seconds"
                } else {
                    cooldownText = ""
                }
            }
        }
    }
}