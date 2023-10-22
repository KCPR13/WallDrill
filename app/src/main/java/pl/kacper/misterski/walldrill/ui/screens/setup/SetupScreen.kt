package pl.kacper.misterski.walldrill.ui.screens.setup

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import pl.kacper.misterski.walldrill.R
import pl.kacper.misterski.walldrill.ui.common.AppToolbar
import pl.kacper.misterski.walldrill.ui.theme.PaddingExtraLarge
import pl.kacper.misterski.walldrill.ui.theme.PaddingLarge


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetupScreen(modifier: Modifier) {

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())


    Scaffold(modifier,
        topBar = {
            AppToolbar(
                R.string.welcome_to_the_app,
                Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                scrollBehavior
            )
        }) { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(PaddingLarge)
        ) {
            Image(
                modifier = Modifier.padding(top = PaddingExtraLarge),
                painter = painterResource(id = R.drawable.ic_camera),
                contentDescription = null
            )
            Text(text = stringResource(R.string.camera_permission_message))
        }
    }


}

@Preview
@Composable
fun SetupScreenPreview() {
    MaterialTheme {
        SetupScreen(modifier = Modifier)
    }
}