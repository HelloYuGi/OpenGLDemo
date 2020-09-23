package com.sven.opengldemo.airtexture

import android.content.Context
import android.graphics.BitmapFactory
import android.opengl.GLES20
import android.opengl.GLUtils
import android.util.Log
import com.sven.opengldemo.airhockey.ShaderHelper

/**
 *Create by Sven
 *Date:2020/9/12
 *Describe:
 */
object TextureHelper {
    fun loadTexture(context: Context, resId: Int): Int {
        val textureObjs = IntArray(1)
        GLES20.glGenTextures(1, textureObjs, 0)
        if (textureObjs[0] == 0) {
            Log.e("wsw", "glGen texture failed")
            return 0
        }
        val options = BitmapFactory.Options()
        options.inScaled = false
        val bitmap = BitmapFactory.decodeResource(context.resources, resId, options)
        //将纹理做为二维纹理对待 绑定到指定的纹理对象id
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureObjs[0])
        //设置纹理的过滤参数
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR_MIPMAP_LINEAR)
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR)
        //将位图加载到opengl
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0)
        bitmap.recycle()
        //生成mip贴图
        GLES20.glGenerateMipmap(GLES20.GL_TEXTURE_2D)
        //解除绑定
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,0)
        return textureObjs[0]
    }

    fun buildProgram(vertexShaderSource:String,fragmentShaderSource:String):Int{
        val vertexShader =ShaderHelper.compileVertexShader(vertexShaderSource)
        val fragShader = ShaderHelper.compileFragShader(fragmentShaderSource)
//        val program = GLES20.glCreateProgram()
//        GLES20.glAttachShader(program,vertexShader)
//        GLES20.glAttachShader(program,fragShader)
//        GLES20.glLinkProgram(program)
        val program = ShaderHelper.linkProgram(vertexShader,fragShader)
        return program
    }

    fun compileShader(type:Int,shaderCode:String):Int{
        val shaderId = GLES20.glCreateShader(type)
        GLES20.glShaderSource(shaderId,shaderCode)
        GLES20.glCompileShader(shaderId)
        return shaderId
    }
}