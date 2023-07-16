package pl.kacper.misterski.walldrill

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.StateFlow
import pl.kacper.misterski.walldrill.domain.ColorAnalyzer
import pl.kacper.misterski.walldrill.ui.MainUiState
import pl.kacper.misterski.walldrill.ui.MainViewModel
import pl.kacper.misterski.walldrill.ui.fragments.calibration.CalibrationScreen
import pl.kacper.misterski.walldrill.ui.fragments.setup.SetupScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity(), ColorAnalyzer.ColorDetectionListener {

    private val viewModel: MainViewModel by viewModels()

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            viewModel.updatePermissionState(isGranted)
            if (isGranted) {
                Toast.makeText(this, "Front camera permission granted!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Front camera permission denied!", Toast.LENGTH_SHORT).show()
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val isGranted = checkCameraPermissionStatus() == PermissionStatus.GRANTED
        viewModel.updatePermissionState(isGranted)
        if (!isGranted){
            requestFrontCameraPermission()
        }
        setContent {
                MainScreen(viewModel.uiState,this)
            }
    }

    private fun checkCameraPermissionStatus(): PermissionStatus {
        val permission = Manifest.permission.CAMERA
        return when {
            ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED -> {
                PermissionStatus.GRANTED
            }
            shouldShowRequestPermissionRationale(permission) -> {
                PermissionStatus.DENIED
            }
            else -> {
                PermissionStatus.NOT_REQUESTED
            }
        }
    }

    private fun requestFrontCameraPermission() {
        val permission = Manifest.permission.CAMERA
        requestPermissionLauncher.launch(permission)
    }

    //TODO separate file
    enum class PermissionStatus {
        GRANTED,
        DENIED,
        NOT_REQUESTED
    }

    //TODO remove is detected
    override fun onRedColorDetected(isDetected: Boolean, detectedLocations: List<Pair<Int, Int>>) {
       viewModel.updatePoints(detectedLocations)
    }

}

@Composable
fun MainScreen(uiState: StateFlow<MainUiState>, colorDetectionListener: ColorAnalyzer.ColorDetectionListener) {
    val navController = rememberNavController()

    val mainUiState by uiState.collectAsState()

    val destination = if (mainUiState.permissionGranted) {
        "calibration"
    } else {
        "setup"
    }

    NavHost(navController = navController, startDestination = destination) {
        composable("calibration") { CalibrationScreen(colorDetectionListener, uiState) }
        composable("setup") { SetupScreen() }
    }

}