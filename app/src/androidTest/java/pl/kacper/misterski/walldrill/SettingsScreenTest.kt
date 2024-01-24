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
    fun Setup(){
        //WHEN
        composeTestRule.setContent {
            WallDrillTheme {
                SettingsScreen(navController = rememberNavController())
            }
        }
    }

    @Test
    fun toolbarVisibilityTest() {
        //THEN
        composeTestRule.onNodeWithText(context.getString(R.string.settings)).assertIsDisplayed()
    }

    @Test
    fun calibrationVisibilityTest() {
        //THEN
        composeTestRule.onNodeWithText(context.getString(R.string.calibration)).assertIsDisplayed()
    }

    @Test
    fun colorsVisibilityTest() {
        //THEN
        composeTestRule.onNodeWithText(context.getString(R.string.colors)).assertIsDisplayed()
    }

}