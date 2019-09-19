package com.example.lc.materialuitest.view;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class Triangle {

    private FloatBuffer vertexBuffer;

    private final int program;

    static final int COORDS_PRE_VERTEX = 3;

    static float triangleCoords[] = {
            0.0f, 0.5f, 0.0f,       //顶点
            -0.5f, -0.25f, 0.0f,     //左下角顶点
            0.5f, -0.25f, 0.0f       //右下角顶点
    };

    private int positionHandle;
    private int colorHandle;

    private final int vertexCount = triangleCoords.length / COORDS_PRE_VERTEX;
    private final int vertexStride = COORDS_PRE_VERTEX * 4;

    float color[] = {255, 0, 0, 1.0f};

    public Triangle(){
        ByteBuffer buffer = ByteBuffer.allocateDirect(triangleCoords.length * 4);
        buffer.order(ByteOrder.nativeOrder());
        vertexBuffer = buffer.asFloatBuffer();
        vertexBuffer.put(triangleCoords);
        vertexBuffer.position(0);

        int vertexShader = OneOpenGlRenderer.loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = OneOpenGlRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);

        program = GLES20.glCreateProgram();
        GLES20.glAttachShader(program, vertexShader);
        GLES20.glAttachShader(program, fragmentShader);

        GLES20.glLinkProgram(program);
    }

    public void draw(){
        //添加到OpenGLES环境
        GLES20.glUseProgram(program);

        //获取句柄
        positionHandle = GLES20.glGetAttribLocation(program, "vPosition");
        //启用三角形顶点位置的句柄
        GLES20.glEnableVertexAttribArray(positionHandle);
        //准备三角形坐标数据
        GLES20.glVertexAttribPointer(positionHandle, COORDS_PRE_VERTEX, GLES20.GL_FLOAT, false, vertexStride, vertexBuffer);

        colorHandle = GLES20.glGetUniformLocation(program, "vColor");
        //设置绘制三角形的颜色
        GLES20.glUniform4fv(colorHandle, 1, color, 0);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);

        GLES20.glDisableVertexAttribArray(positionHandle);
    }

    //顶点着色器
    private final String vertexShaderCode = "attribute vec4 vPosition;" +
            "void main(){" +
            "   gl_Position = vPosition;" +
            "}";

    //片段着色器
    private final String fragmentShaderCode = "precision mediump float;" +
            "uniform vec4 vColor;" +
            "void main(){" +
            "   gl_FragColor = vColor;"
            +"}";

}
