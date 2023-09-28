package ru.mobileup.template.features.root.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import ru.mobileup.kmm_form_validation.control.InputControl
import ru.mobileup.template.core.message.ui.MessageUi
import ru.mobileup.template.core.theme.AppTheme
import ru.mobileup.template.core.widget.BottomBar
import ru.mobileup.template.core.widget.OutlinedTextField
import ru.mobileup.template.core.widget.Page
import ru.mobileup.template.core.widget.PrimaryButton
import ru.mobileup.template.core.widget.TextButton
import ru.mobileup.template.core.widget.Toolbar
import ru.mobileup.template.features.pokemons.ui.PokemonsUi

@Composable
fun RootUi(
    component: RootComponent,
    modifier: Modifier = Modifier
) {
    val childStack by component.childStack.collectAsState()

    SystemBarColors()

    Children(childStack, modifier) { child ->
        when (val instance = child.instance) {
            is RootComponent.Child.Pokemons -> {
                Material3WidgetsUi()
            }
        }
    }

    MessageUi(
        component = component.messageComponent,
        modifier = modifier,
        bottomPadding = 16.dp
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun Material3WidgetsUi() {
    AppTheme {
        val coroutineScope = rememberCoroutineScope()
        val inputControl = remember { InputControl(coroutineScope) }
        val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
        Scaffold(
            topBar = {
                Toolbar(
                    title = "Toolbar",
                    scrollBehavior = scrollBehavior
                )
            },
            bottomBar = {
                BottomBar(currentPage = Page.Main, elevated = true, onPageSelected = {})
            },
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                PrimaryButton(text = "PrimaryButton", onClick = {})
                TextButton(text = "TextButton")
                OutlinedTextField(inputControl = inputControl)
                repeat(15) {
                    TextButton(text = "TextButton #$it")
                }
            }
        }
    }
}

@Composable
private fun SystemBarColors() {
    val systemUiController = rememberSystemUiController()

    val statusBarColor = MaterialTheme.colorScheme.surface
    LaunchedEffect(statusBarColor) {
        systemUiController.setStatusBarColor(statusBarColor)
    }

    val navigationBarColor = MaterialTheme.colorScheme.surface
    LaunchedEffect(navigationBarColor) {
        systemUiController.setNavigationBarColor(navigationBarColor)
    }
}

@Composable
fun RootUiPreview() {
    AppTheme {
        RootUi(FakeRootComponent())
    }
}