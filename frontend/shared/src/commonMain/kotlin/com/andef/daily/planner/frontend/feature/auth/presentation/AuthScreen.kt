package com.andef.daily.planner.frontend.feature.auth.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.andef.daily.planner.frontend.core.design.Black
import com.andef.daily.planner.frontend.core.design.Blue
import com.andef.daily.planner.frontend.core.design.GrayForLight
import com.andef.daily.planner.frontend.core.design.Red
import com.andef.daily.planner.frontend.core.design.White
import com.andef.daily.planner.frontend.core.design.button.ui.UiButton
import com.andef.daily.planner.frontend.core.design.loading.ui.UiLoading
import com.andef.daily.planner.frontend.core.design.textButtonShape
import com.andef.daily.planner.frontend.core.design.textfield.ui.UiTextField
import com.andef.daily.planner.frontend.resources.Res
import com.andef.daily.planner.frontend.resources.lock
import com.andef.daily.planner.frontend.resources.person
import org.koin.compose.viewmodel.koinViewModel
import org.jetbrains.compose.resources.painterResource

/**
 * Экран аутентификации
 */
@Composable
fun AuthScreen(
    onAuthenticated: () -> Unit,
    viewModel: AuthViewModel = koinViewModel()
) {
    val state = viewModel.state.collectAsState().value

    AuthContent(
        state = state,
        onIntent = { intent ->
            viewModel.send(
                when (intent) {
                    is AuthIntent.Submit -> AuthIntent.Submit(onAuthenticated)
                    else -> intent
                }
            )
        }
    )
    UiLoading(isVisible = state.loading, isLightTheme = true)
}

/**
 * Содержимое экрана аутентификации
 */
@Composable
private fun AuthContent(state: AuthState, onIntent: (AuthIntent) -> Unit) {
    val personIcon = painterResource(Res.drawable.person)
    val lockIcon = painterResource(Res.drawable.lock)
    val submit = { onIntent(AuthIntent.Submit {}) }

    Surface(modifier = Modifier.fillMaxSize(), color = White) {
        Box(
            modifier = Modifier.fillMaxSize().padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .widthIn(max = 430.dp)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Ежедневник",
                    color = Black,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(30.dp))
                AuthModeSelector(
                    selectedMode = state.mode,
                    onModeChange = { onIntent(AuthIntent.ModeChange(it)) }
                )
                Spacer(Modifier.height(26.dp))
                UiTextField(
                    isLightTheme = true,
                    value = state.login,
                    onValueChange = { onIntent(AuthIntent.LoginChange(it)) },
                    modifier = Modifier.fillMaxWidth().height(authControlHeight),
                    placeholderText = "Логин",
                    leadingIcon = personIcon,
                    contentDescription = "Логин",
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
                )
                Spacer(Modifier.height(14.dp))
                UiTextField(
                    isLightTheme = true,
                    value = state.password,
                    onValueChange = { onIntent(AuthIntent.PasswordChange(it)) },
                    modifier = Modifier.fillMaxWidth().height(authControlHeight),
                    placeholderText = state.mode.passwordPlaceholder,
                    leadingIcon = lockIcon,
                    contentDescription = state.mode.passwordPlaceholder,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = if (state.passwordConfirmationRequired) ImeAction.Next else ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(onDone = { if (state.submitEnabled) submit() })
                )
                if (state.passwordConfirmationRequired) {
                    Spacer(Modifier.height(14.dp))
                    UiTextField(
                        isLightTheme = true,
                        value = state.repeatedPassword,
                        onValueChange = { onIntent(AuthIntent.RepeatedPasswordChange(it)) },
                        modifier = Modifier.fillMaxWidth().height(authControlHeight),
                        placeholderText = "Повторите пароль",
                        leadingIcon = lockIcon,
                        contentDescription = "Повтор пароля",
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(onDone = { if (state.submitEnabled) submit() })
                    )
                    if (state.repeatedPassword.isNotEmpty() && !state.passwordsMatch) {
                        Spacer(Modifier.height(8.dp))
                        Text(
                            text = "Пароли не совпадают",
                            color = Red,
                            modifier = Modifier.fillMaxWidth(),
                            fontSize = 14.sp
                        )
                    }
                }
                state.error?.let { message ->
                    Spacer(Modifier.height(12.dp))
                    Text(
                        text = message,
                        color = Red,
                        textAlign = TextAlign.Center,
                        fontSize = 14.sp
                    )
                }
                Spacer(Modifier.height(24.dp))
                UiButton(
                    text = state.mode.actionTitle,
                    enabled = state.submitEnabled,
                    onClick = submit,
                    modifier = Modifier.fillMaxWidth().height(authControlHeight)
                )
            }
        }
    }
}

/**
 * Переключатель режима аутентификации
 */
@Composable
private fun AuthModeSelector(
    selectedMode: AuthMode,
    onModeChange: (AuthMode) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = GrayForLight.copy(alpha = 0.1f)
    ) {
        BoxWithConstraints {
            val compact = maxWidth < 360.dp

            Row(
                modifier = Modifier.padding(4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                AuthMode.entries.forEach { mode ->
                    val selected = mode == selectedMode

                    TextButton(
                        modifier = Modifier
                            .weight(1f)
                            .height(if (compact) 52.dp else 48.dp),
                        onClick = { onModeChange(mode) },
                        shape = textButtonShape(),
                        contentPadding = PaddingValues(
                            horizontal = if (compact) 4.dp else 8.dp
                        ),
                        colors = ButtonDefaults.textButtonColors(
                            containerColor = if (selected) {
                                Blue.copy(alpha = 0.14f)
                            } else {
                                White.copy(alpha = 0f)
                            },
                            contentColor = if (selected) Blue else Black
                        )
                    ) {
                        Text(
                            text = mode.tabTitle,
                            color = if (selected) Blue else Black,
                            fontWeight = if (selected) {
                                FontWeight.Bold
                            } else {
                                FontWeight.Normal
                            },
                            fontSize = if (compact) 12.sp else 14.sp,
                            maxLines = if (compact) 2 else 1,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

private val AuthMode.tabTitle: String
    get() = when (this) {
        AuthMode.Login -> "Вход"
        AuthMode.Register -> "Регистрация"
        AuthMode.ChangePassword -> "Смена пароля"
    }

private val AuthMode.passwordPlaceholder: String
    get() = if (this == AuthMode.ChangePassword) "Новый пароль" else "Пароль"

private val AuthMode.actionTitle: String
    get() = when (this) {
        AuthMode.Login -> "Войти"
        AuthMode.Register -> "Зарегистрироваться"
        AuthMode.ChangePassword -> "Изменить пароль"
    }

private val authControlHeight = 56.dp
