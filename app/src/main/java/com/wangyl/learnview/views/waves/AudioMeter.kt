package com.wangyl.learnview.views.waves

import android.content.Context
import android.media.MediaRecorder
import android.os.Environment
import android.os.Handler
import android.os.Looper
import java.io.File
import java.io.IOException
import kotlin.random.Random


class AudioMeter(val context:Context) {

    private val mediaRecorder: MediaRecorder = MediaRecorder()
    private val handler = Handler(Looper.getMainLooper())

    var callback: GetAmplitudeCallback? = null

    fun start(callback: GetAmplitudeCallback) {
        this.callback = callback

//        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT)
////        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA)
//        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
//        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
//        val file: File = File.createTempFile("录音", ".amr", context.cacheDir)
//        mediaRecorder.setOutputFile(file.absolutePath) //设置文件输出路径
//
////        mediaRecorder.setOutputFile("/dev/null")
////        mediaRecorder.setAudioSamplingRate(11025)
////        mediaRecorder.setVideoSize(640,480)
////        mediaRecorder.setVideoFrameRate(30)
//        try {
//            mediaRecorder.prepare()
//            mediaRecorder.start()
//        } catch (e: IllegalStateException) {
//            e.printStackTrace()
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
        startLoop()
    }

    private fun startLoop() {
        handler.post(object : Runnable {
            override fun run() {
                callback?.onAmplitudeUpdate(Random.nextInt(3000))
                handler.postDelayed(this, 50)
            }
        })
    }

    fun getAmplitude(): Int {
        return mediaRecorder.maxAmplitude;
    }
}

interface GetAmplitudeCallback {
    fun onAmplitudeUpdate(amplitude: Int)
}