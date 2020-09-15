package com.sven.opengldemo.airtexture

import android.opengl.GLES20

/**
 * Create by Sven
 * Date:2020/9/16
 * Describe:
 */
class Mallet{
    private val POSITION_COMPONENT_COUNT = 2
    private val COLOR_COMPONENT_COUNT = 3
    private val STRIDE = (POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT)*4
    private val VERTEX_DATA = floatArrayOf(
        0f,-0.4f,0f,0f,1f,
        0f,0.4f,1f,0f,0f
    )
    private val vertexArray:VertexArray
    init {
        vertexArray = VertexArray(VERTEX_DATA)
    }

    fun bindData(){
        vertexArray.setVertexAttriPointer(0,1,POSITION_COMPONENT_COUNT,STRIDE)
        vertexArray.setVertexAttriPointer(POSITION_COMPONENT_COUNT,1,COLOR_COMPONENT_COUNT,STRIDE)
    }

    fun draw(){
        GLES20.glDrawArrays(GLES20.GL_POINTS,0,2)
    }
}
