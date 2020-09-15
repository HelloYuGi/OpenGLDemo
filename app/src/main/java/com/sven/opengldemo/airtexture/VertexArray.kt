package com.sven.opengldemo.airtexture

import android.opengl.GLES20
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

/**
 *Create by Sven
 *Date:2020/9/16
 *Describe:
 */
class VertexArray(val vertexData: FloatArray) {
    private val floatBuffer: FloatBuffer

    init {
        floatBuffer = ByteBuffer.allocateDirect(vertexData.size * 4).order(ByteOrder.nativeOrder()).asFloatBuffer()
            .put(vertexData)
    }

    fun setVertexAttriPointer(dataOffset:Int,attributeLocation:Int,componentCount:Int,stride:Int){
        floatBuffer.position(dataOffset)
        GLES20.glVertexAttribPointer(attributeLocation,componentCount,GLES20.GL_FLOAT,false,stride,floatBuffer)
        GLES20.glEnableVertexAttribArray(attributeLocation)
        floatBuffer.position(0)
    }


}