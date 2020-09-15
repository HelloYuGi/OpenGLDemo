package com.sven.opengldemo.airhockey

import android.content.Context
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import com.sven.opengldemo.R
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 *Create by Sven
 *Date:2020/8/30
 *Describe:
 */
class AirHockeyRender(val context: Context) : GLSurfaceView.Renderer {
    val POSITION_COMPONENT_COUNT = 2
    val tableVertices = floatArrayOf(
        0.0f, 0.0f,
        0.0f, 14f,
        9f, 14f,
        9f, 0f
    )

    val tableVerticesWithTriangles = floatArrayOf(
        //Triangle 1
        -0.5f, -0.5f,
        0.5f, 0.5f,
        -0.5f, 0.5f,

        //Triangle 2
        -0.5f, -0.5f,
        0.5f, -0.5f,
        0.5f, 0.5f,

        //Line
        -0.5f, 0f,
        0.5f, 0f,

        //mallets
        0f, -0.25f,
        0f, 0.25f,

        -0.6f, -0.6f,
        0.6f, 0.6f,
        -0.6f, 0.6f,

        //Triangle 2
        -0.6f, -0.6f,
        0.6f, -0.6f,
        0.6f, 0.6f
    )
    val BYTES_PER_FLOAT = 4
    var programId: Int = 0
    val U_COLOR = "u_Color"
    var uColorLocation = 0

    val A_POSITION = "a_Position"
    var aPositionLocation = 0
    lateinit var vertexData: FloatBuffer

    init {
        vertexData =
            ByteBuffer.allocateDirect(tableVerticesWithTriangles.size * BYTES_PER_FLOAT).order(ByteOrder.nativeOrder())
                .asFloatBuffer()
        vertexData.put(tableVerticesWithTriangles)
    }

    override fun onDrawFrame(gl: GL10?) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)
        GLES20.glUniform4f(uColorLocation,1.0f,0.0f,0.0f,1.0f)
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES,10,6)
        //绘制桌子 指定颜色 RGBA
        GLES20.glUniform4f(uColorLocation, 1.0f, 1.0f, 1.0f, 1.0f)
        //画桌子从0开始 读取6数据
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 6)
        //画线 从6开始 读取2组数据
        GLES20.glUniform4f(uColorLocation, 1.0f, 0.0f, 0.0f, 1.0f)
        GLES20.glDrawArrays(GLES20.GL_LINES, 6, 2)
        //绘制木追
        GLES20.glUniform4f(uColorLocation, 0.0f, 0.0f, 1.0f, 1.0f)
        GLES20.glDrawArrays(GLES20.GL_POINTS, 8, 1)
        GLES20.glUniform4f(uColorLocation, 1.0f, 0.0f, 0.0f, 1.0f)
        GLES20.glDrawArrays(GLES20.GL_POINTS, 9, 1)

    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f)
        var vertexShaderSource = TextResReader.readTextFileFromRes(context, R.raw.simple_vertex_shader)
        var fragShaderSource = TextResReader.readTextFileFromRes(context, R.raw.simple_fragment_shader)
        val vertexShader = ShaderHelper.compileVertexShader(vertexShaderSource)
        val fragShader = ShaderHelper.compileFragShader(fragShaderSource)
        programId = ShaderHelper.linkProgram(vertexShader, fragShader)
        //获取uniform的位置
        GLES20.glUseProgram(programId)
        uColorLocation = GLES20.glGetUniformLocation(programId, U_COLOR)
        //获取属性的位置
        aPositionLocation = GLES20.glGetAttribLocation(programId, A_POSITION)
        //关联属性与顶点数据的数组
        vertexData.position(0)
        GLES20.glVertexAttribPointer(aPositionLocation, POSITION_COMPONENT_COUNT, GLES20.GL_FLOAT, false, 0, vertexData)
        GLES20.glEnableVertexAttribArray(aPositionLocation)
    }
}