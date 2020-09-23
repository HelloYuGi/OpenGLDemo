package com.sven.opengldemo.airtexture

import android.content.Context
import android.opengl.GLES20
import android.util.Log

/**
 *Create by Sven
 *Date:2020/9/20
 *Describe:
 */
class TextureShaderProgram(context: Context,vertexResId:Int,fragResId:Int) : ShaderProgram(context,vertexResId,fragResId) {
    var uMatrixLocation = 0
    var uTextureUnitLocation = 0
    var aPositionLocation = 0
    var aTextureCoordinateLocation = 0

    init {
        Log.d("wsw","TextureShaderProgram  init")
        uMatrixLocation = GLES20.glGetUniformLocation(program, U_MATRIX)
        uTextureUnitLocation = GLES20.glGetUniformLocation(program, U_TEXTUREUNIT)
        aPositionLocation = GLES20.glGetAttribLocation(program, A_POSITION)
        aTextureCoordinateLocation = GLES20.glGetAttribLocation(program, A_TEXTURECOORDINATES)
    }

    fun setUniforms(matrix:FloatArray,textureId:Int){
        GLES20.glUniformMatrix4fv(uMatrixLocation,1,false,matrix,0)
        GLES20.glActiveTexture(GLES20.GL_TEXTURE)
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,textureId)
        GLES20.glUniform1i(uTextureUnitLocation,0)
    }
}