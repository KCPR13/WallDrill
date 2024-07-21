/*
 * Copyright 2024 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
fun FolderScreen(modifier: Modifier) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier,
        topBar = {
            AppToolbar(
                R.string.saved_sessions,
                Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                scrollBehavior,
            )
        },
    ) { paddingValues ->
        Text(
            modifier =
            Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            text = "TO IMPLEMENT",
            textAlign = TextAlign.Center,
        )
    }
}

@Preview
@Composable
fun FolderScreenPreview() {
    MaterialTheme {
        FolderScreen(Modifier)
    }
}
