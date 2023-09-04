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

package com.google.mlkit.vision.demo.kotlin.posedetector

import android.content.Context
import android.speech.tts.TextToSpeech
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.android.odml.image.MlImage
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.demo.GraphicOverlay
import com.google.mlkit.vision.demo.data.AppContainer
import com.google.mlkit.vision.demo.data.RepItem
import com.google.mlkit.vision.demo.java.posedetector.classification.PoseClassifierProcessor
import com.google.mlkit.vision.demo.kotlin.VisionProcessorBase
import com.google.mlkit.vision.pose.Pose
import com.google.mlkit.vision.pose.PoseDetection
import com.google.mlkit.vision.pose.PoseDetector
import com.google.mlkit.vision.pose.PoseDetectorOptionsBase
import kotlinx.coroutines.runBlocking
import java.util.Locale
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.Random
/** A processor to run pose detector. */
class PoseDetectorProcessor(
  private val context: Context,
  options: PoseDetectorOptionsBase,
  private val showInFrameLikelihood: Boolean,
  private val visualizeZ: Boolean,
  private val rescaleZForVisualization: Boolean,
  private val runClassification: Boolean,
  private val isStreamMode: Boolean,
  private val container: AppContainer
) : VisionProcessorBase<PoseDetectorProcessor.PoseWithClassification>(context) {

  private val detector: PoseDetector
  private val classificationExecutor: Executor

  private var poseClassifierProcessor: PoseClassifierProcessor? = null

  private var speaker: TextToSpeech? = null

  private var sessionID: Int

  /** Internal class to hold Pose and classification results. */
  class PoseWithClassification(val pose: Pose, val classificationResult: List<String>)

  init {
    detector = PoseDetection.getClient(options)
    sessionID = generateRandomNumberBasedOnDay()

    classificationExecutor = Executors.newSingleThreadExecutor()
    speaker = TextToSpeech(context) { status ->
      if (status != TextToSpeech.ERROR) {
        speaker?.setLanguage(Locale.UK)
      }
    }
  }

  fun generateRandomNumberBasedOnDay(): Int {
//    val calendar = Calendar.getInstance()
//    val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

    // Use the day of the month as the seed for random number generation
//    val random = Random(dayOfMonth.toLong())
//    val random =
    // Generate a random number between 1 and 100
    return Random().nextInt(100000) + 1
  }


  override fun stop() {
    if(speaker !=null){
      speaker?.stop()
      speaker?.shutdown()
    }
    super.stop()
    detector.close()
  }

  override fun detectInImage(image: InputImage): Task<PoseWithClassification> {
    return detector
      .process(image)
      .continueWith(
        classificationExecutor,
        { task ->
          val pose = task.getResult()
          var classificationResult: List<String> = ArrayList()
          if (runClassification) {
            if (poseClassifierProcessor == null) {
              poseClassifierProcessor = PoseClassifierProcessor(context, isStreamMode)
            }
            classificationResult = poseClassifierProcessor!!.getPoseResult(pose, speaker)
          }
          PoseWithClassification(pose, classificationResult)
        }
      )
  }

  override fun detectInImage(image: MlImage): Task<PoseWithClassification> {
    return detector
      .process(image)
      .continueWith(
        classificationExecutor,
        { task ->
          val pose = task.getResult()
          var classificationResult: List<String> = ArrayList()
          if (runClassification) {
            if (poseClassifierProcessor == null) {
              poseClassifierProcessor = PoseClassifierProcessor(context, isStreamMode)
            }
            classificationResult = poseClassifierProcessor!!.getPoseResult(pose, speaker)
          }
          PoseWithClassification(pose, classificationResult)
        }
      )
  }

  override fun onSuccess(
    poseWithClassification: PoseWithClassification,
    graphicOverlay: GraphicOverlay
  ) {
    graphicOverlay.add(
      PoseGraphic(
        graphicOverlay,
        poseWithClassification.pose,
        showInFrameLikelihood,
        visualizeZ,
        rescaleZForVisualization,
        poseWithClassification.classificationResult
      )
    )
    try {
      val exerciseInfo = parseExercise(poseWithClassification.classificationResult[0])
      val (name, reps) = exerciseInfo
        val item = RepItem(
          sessionID,
          name,
          reps,
          lastModifiedTime = System.currentTimeMillis()
        )
        runBlocking {
          container.itemsRepository.upsertItem(item)
        }
      } catch (e: Exception) {
        Log.e(TAG, "parsing pose classification result failed!", e)
      }
  }

  fun parseExercise(string: String): Pair<String, Int> {
    val regex = """(\w+)\s:\s(\d+)\sreps""".toRegex()
    val match = regex.find(string) ?: throw IllegalArgumentException("String is not in the format '%s : %d reps'")

    val exerciseName = match.groupValues[1]
    val reps = match.groupValues[2].toInt()

    return Pair(exerciseName, reps)
  }


  override fun onFailure(e: Exception) {
    Log.e(TAG, "Pose detection failed!", e)
  }

  override fun isMlImageEnabled(context: Context?): Boolean {
    // Use MlImage in Pose Detection by default, change it to OFF to switch to InputImage.
    return true
  }

  companion object {
    private val TAG = "PoseDetectorProcessor"
  }
}
