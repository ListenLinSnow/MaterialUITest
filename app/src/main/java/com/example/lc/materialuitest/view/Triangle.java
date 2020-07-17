package com.example.lc.materialuitest.view;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class Triangle {

    private FloatBuffer vertexBuffer;

    //设置每个顶点的坐标数
    static final int COORDS_PER_VERTEX = 3;
    //设置三角形顶点数组，默认按逆时针方向绘制
    /*static float triangleCoords[] = {
            0.0f, 1.0f, 0.0f,       //顶点
            -1.0f, -0.0f, 0.0f,      //左下角
            1.0f, -0.0f, 0.0f,       //右下角
    };*/
    static float triangleCoords[] = {
            0.0f, 0.622008459f, 0.0f,       //顶点
            -0.5f, -0.311004243f, 0.0f,     //左下顶点
            0.5f, -0.311004243f, 0.0f       //右下顶点
    };

    //设置三角形颜色和透明度(r, g, b, a)
    //float color[] = {0.0f, 0.5f, 0.0f, 0.5f};       //绿色不透明
    float color[] = {0.63671875f, 0.76953125f, 0.22265625f, 1.0f};

    private final String vertexShaderCode =
                    "attribute vec4 vPosition;"+
                    "void main() {"+
                    "   gl_Position = vPosition;"+
                    "}";

    private final String fragmentShaderCode =
                    "precision mediump float;"+
                    "uniform vec4 vColor;"+
                    "void main() {"+
                    "   gl_FragColor = vColor;"+
                    "}";

    private final int program;
    private int positionHandle;
    private int colorHandle;

    private final int vertexCount = triangleCoords.length / COORDS_PER_VERTEX;
    private final int vertexStride = COORDS_PER_VERTEX * 4;

    public Triangle(){
        //初始化顶点字节缓冲区，用于存放性状的坐标（每个浮点数占用4个字节）
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(triangleCoords.length * 4);
        //设置使用设备硬件的原生字节序
        byteBuffer.order(ByteOrder.nativeOrder());

        //从ByteBuffer中创建一个浮点缓冲区
        vertexBuffer = byteBuffer.asFloatBuffer();
        //把坐标都添加到FloatBuffer中
        vertexBuffer.put(triangleCoords);
        //设置buffer从第一个坐标开始读
        vertexBuffer.position(0);

        //编译shader代码
        int vertexShader = MyRenderer.loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = MyRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);

        //创建空的OpenGL ES program
        program = GLES20.glCreateProgram();

        //将vertex shader添加到program
        GLES20.glAttachShader(program, vertexShader);
        //将fragment shader添加到program
        GLES20.glAttachShader(program, fragmentShader);

        //创建可执行的OpenGL ES program
        GLES20.glLinkProgram(program);
    }

    public void draw(){
        //添加program到OpenGL ES环境中
        GLES20.glUseProgram(program);

        //获取指向vertex shader的成员vPosition的handle
        positionHandle = GLES20.glGetAttribLocation(program, "vPosition");

        //启用一个指向三角形的顶点数组的handle
        GLES20.glEnableVertexAttribArray(positionHandle);

        //准备三角形的坐标数据
        GLES20.glVertexAttribPointer(positionHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, vertexStride, vertexBuffer);

        //获取指向fragment shader的成员vColor的handle
        colorHandle = GLES20.glGetUniformLocation(program, "vColor");

        //绘制三角形
        GLES20.glUniform4fv(colorHandle, 1, color, 0);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);

        //禁用指向三角形的顶点数组
        GLES20.glDisableVertexAttribArray(positionHandle);
    }

}
