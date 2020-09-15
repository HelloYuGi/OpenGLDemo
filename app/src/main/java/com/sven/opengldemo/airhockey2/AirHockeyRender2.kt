package com.sven.opengldemo.airhockey1

import android.content.Context
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import com.sven.opengldemo.R
import com.sven.opengldemo.airhockey.TextResReader
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 *Create by Sven
 *Date:2020/9/6
 *Describe:
 */
class AirHockeyRender2(val context: Context) : GLSurfaceView.Renderer {
    private val A_POSITION = "a_Position"
    private val U_COLOR = "a_Color"
    private val U_MATRIX = "u_Matrix"
    var positionLocation: Int = 0
    var colorLocation: Int = 0
    var umatrixLocation: Int = 0
    private val vertexArray = floatArrayOf(
        0.0f, 0.0f, 1f, 1f, 1f,
        -0.5f, -0.8f, 0.7f, 0.7f, 0.7f,
        0.5f, -0.8f, 0.7f, 0.7f, 0.7f,
        0.5f, 0.8f, 0.7f, 0.7f, 0.7f,
        -0.5f, 0.8f, 0.7f, 0.7f, 0.7f,
        -0.5f, -0.8f, 0.7f, 0.7f, 0.7f,

        //Line
        -0.5f, 0f, 1f, 0f, 0f,
        0.5f, 0f, 1f, 0f, 0f,

        //mallets
        0f, -0.4f, 1f, 0f, 0f,
        0f, 0.4f, 0f, 0f, 0f
    )
    private var vertexBuffer: FloatBuffer
    private val projectionMatrix = FloatArray(16)
    private val modelMatrix = FloatArray(16);
    init {
        vertexBuffer = ByteBuffer.allocateDirect(vertexArray.size * 4).order(ByteOrder.nativeOrder()).asFloatBuffer()
            .put(vertexArray)
        vertexBuffer.position(0)
    }

    override fun onDrawFrame(gl: GL10?) {
        GLES20.glUniform4f(colorLocation, 1.0f, 1.0f, 1.0f, 1.0f)
        GLES20.glUniformMatrix4fv(umatrixLocation, 1, false, projectionMatrix, 0)
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 6)
        //画线 从6开始 读取2组数据
        GLES20.glUniform4f(colorLocation, 1.0f, 0.0f, 0.0f, 1.0f)
        GLES20.glDrawArrays(GLES20.GL_LINES, 6, 2)
        //绘制木追
        GLES20.glUniform4f(colorLocation, 0.0f, 0.0f, 1.0f, 1.0f)
        GLES20.glDrawArrays(GLES20.GL_POINTS, 8, 1)
        GLES20.glUniform4f(colorLocation, 1.0f, 0.0f, 0.0f, 1.0f)
        GLES20.glDrawArrays(GLES20.GL_POINTS, 9, 1)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)
        Matrix.setIdentityM(modelMatrix,0)
        Matrix.translateM(modelMatrix,0,0f,0f,-3f)
        Matrix.rotateM(modelMatrix,0,-60f,1f,0f,0f)
        Matrix.perspectiveM(projectionMatrix,0,45f,width/height.toFloat(),1f,10f)
        val temp = FloatArray(16)
        Matrix.multiplyMM(temp,0,projectionMatrix,0,modelMatrix,0)
        System.arraycopy(temp,0,projectionMatrix,0,temp.size)
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f)
        val vertexSource = TextResReader.readTextFileFromRes(context, R.raw.simple_vertex_shader1)
        val fragSouce = TextResReader.readTextFileFromRes(context, R.raw.simple_fragment_shader1)
        val vertexId = compileShader(GLES20.GL_VERTEX_SHADER, vertexSource)
        val fragId = compileShader(GLES20.GL_FRAGMENT_SHADER, fragSouce)
        val programId = linkProgram(vertexId, fragId)
        GLES20.glUseProgram(programId)
        positionLocation = GLES20.glGetAttribLocation(programId, A_POSITION)
        GLES20.glVertexAttribPointer(positionLocation, 2, GLES20.GL_FLOAT, false, 5 * 4, vertexBuffer)

        vertexBuffer.position(2)
        colorLocation = GLES20.glGetAttribLocation(programId, U_COLOR)
        GLES20.glVertexAttribPointer(colorLocation, 3, GLES20.GL_FLOAT, false, 5 * 4, vertexBuffer)

        umatrixLocation = GLES20.glGetUniformLocation(programId, U_MATRIX)


        GLES20.glEnableVertexAttribArray(positionLocation)
        GLES20.glEnableVertexAttribArray(colorLocation)

        GLES20.glDeleteShader(vertexId)
        GLES20.glDeleteShader(fragId)
        GLES20.glDeleteProgram(programId)
    }

    fun compileShader(shaderType: Int, shaderSource: String): Int {
        val shaderId = GLES20.glCreateShader(shaderType)
        GLES20.glShaderSource(shaderId, shaderSource)
        GLES20.glCompileShader(shaderId)
        return shaderId
    }

    fun linkProgram(vertexShaderId: Int, fragShaderId: Int): Int {
        val program = GLES20.glCreateProgram()
        GLES20.glAttachShader(program, vertexShaderId)
        GLES20.glAttachShader(program, fragShaderId)
        GLES20.glLinkProgram(program)
        return program
    }
}