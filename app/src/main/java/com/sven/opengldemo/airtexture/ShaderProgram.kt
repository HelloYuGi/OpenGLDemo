package com.sven.opengldemo.airtexture

import android.content.Context
import android.opengl.GLES20
import android.util.Log
import com.sven.opengldemo.R
import com.sven.opengldemo.airhockey.TextResReader

/**
 *Create by Sven
 *Date:2020/9/20
 *Describe:
 */
open class ShaderProgram(context: Context,vertexResId:Int,fragmResId:Int) {
    val U_MATRIX = "u_Matrix"
    val U_TEXTUREUNIT = "u_TextureUnit"
    val A_POSITION = "a_Position"
    val A_TEXTURECOORDINATES = "a_TextureCoordinates"
    val A_COLOR = "a_color"

    var program = 0

    init {
        Log.d("wsw","shader program init")
        val vertexSource = TextResReader.readTextFileFromRes(context, vertexResId)
        val fragSource = TextResReader.readTextFileFromRes(context, fragmResId)
        program = TextureHelper.buildProgram(vertexSource, fragSource)
    }

    fun userProgram(){
        GLES20.glUseProgram(program)
    }
}