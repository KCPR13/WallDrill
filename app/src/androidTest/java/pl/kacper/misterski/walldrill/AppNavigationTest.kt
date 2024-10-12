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
package pl.kacper.misterski.walldrill

import android.Manifest
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.GrantPermissionRule
import junit.framework.TestCase
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import pl.kacper.misterski.walldrill.ui.main.MainScreen
import pl.kacper.misterski.walldrill.ui.main.MainUiState

@RunWith(AndroidJUnit4::class)
class AppNavigationTest : TestCase() {
    @get:Rule
    val androidComposeTestRule = createComposeRule()

    @get:Rule
    val cameraPermission: GrantPermissionRule
        get() = GrantPermissionRule.grant(Manifest.permission.CAMERA)

    lateinit var navController: TestNavHostController

    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    @Before
    fun setup() {
        androidComposeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            MainScreen(
                navController = navController,
                uiState = MainUiState(),
                onAimClick = {},
            )
        }
    }

    @Test
    fun testSettingsNavigation() {
        with(androidComposeTestRule) {
            // GIVEN
            onNodeWithText(context.getString(R.string.settings)).assertDoesNotExist()

            // WHEN
            onNodeWithTag(context.getString(R.string.test_tag_settings_icon)).performClick()
            onNodeWithText(context.getString(R.string.settings)).assertIsDisplayed()
            onNodeWithTag(context.getString(R.string.test_tag_folder_icon)).performClick()

            // THEN
            onNodeWithText(context.getString(R.string.settings)).assertDoesNotExist()
        }
    }
}
