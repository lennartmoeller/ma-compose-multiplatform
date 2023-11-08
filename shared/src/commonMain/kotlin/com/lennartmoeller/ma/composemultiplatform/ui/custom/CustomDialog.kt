package com.lennartmoeller.ma.composemultiplatform.ui.custom

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.lennartmoeller.ma.composemultiplatform.ui.util.ScreenWidthBreakpoint
import kotlinx.coroutines.launch

@Composable
fun CustomDialog(
    onClose: () -> Unit,
    onSave: () -> Unit,
    title: String,
    content: @Composable () -> Unit,
) {
    val dialogState = remember { CustomDialogState(onClose, onSave, title, content) }
    dialogState.RenderDialog()
}

class CustomDialogState(
    private val onClose: () -> Unit,
    private val onSave: () -> Unit,
    private val dialogTitle: String,
    private val dialogContent: @Composable () -> Unit,
) {
    companion object {
        val maxDialogContainerWidth: Dp = 560.dp
    }

    @Composable
    fun RenderDialog() {
        val animationDuration = 200
        val animateTrigger = remember { mutableStateOf(false) }
        LaunchedEffect(key1 = Unit) {
            launch {
                animateTrigger.value = true
            }
        }
        Dialog(
            onDismissRequest = onClose,
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            ScreenWidthBreakpoint(
                maxDialogContainerWidth,
                smallDeviceContent = {
                    AnimatedVisibility(
                        visible = animateTrigger.value,
                        enter = slideIn(
                            animationSpec = tween(animationDuration, easing = EaseOut)
                        ) {
                            IntOffset(0, it.height)
                        } + fadeIn(
                            animationSpec = tween(animationDuration)
                        ),
                        content = { ThinDeviceDialogContent() }
                    )
                },
                largeDeviceContent = {
                    AnimatedVisibility(
                        visible = animateTrigger.value,
                        enter = scaleIn(
                            animationSpec = tween(animationDuration, easing = EaseOut),
                            initialScale = .9f,
                        ) + fadeIn(
                            animationSpec = tween(animationDuration)
                        ),
                        content = { WideDeviceDialogContent() }
                    )
                }
            )
        }
    }

    @Composable
    fun ThinDeviceDialogContent() {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .fillMaxSize()
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .height(56.dp)
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    IconButton(onClick = onClose) {
                        CustomIcon(
                            unicode = "\uf00d",
                            style = RegularStyle(),
                            size = 22.sp,
                        )
                    }
                    Text(
                        dialogTitle,
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.titleLarge
                    )
                    TextButton(onClick = onSave) {
                        Text("Speichern")
                    }
                }
            }
        }
    }

    @Composable
    fun WideDeviceDialogContent() {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(24.dp))
                .background(MaterialTheme.colorScheme.surface)
                .padding(24.dp)
                .width(maxDialogContainerWidth)
        ) {
            Column {
                Text(
                    dialogTitle,
                    style = MaterialTheme.typography.titleLarge
                )
                Box(modifier = Modifier.padding(top = 16.dp)) {
                    dialogContent()
                }
                Row(
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                ) {
                    TextButton(
                        onClick = onClose
                    ) {
                        Text("Verwerfen")
                    }
                    TextButton(
                        onClick = onSave
                    ) {
                        Text("Speichern")
                    }
                }
            }
        }
    }

}
