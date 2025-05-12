package com.meally.meally.common.components.datePicker

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.meally.meally.R
import com.meally.meally.common.components.BasicText
import com.meally.meally.common.theme.Typography
import com.meally.meally.common.time.util.isToday
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun DatePickerInput(
    selectedDate: LocalDate,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    formatPattern: String = "EEE, dd/MM/yyyy",
    textStyle: TextStyle = Typography.body1.copy(
        color = MaterialTheme.colorScheme.onBackground,
    ),
) {

    val dateFormatter by remember(formatPattern) { mutableStateOf(DateTimeFormatter.ofPattern(formatPattern)) }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clip(RoundedCornerShape(100.dp))
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = RoundedCornerShape(100.dp)
            )
            .background(MaterialTheme.colorScheme.background, RoundedCornerShape(100.dp))
            .clickable { onClick() }
            .padding(vertical = 12.dp)
            .padding(end = 12.dp, start = 4.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_down_menu),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.size(24.dp)
            )

            BasicText(
                text = if (selectedDate.isToday()) "Today" else dateFormatter.format(selectedDate),
                style = textStyle,
            )
        }

    }
}