package com.sven.opengldemo.airtexture

import android.content.Context
import android.opengl.GLES20

/**
 *Create by Sven
 *Date:2020/9/20
 *Describe:
 */
class ColorShaderProgram(context: Context,vertexResId:Int,fragResId:Int) : ShaderProgram(context,vertexResId,fragResId) {
    var uMatrixLocation = 0
    var aPositionLocation = 0
    var aColorLocation = 0

    init {
        uMatrixLocation = GLES20.glGetUniformLocation(program, U_MATRIX)
        aPositionLocation = GLES20.glGetAttribLocation(program, A_POSITION)
        aColorLocation = GLES20.glGetAttribLocation(program, A_COLOR)
    }

    fun setUniforms(matrix:FloatArray){
        GLES20.glUniformMatrix4fv(uMatrixLocation,1,false,matrix,0)
    }

}