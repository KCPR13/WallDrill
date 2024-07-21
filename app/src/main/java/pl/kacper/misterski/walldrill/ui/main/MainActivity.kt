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
package pl.kacper.misterski.walldrill.ui.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import pl.kacper.misterski.walldrill.domain.enums.PermissionStatus

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
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
        setContent {
            MainScreen(viewModel, rememberNavController())
        }
        val isGranted = checkCameraPermissionStatus() == PermissionStatus.GRANTED
        viewModel.updatePermissionState(isGranted)
        if (!isGranted) {
            requestFrontCameraPermission()
        }
    }

    private fun checkCameraPermissionStatus(): PermissionStatus {
        val permission = Manifest.permission.CAMERA
        return when {
            ContextCompat.checkSelfPermission(
                this,
                permission,
            ) == PackageManager.PERMISSION_GRANTED -> {
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
}
