package com.nbscollege.professorsattendanceapp.AuthenticationScreens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shin.myproject.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen() {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth()
            .padding(25.dp)
            .wrapContentSize(Alignment.Center)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .fillMaxWidth()
                .padding(horizontal = 25.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Logo()

            Text(
                text = "Get Started",
                style = TextStyle(fontSize = 30.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .padding(top = 16.dp)
            )
            Text(
                text = "by creating free account.",
                style = TextStyle(fontSize = 17.sp),
                modifier = Modifier
                    .padding(top = 8.dp)
            )

            FullnameTextField()
            EmailTextField()
            PhoneNumberTextField()
            PasswordTextField()
            TermsandConditionCheckBox()
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .fillMaxWidth()
                .padding(horizontal = 25.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NextButton()
            AlreadyMember()
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FullnameTextField() {
    var fullname by remember { mutableStateOf("") }
    var isFullnameValid by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = fullname,
        onValueChange = { newFullname ->
            fullname = newFullname
            isFullnameValid = newFullname.isNotEmpty()
        },
        label = {
            Text(
                text = "Fullname",
                color = if (isFullnameValid) Color.Red else Color.Gray
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.Transparent,
            focusedIndicatorColor = Color.Red,
            unfocusedIndicatorColor = Color.Gray
        ),
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Email",
                tint = if (isFullnameValid) Color.Red else Color.Gray
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 25.dp)
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailTextField() {
    var email by remember { mutableStateOf("") }
    var isEmailValid by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = email,
        onValueChange = { newEmail ->
            email = newEmail
            isEmailValid = newEmail.isNotEmpty()
        },
        label = {
            Text(
                text = "Email",
                color = if (isEmailValid) Color.Red else Color.Gray
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.Transparent,
            focusedIndicatorColor = Color.Red,
            unfocusedIndicatorColor = Color.Gray
        ),
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.MailOutline,
                contentDescription = "Valid Email",
                tint = if (isEmailValid) Color.Red else Color.Gray
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 25.dp)
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhoneNumberTextField() {
    var phone = remember { mutableStateOf("") }
    var isPhoneValid by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = phone.value,
        onValueChange = { newPhone ->
            phone.value = newPhone
            isPhoneValid = newPhone.isNotEmpty()
        },
        label = {
            Text(
                text = "Phone Number",
                color = if (isPhoneValid) Color.Red else Color.Gray
            )
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            containerColor = Color.Transparent,
            focusedBorderColor = if (phone.value.matches(Regex("\\d{11}"))) Color.Gray else Color.Red,
            unfocusedBorderColor = Color.Gray,
            focusedLabelColor = if (phone.value.matches(Regex("\\d{11}"))) Color.Gray else Color.Red,
            unfocusedLabelColor = Color.Gray
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        trailingIcon = {
            Icon(
                painter = painterResource(R.drawable.phone),
                contentDescription = "phone logo",
                tint = if (phone.value.matches(Regex("\\d{10}"))) Color.Red else Color.Gray
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 25.dp)
    )
}

@Composable
fun TermsandConditionCheckBox(){
    val isChecked = remember { mutableStateOf(false)}

    val termsAndConditionsText = buildAnnotatedString {
        withStyle(style = SpanStyle(color = Color.Black)) {
            append("By checking the box, you agree to our ")
        }
        pushStyle(style = SpanStyle(color = if (isChecked.value) Color.Red else Color.Black))
        append("Terms ")
        pushStyle(style = SpanStyle(color = Color.Black))
        append("and ")
        pushStyle(style = SpanStyle(color = if (isChecked.value) Color.Red else Color.Black))
        append("Conditions.")
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 25.dp, vertical = 15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = isChecked.value,
            colors = CheckboxDefaults.colors(
                checkedColor = Color.Red,
                uncheckedColor = Color.Black,
                checkmarkColor = Color.White
            ),
            onCheckedChange = { isChecked.value = it }
        )
        ClickableText(
            text = termsAndConditionsText,
            onClick = { offset ->
                if (offset >= termsAndConditionsText.length - 18) {
                    // Handle click on "Terms and Conditions" text
                }
            }
        )
    }
}


@Composable
fun AlreadyMember(){
    Row(
        modifier = Modifier
            .padding(horizontal = 25.dp, vertical = 3.dp)
            .width(150.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Already a member?",
            style = TextStyle(fontSize = 17.sp),
            modifier = Modifier
        )
        ClickableText(
            text = AnnotatedString("Log In"),
            onClick = { },
            style = TextStyle(
                color = Color.Red,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )
        )
    }
}