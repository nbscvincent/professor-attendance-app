package com.shin.myproject.screens.main.mainScreen.subject.screen.addSubjectScreen.component

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun calendar(){
    val calendarState = rememberSheetState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        CalendarDialog(
            state = calendarState,
            config = CalendarConfig(
                monthSelection = true,
                yearSelection = true,
                style = CalendarStyle.MONTH,
                disabledDates = listOf(LocalDate.now()/*plusDays(7)*/)
            ),
            selection = CalendarSelection.Date { date ->
                Log.d("Selected Date", "$date")
            }
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OutlinedCard(

            ) {
                //TODO : Display the selected Date  here
                Modifier.weight(1f)
            }

            Button(
                onClick = { calendarState.show() }
            ) {
                Text(text = "Select Date")
            }
        }
    }
}