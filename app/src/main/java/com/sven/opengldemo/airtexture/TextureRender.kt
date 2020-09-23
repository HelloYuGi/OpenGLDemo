package com.sven.opengldemo.airtexture

import android.content.Context
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import com.sven.opengldemo.R
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 *Create by Sven
 *Date:2020/9/21
 *Describe:
 */
class TextureRender(val context: Context) : GLSurfaceView.Renderer {
    val projectionMatrix = FloatArray(16)
    val modelMatrix = FloatArray(16)
    lateinit var table: Table
    lateinit var mallet: Mallet
    lateinit var textureProgram: TextureShaderProgram
    lateinit var colorProgram: ColorShaderProgram
    //    val colorProgram = ColorShaderProgram(context)
    var texture = 0

    override fun onDrawFrame(gl: GL10?) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)
        textureProgram.userProgram()
        textureProgram.setUniforms(projectionMatrix, texture)
        table.bindData(textureProgram)
        table.draw()

        colorProgram.userProgram()
        colorProgram.setUniforms(projectionMatrix)
        mallet.bindData(colorProgram)
        mallet.draw()
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)
        Matrix.setIdentityM(modelMatrix, 0)
        Matrix.translateM(modelMatrix, 0, 0f, 0f, -3f)
        Matrix.rotateM(modelMatrix, 0, -60f, 1f, 0f, 0f)
        Matrix.perspectiveM(projectionMatrix, 0, 45f, width / height.toFloat(), 1f, 10f)
        val temp = FloatArray(16)
        Matrix.multiplyMM(temp, 0, projectionMatrix, 0, modelMatrix, 0)
        System.arraycopy(temp, 0, projectionMatrix, 0, temp.size)
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f)
        textureProgram = TextureShaderProgram(context,R.raw.texture_vertex_shader,R.raw.texture_fragment_shader)
        colorProgram = ColorShaderProgram(context,R.raw.simple_texture_vertex,R.raw.simple_fragment_shader)
        table = Table()
        mallet = Mallet()
        texture = TextureHelper.loadTexture(context, R.mipmap.air_hockey_surface)

    }
}