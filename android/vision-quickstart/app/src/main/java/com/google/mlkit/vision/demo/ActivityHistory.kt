package com.google.mlkit.vision.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.mlkit.vision.demo.data.RepItem
import com.google.mlkit.vision.demo.ui.theme.RepsTrackerTheme
import java.text.SimpleDateFormat
import java.util.Locale

class ActivityHistory : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            RepsTrackerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")

                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RepsTrackerTheme {
        Greeting("Android")
    }
}


@Composable
fun RepItemsScreen(
    repItems: List<RepItem>,
    modifier: Modifier = Modifier,
    onScheduleClick: ((String) -> Unit)? = null,
) {
//    val stopNameText = if (stopName == null) {
//        stringResource(R.string.stop_name)
//    } else {
//        "$stopName ${stringResource(R.string.route_stop_name)}"
//    }

    val stopNameText = "Woho"

    Column(modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
//                .padding(dimensionResource(R.dimen.padding_medium)),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(stopNameText)
//            Text(stringResource(R.string.arrival_time))
            Text("hey")
        }
        Divider()
        ActivityDetails(
            repItems = repItems,
//            onScheduleClick = onScheduleClick
        )
    }
}

/*
* Composable for BusScheduleDetails which show list of bus schedule
* When [onScheduleClick] is null, [stopName] is replaced with placeholder
* as it is assumed [stopName]s are the same as shown
* in the list heading display in [BusScheduleScreen]
*/
@Composable
fun ActivityDetails(
    repItems: List<RepItem>,
    modifier: Modifier = Modifier,
//    onScheduleClick: ((String) -> Unit)? = null
) {
    LazyColumn(modifier = modifier, contentPadding = PaddingValues(vertical = 8.dp)) {
        items(
            count = repItems.size,
//            items = repItems,
//            key = { (repItem: RepItem) -> repItem.id }
        ) { repItem ->
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
//                    .clickable(enabled = onScheduleClick != null) {
//                        onScheduleClick?.invoke(repItem.)
//                    },
//                    .padding(
//                        vertical = dimensionResource(R.dimen.padding_medium),
//                        horizontal = dimensionResource(R.dimen.padding_medium)
//                    ),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
//                if (onScheduleClick == null) {
                    Text(
                        text = "--",
                        style = MaterialTheme.typography.bodyLarge.copy(
//                            fontSize = dimensionResource(R.dimen.font_large).value.sp,
                            fontWeight = FontWeight(300)
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f)
                    )
//                } else {
//                    Text(
//                        text = schedule.stopName,
//                        style = MaterialTheme.typography.bodyLarge.copy(
//                            fontSize = dimensionResource(R.dimen.font_large).value.sp,
//                            fontWeight = FontWeight(300)
//                        )
//                    )
//                }
//                Text(
//                    text = SimpleDateFormat("h:mm a", Locale.getDefault())
//                        .format(Date(schedule.arrivalTimeInMillis.toLong() * 1000)),
//                    style = MaterialTheme.typography.bodyLarge.copy(
//                        fontSize = dimensionResource(R.dimen.font_large).value.sp,
//                        fontWeight = FontWeight(600)
//                    ),
//                    textAlign = TextAlign.End,
//                    modifier = Modifier.weight(2f)
//                )
            }
        }
    }
}
