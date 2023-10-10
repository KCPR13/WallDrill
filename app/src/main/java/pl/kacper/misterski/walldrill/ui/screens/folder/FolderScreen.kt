package pl.kacper.misterski.walldrill.ui.screens.folder

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import pl.kacper.misterski.walldrill.R
import pl.kacper.misterski.walldrill.ui.common.AppToolbar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FolderScreen(
    modifier: Modifier) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(modifier,
        topBar = {
            AppToolbar(
                R.string.saved_sessions,
                Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                scrollBehavior
            )
        }) { paddingValues ->
            Text(modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(), text = "TO IMPLEMENT",
                textAlign = TextAlign.Center)
    }
}



@Preview
@Composable
fun FolderScreenPreview() {
    MaterialTheme{
        FolderScreen(Modifier)
    }
}