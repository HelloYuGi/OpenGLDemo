package com.sven.opengldemo.airhockey

import android.opengl.GLES20
import  android.opengl.GLES20.*
import android.util.Log
import java.lang.IllegalArgumentException

/**
 *Create by Sven
 *Date:2020/8/30
 *Describe:
 */
object ShaderHelper {
    fun compileVertexShader(shaderCode: String): Int {
        return compileShader(GLES20.GL_VERTEX_SHADER, shaderCode)
    }

    fun compileFragShader(shaderCode: String): Int {
        return compileShader(GLES20.GL_FRAGMENT_SHADER, shaderCode)
    }

    private fun compileShader(type: Int, shaderCode: String): Int {
        //返回opengl对象的引用
        val shaderObjectId = GLES20.glCreateShader(type)
        if (shaderObjectId == 0) {
            throw IllegalArgumentException("create shader failed")
        }
        //把glsl文件编写的顶点/片元跟着色器对象关联起来
        glShaderSource(shaderObjectId, shaderCode)
        //编译前先上传到shaderObjectId的源代码
        glCompileShader(shaderObjectId)
        //检查openGl能否成功编译这个着色器
        val compileStatus = IntArray(1)
        glGetShaderiv(shaderObjectId, GL_COMPILE_STATUS, compileStatus, 0)
        //获取OpenGL编译信息
        Log.d("wsw", "result compiling source " + glGetShaderInfoLog(shaderObjectId))
        if (compileStatus[0] == 0) {
            //编译失败
            glDeleteShader(shaderObjectId)
            return 0
        }
        return shaderObjectId
    }

    fun linkProgram(vertexShaderId: Int, fragmentShaderId: Int): Int {
        //创建程序对象
        val programObjectId = glCreateProgram()
        if (programObjectId == 0) {
            throw IllegalArgumentException("glCreate Program  failed")
        }
        //附上着色器
        glAttachShader(programObjectId, vertexShaderId)
        glAttachShader(programObjectId, fragmentShaderId)
        //把这些周瑟琪联合起来
        glLinkProgram(programObjectId)
        //检查成功还是失败
        val linkStatus = IntArray(1)
        glGetProgramiv(programObjectId, GL_LINK_STATUS, linkStatus, 0)
        Log.d("wsw", "result link program " + glGetProgramInfoLog(programObjectId))
        //验证状态
        if (linkStatus[0] == 0) {
            glDeleteProgram(programObjectId)
            return 0
        }
        return programObjectId
    }

    //验证一下这个程序对于当前的OpenGl状态是否有效
    fun validateProgram(programObjectId: Int): Boolean {
        glValidateProgram(programObjectId)
        val validateStatus = IntArray(1)
        glGetProgramiv(programObjectId, GL_VALIDATE_STATUS, validateStatus, 0)
        Log.d("wsw", "result validate program " + glGetProgramInfoLog(programObjectId))
        return validateStatus[0] != 0
    }
}