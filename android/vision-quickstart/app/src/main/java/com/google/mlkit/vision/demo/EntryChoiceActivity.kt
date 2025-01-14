/*
 * Copyright 2020 Google LLC. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.mlkit.vision.demo

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.mlkit.vision.demo.data.AppContainer
import com.google.mlkit.vision.demo.data.AppDataContainer
import com.google.mlkit.vision.demo.ui.theme.RepsTrackerTheme

class EntryChoiceActivity : AppCompatActivity(), ActivityCompat.OnRequestPermissionsResultCallback {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {
      RepsTrackerTheme {
        Surface (/*color = MaterialTheme.colorScheme.primary*/) {
          HomeScreen(onClick = {
            val intent =
              Intent(
                this@EntryChoiceActivity,
                com.google.mlkit.vision.demo.kotlin.CameraXLivePreviewActivity::class.java
              )
            startActivity(intent)
          },
            onClick1 = {
              val intent =
                Intent(
                  this@EntryChoiceActivity,
                  ActivityHistory::class.java
                )
              startActivity(intent)

            })
        }
      }
    }

    if (!allRuntimePermissionsGranted()) {
      getRuntimePermissions()
    }
  }

  private fun allRuntimePermissionsGranted(): Boolean {
    for (permission in REQUIRED_RUNTIME_PERMISSIONS) {
      permission.let {
          if (!isPermissionGranted(this, it)) {
            return false
          }
      }
    }
    return true
  }

  private fun getRuntimePermissions() {
    val permissionsToRequest = ArrayList<String>()
    for (permission in REQUIRED_RUNTIME_PERMISSIONS) {
      permission.let {
          if (!isPermissionGranted(this, it)) {
            permissionsToRequest.add(permission)
          }
      }
    }

    if (permissionsToRequest.isNotEmpty()) {
      ActivityCompat.requestPermissions(
        this,
        permissionsToRequest.toTypedArray(),
        PERMISSION_REQUESTS
      )
    }
  }

  private fun isPermissionGranted(context: Context, permission: String): Boolean {
    if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
    ) {
      Log.i(TAG, "Permission granted: $permission")
      return true
    }
    Log.i(TAG, "Permission NOT granted: $permission")
    return false
  }

  companion object {
    private const val TAG = "EntryChoiceActivity"
    private const val PERMISSION_REQUESTS = 1

    private val REQUIRED_RUNTIME_PERMISSIONS =
      arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
      )
  }
}

@Composable
fun HomeScreen(
  onClick: () -> Unit = {},
  onClick1: () -> Unit = {}
) {
  Column(Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
    ) {
    Button(onClick = onClick,
      modifier = Modifier.padding(vertical = 24.dp)
      ) {
      Text(
        text = stringResource(R.string.kotlin_entry_title),
      )
    }
    Button(onClick = onClick1,
      modifier = Modifier.padding(vertical = 24.dp)
    ) {
      Text(
        text = stringResource(R.string.activity_history),
      )
    }
  }
}

//@Composable
//fun ActivityHistoryScreen() {
//  Column(Modifier.fillMaxSize(),
//    verticalArrangement = Arrangement.Center,
//    horizontalAlignment = Alignment.CenterHorizontally
//  ) {
//    Button(onClick = {},
//      modifier = Modifier.padding(vertical = 24.dp)
//    ) {
//      Text(
//        text = stringResource(R.string.activity_history_title),
//      )
//    }
//  }
//}