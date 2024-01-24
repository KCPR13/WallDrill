package pl.kacper.misterski.walldrill.ui.screens.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import pl.kacper.misterski.walldrill.R
import pl.kacper.misterski.walldrill.ui.AppNavigation
import pl.kacper.misterski.walldrill.ui.common.AppToolbar
import pl.kacper.misterski.walldrill.ui.theme.CardElevation
import pl.kacper.misterski.walldrill.ui.theme.FontMedium
import pl.kacper.misterski.walldrill.ui.theme.PaddingLarge

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(modifier: Modifier = Modifier, viewModel: SettingsViewModel = viewModel(),
                   navController: NavHostController) {

    val state = viewModel.uiState.collectAsState()
    viewModel.fetchModels(LocalContext.current)

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    Scaffold(modifier,
        topBar = {
            AppToolbar(
                R.string.settings,
                Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                scrollBehavior
            )
        }) { paddingValues ->
        LazyColumn(Modifier.padding(top = paddingValues.calculateTopPadding())) {
            items(state.value.models) { model ->
                SettingsItem(
                    modifier = Modifier
                        .clickable {
                            when (model.action) {
                                SettingsAction.COLORS -> navController.navigate(
                                    AppNavigation.COLORS
                                )

                                SettingsAction.CALIBRATION -> navController.navigate(AppNavigation.CALIBRATION)
                            }
                        }
                        .padding(PaddingLarge)
                        .fillMaxWidth(), model = model
                )
            }
        }
    }
}

@Composable
private fun SettingsItem(modifier: Modifier, model: SettingsModel) {
    Card(
        modifier,
        shape = RectangleShape,
        elevation = CardDefaults.cardElevation(
            defaultElevation = CardElevation
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier
                    .padding(PaddingLarge)
                    .align(Alignment.CenterVertically),
                fontSize = FontMedium,
                text = model.title,
            )
            Icon(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(end = PaddingLarge),
                imageVector = Icons.Filled.KeyboardArrowRight,
                contentDescription = null
            )
        }
    }

}





@Preview
@Composable
fun SettingsScreenPreview() {
    MaterialTheme {
        SettingsScreen(navController = rememberNavController())
    }
}