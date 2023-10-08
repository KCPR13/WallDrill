package pl.kacper.misterski.walldrill.ui.screens.colors

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import pl.kacper.misterski.walldrill.R
import pl.kacper.misterski.walldrill.ui.AppNavigation
import pl.kacper.misterski.walldrill.ui.common.AppToolbar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColorsScreen(
    modifier: Modifier,
    navController: NavHostController,
    viewModel: ColorsViewModel = viewModel()
) {

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val state = viewModel.uiState.collectAsState()


    Scaffold(modifier,
        topBar = {
            AppToolbar(
                R.string.colors,
                Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                scrollBehavior,
                onBackPressedClick = { navController.navigate(AppNavigation.SETTINGS) }

            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(AppNavigation.COLOR_DETECTION) },
                containerColor = colorResource(id = R.color.mili)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Add,
                    contentDescription = null
                )
            }

        }) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (state.value.colors.isEmpty()) {
                EmptyColorsPlaceHolder(modifier = Modifier.align(Center))
            } else {
                //TODO K
            }
        }

    }
}

@Composable
private fun EmptyColorsPlaceHolder(modifier: Modifier) {
    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = painterResource(id = R.drawable.ic_paint), contentDescription = null)
        Text(
            modifier = Modifier.padding(vertical = 16.dp),
            text = stringResource(R.string.no_colors_set)
        )

    }

}

@Preview
@Composable
fun ColorsScreenPreview() {
    MaterialTheme {
        ColorsScreen(modifier = Modifier, navController = rememberNavController())
    }
}