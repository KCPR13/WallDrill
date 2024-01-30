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

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import pl.kacper.misterski.walldrill.ui.screens.settings.SettingsScreen
import pl.kacper.misterski.walldrill.ui.theme.WallDrillTheme

@RunWith(AndroidJUnit4::class)
class SettingsScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    @Before
    fun setup() {
        // WHEN
        composeTestRule.setContent {
            WallDrillTheme {
                SettingsScreen(navController = rememberNavController())
            }
        }
    }

    @Test
    fun toolbarVisibilityTest() {
        // THEN
        composeTestRule.onNodeWithText(context.getString(R.string.settings)).assertIsDisplayed()
    }

    @Test
    fun calibrationVisibilityTest() {
        // THEN
        composeTestRule.onNodeWithText(context.getString(R.string.calibration)).assertIsDisplayed()
    }

    @Test
    fun colorsVisibilityTest() {
        // THEN
        composeTestRule.onNodeWithText(context.getString(R.string.colors)).assertIsDisplayed()
    }
}
