package com.affise.app.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AffiseSwitch(
    checked: MutableState<Boolean>,
    modifier: Modifier = Modifier,
    label: String = "",
    onCheckedChange: (Boolean) -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth(1f)
            .clickable {
                checked.value = !checked.value
                onCheckedChange.invoke(checked.value)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Switch(
            modifier = Modifier.padding(end = 16.dp),
            checked = checked.value,
            onCheckedChange = {
                checked.value = it
                onCheckedChange.invoke(it)
            }
        )
        Text(label)
    }

}