package com.sven.opengldemo.airhockey

import android.content.Context
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 *Create by Sven
 *Date:2020/8/30
 *Describe:
 */
object TextResReader {
    fun readTextFileFromRes(context: Context, resId: Int): String {
        val builder = StringBuilder()
        val inputStream = context.resources.openRawResource(resId)
        val reader = InputStreamReader(inputStream)
        val bufferReader = BufferedReader(reader)
        var nextLine: String? = ""
        while ((bufferReader.readLine().also { nextLine = it }) != null) {
            builder.append(nextLine)
            builder.append('\n')
        }

        return builder.toString()
    }
}