package com.sven.opengldemo.airtexture

import android.opengl.GLES20

/**
 *Create by Sven
 *Date:2020/9/16
 *Describe:
 */
class Table {
    private val vertexArray:VertexArray
    private val POSITION_COMPONENT_COUNT = 2
    private val TEXTURE_COORDINATES_COUNT = 2
    private val STRIDE = (POSITION_COMPONENT_COUNT + TEXTURE_COORDINATES_COUNT) * 4
    private val VERTEX_DATA = floatArrayOf(
        0f, 0f, 0.5f, 0.5f,
        -0.5f, -0.8f, 0f, 0.9f,
        0.5f, -0.8f, 1f, 0.9f,
        0.5f, 0.8f, 1f, 0.1f,
        -0.5f, 0.8f, 0f, 0.1f,
        -0.5f, -0.8f, 0f, 0.9f
    )
    init {
        vertexArray = VertexArray(VERTEX_DATA)
    }

    fun bindData(textureShaderProgram: TextureShaderProgram){
        vertexArray.setVertexAttriPointer(0,textureShaderProgram.aPositionLocation,POSITION_COMPONENT_COUNT,STRIDE)
        vertexArray.setVertexAttriPointer(POSITION_COMPONENT_COUNT,textureShaderProgram.aTextureCoordinateLocation,TEXTURE_COORDINATES_COUNT,STRIDE)
    }

    fun draw(){
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN,0,6)
    }
}