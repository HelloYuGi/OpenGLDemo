package com.sven.opengldemo.airhockey

import android.opengl.GLSurfaceView
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import com.sven.opengldemo.R

/**
 *Create by Sven
 *Date:2020/8/30
 *Describe:
 */
class AirHockeyActivity:AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_airhockey)
        val glSurface = findViewById<GLSurfaceView>(R.id.gl_surface)
        //这个一定要 设置使用OpenGL2.0
        glSurface.setEGLContextClientVersion(2)
        glSurface.setRenderer(AirHockeyRender(this))
    }
}