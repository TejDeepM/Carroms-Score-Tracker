package com.example.carromsscoretracker

import android.os.Bundle
import android.widget.ProgressBar
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.carromsscoretracker.ui.theme.CarromsScoreTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CarromsScoreTrackerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    App()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(modifier: Modifier = Modifier) {

    val team1Name = "Team 1"
    val team2Name = "Team 2"

    var team1Scores by rememberSaveable { mutableStateOf(arrayOf(0, 0)) }
    var team2Scores by rememberSaveable { mutableStateOf(arrayOf(0, 0)) }

    var focusedTeam by rememberSaveable { mutableStateOf(1) }
    var focusedIndex by rememberSaveable { mutableStateOf(0) }

    var rows by rememberSaveable { mutableStateOf(2) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState()),
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {

        Spacer(modifier = Modifier.height(40.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {


            Column(modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    team1Name,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
                Spacer(modifier = Modifier.height(20.dp))
                for (i in (0..rows - 1)) {
                    ScoresButton({ focusedTeam = 1; focusedIndex = i }, team1Scores[i])
                }
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = {},
                    modifier = Modifier
                        .size(80.dp, 60.dp),
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary)
                ) {
                    Text(team1Scores.sum().toString(), fontSize = 25.sp, color = MaterialTheme.colorScheme.onSecondary)
                }
            }

            Column(modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    team2Name,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
                Spacer(modifier = Modifier.height(20.dp))
                for (i in (0..rows - 1)) {
                    ScoresButton({ focusedTeam = 2; focusedIndex = i }, team2Scores[i])
                }
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = {},
                    modifier = Modifier
                        .size(80.dp, 60.dp),
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary)
                ) {
                    Text(team2Scores.sum().toString(), fontSize = 25.sp, color = MaterialTheme.colorScheme.onSecondary)
                }
            }

        }

        Spacer(modifier = Modifier.height(60.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            CircularProgressIndicator(
                (team1Scores.sum().toFloat() / 29.toFloat()),
                modifier = Modifier
                    .size(80.dp)
                ,
                color = MaterialTheme.colorScheme.secondary,
                trackColor = MaterialTheme.colorScheme.secondaryContainer
            )
            CircularProgressIndicator(
                (team2Scores.sum().toFloat() / 29.toFloat()),
                modifier = Modifier
                    .size(80.dp),
                color = MaterialTheme.colorScheme.secondary,
                trackColor = MaterialTheme.colorScheme.secondaryContainer
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Column(modifier = Modifier) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Spacer(modifier = Modifier.width(10.dp))
                ActionButtons("+5") {
                    val updatedScores = when (focusedTeam) {
                        1 -> team1Scores.copyOf() // Create a copy of team1Scores
                            .also { it[focusedIndex] += 5 } // Update the element at focusedIndex
                        2 -> team2Scores.copyOf()
                            .also { it[focusedIndex] += 5 }

                        else -> team1Scores // Default case
                    }
                    when (focusedTeam) {
                        1 -> team1Scores = updatedScores
                        2 -> team2Scores = updatedScores
                    }
                }
                ActionButtons("+1") {
                    val updatedScores = when (focusedTeam) {
                        1 -> team1Scores.copyOf() // Create a copy of team1Scores
                            .also { it[focusedIndex] += 1 } // Update the element at focusedIndex
                        2 -> team2Scores.copyOf()
                            .also { it[focusedIndex] += 1 }

                        else -> team1Scores // Default case
                    }
                    when (focusedTeam) {
                        1 -> team1Scores = updatedScores
                        2 -> team2Scores = updatedScores
                    }
                }
                ActionButtons(contains = "image") {
                    val updatedScores = when (focusedTeam) {
                        1 -> team1Scores.copyOf() // Create a copy of team1Scores
                            .also { it[focusedIndex] = 0 } // Update the element at focusedIndex
                        2 -> team2Scores.copyOf()
                            .also { it[focusedIndex] = 0 }

                        else -> team1Scores // Default case
                    }
                    when (focusedTeam) {
                        1 -> team1Scores = updatedScores
                        2 -> team2Scores = updatedScores
                    }
                }


                Spacer(modifier = Modifier.width(10.dp))
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly) {
                ActionButtons("+") {
                    team1Scores += 0
                    team2Scores += 0
                    rows += 1
                }
                ActionButtons("-") {
                    team1Scores.dropLast(1)
                    team2Scores.dropLast(1)
                    rows -= 1
                }
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

    }
}





@Composable
private fun ScoresButton(onclick: () -> Unit, score: Int) {
    Button(
        onClick = onclick,
        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
        modifier = Modifier
            .size(width = 80.dp, height = 60.dp)

    ) {
        Text(
            text = score.toString(),
            fontSize = 25.sp,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
    Spacer(modifier = Modifier.height(20.dp))
}



@Composable
private fun ActionButtons(
    text: String = "",
    theme: Color = MaterialTheme.colorScheme.tertiary,
    fontTheme: Color = MaterialTheme.colorScheme.onTertiary,
    fontSize: Int = 25,
    contains: String = "text",
    onclick: () -> Unit
) {
    Button(
        modifier = Modifier
            .shadow(10.dp, RoundedCornerShape(20.dp))
            .size(80.dp, 60.dp),
        colors = ButtonDefaults.buttonColors(theme),
        onClick = onclick
    ) {
        when(contains) {
            "text" -> Text(
                text,
                fontSize = fontSize.sp,
                color = fontTheme
            )
            "image" -> {
                val darkTheme = isSystemInDarkTheme()
                Image(
                    painter = painterResource(R.drawable.reset_regular_24),
                    contentDescription = null,
                    modifier = Modifier
                        .size(25.dp),
                    colorFilter = when (darkTheme) {
                        false -> ColorFilter.colorMatrix(
                            ColorMatrix(
                                floatArrayOf(
                                    -1f, 0f, 0f, 0f, 255f,
                                    0f, -1f, 0f, 0f, 255f,
                                    0f, 0f, -1f, 0f, 255f,
                                    0f, 0f, 0f, 1f, 0f
                                )
                            )
                        )

                        true -> null
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CarromsScoreTrackerTheme {
        App()
    }
}