package pl.kacper.misterski.walldrill.ui.screens.aim

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.navigation.NavHostController
import pl.kacper.misterski.walldrill.R
import pl.kacper.misterski.walldrill.ui.AppNavigation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AimScreen(
    modifier: Modifier,
    navController: NavHostController,
    showBottomBar: (Boolean) -> Unit
) {
    showBottomBar.invoke(false)
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Scaffold(modifier = modifier, floatingActionButton =
        {
            FloatingActionButton(
                onClick = {
                    showBottomBar.invoke(true)
                    navController.navigate(AppNavigation.FOLDER)
                }, // TODO K ??
                containerColor = colorResource(id = R.color.mili),

                ) {
                Icon(
                    imageVector = Icons.Outlined.KeyboardArrowLeft,
                    contentDescription = null
                )
            }

        }) { paddingValues ->


        }
    }

}