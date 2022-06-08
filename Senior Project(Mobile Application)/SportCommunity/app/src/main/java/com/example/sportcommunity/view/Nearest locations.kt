package com.example.sportcommunity.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sportcommunity.ui.theme.GreenColor
import com.example.sportcommunity.ui.theme.ItemBgColor


@Preview
@Composable
fun ShowNearest() {

    Card(
        modifier = Modifier .padding(horizontal = 18.dp)
            .padding(top = 10.dp, bottom = 8.dp).clip(RoundedCornerShape(25.dp)),
        elevation = 5.dp,
        backgroundColor = ItemBgColor,
        shape = RoundedCornerShape(15.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(0.1f),
                horizontalAlignment = Alignment.End
            ) {
                Icon(
                    imageVector = Icons.Default.MyLocation,
                    contentDescription = "",
                    tint = GreenColor
                )
            }
            Text(
                text = "Show nearest",
                color = GreenColor,
                modifier = Modifier
                    .weight(0.7f)
                    .padding(start = 15.dp),
                // fontFamily = FontFamily(Font(R.font.poppins_medium)),
                fontSize = 16.sp
            )
            val nearest = remember { mutableStateOf(false) }

            Switch(
                checked = nearest.value,
                onCheckedChange = {
                    nearest.value = it

                                  },
                colors = SwitchDefaults.colors(GreenColor),
                modifier = Modifier.weight(0.2f)
            )


        }
    }


}