package kitty.cheshire.recents.fixer.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.work.WorkInfo
import kitty.cheshire.recents.fixer.R
import kitty.cheshire.recents.fixer.ui.MainViewModel
import kitty.cheshire.recents.fixer.ui.theme.RecentsFixerTheme
import org.koin.androidx.compose.inject

@Composable
fun MainScreenContent() {
    val mainViewModel: MainViewModel by inject()

    val workState by mainViewModel.workerState.observeAsState()

    Column(Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.66f)
                .background(MaterialTheme.colorScheme.secondaryContainer),
            contentAlignment = Alignment.Center
        ) {
            val icon = if (workState !in listOf(WorkInfo.State.ENQUEUED, WorkInfo.State.RUNNING)) {
                Icons.Filled.Info
            } else {
                Icons.Filled.Settings
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                WorkStateIcon(state = workState)
                Text(
                    text = stringResource(id = getWorkerStateNiceText(workState)),
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 24.dp)
                )
            }
        }
        Column(
            modifier = Modifier
                .weight(0.33f)
                .background(MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.Center
        ) {
            ExtendedFloatingActionButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                text = { Text(text = stringResource(id = R.string.kill_now)) },
                onClick = {
                    mainViewModel.justKillPixelLauncher()
                }
            )
            val btnText =  if (workState !in listOf(WorkInfo.State.ENQUEUED, WorkInfo.State.RUNNING)) {
                stringResource(id = R.string.start_work)
            } else {
                stringResource(id = R.string.stop_work)
            }
            ExtendedFloatingActionButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 48.dp, start = 24.dp, end = 24.dp),
                text = { Text(text = btnText) },
                onClick = {
                    mainViewModel.toggleWorkerTask()
                }
            )
        }
    }
}

@Composable
fun WorkStateIcon(state: WorkInfo.State?) {
    val iconModifier = Modifier.size(150.dp)

    var currentRotation by remember { mutableStateOf(0f) }
    val rotation = remember { Animatable(currentRotation) }

    if (state in listOf(WorkInfo.State.ENQUEUED, WorkInfo.State.RUNNING)) {
        LaunchedEffect(null) {
            rotation.animateTo(
                targetValue = currentRotation + 360f,
                animationSpec = infiniteRepeatable(
                    animation = tween(5000, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart
                )
            ) {
                currentRotation = value
            }
        }
    }

    Icon(
        imageVector = Icons.Filled.Settings,
        contentDescription = state?.name,
        tint = MaterialTheme.colorScheme.onSecondaryContainer,
        modifier = iconModifier.rotate(rotation.value)
    )
}

private fun getWorkerStateNiceText(state: WorkInfo.State?) =
    when (state) {
        WorkInfo.State.ENQUEUED,
        WorkInfo.State.RUNNING -> R.string.work_state_running
        else -> R.string.work_state_not_available
    }

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun PreviewActiveWorkStateIcon() = RecentsFixerTheme {
    WorkStateIcon(WorkInfo.State.RUNNING)
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun PreviewMainScreenContent() = RecentsFixerTheme {
    MainScreenContent()
}