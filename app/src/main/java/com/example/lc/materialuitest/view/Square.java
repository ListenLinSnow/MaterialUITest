package com.example.lc.materialuitest.view;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public class Square {

    private FloatBuffer vertexBuffer;
    private ShortBuffer drawListBuffer;

    static final int COORDS_PER_VERTEX = 3;

    static float squareCoords[] = {
            -0.5f, 0.5f, 0.0f,      //左上
            -0.5f, -0.5f, 0.0f,     //左下
            0.5f, -0.5f, 0.0f,      //右下
            0.5f, 0.5f, 0.0f        //右上
    };

    private short drawOrder[] = {0, 1, 2, 0, 2, 3};         //绘制顶点的顺序

    public Square(){
        ByteBuffer buffer = ByteBuffer.allocateDirect(squareCoords.length * 4);
        buffer.order(ByteOrder.nativeOrder());
        vertexBuffer = buffer.asFloatBuffer();
        vertexBuffer.put(squareCoords);
        vertexBuffer.position(0);

        buffer = ByteBuffer.allocateDirect(drawOrder.length * 2);
        buffer.order(ByteOrder.nativeOrder());
        drawListBuffer = buffer.asShortBuffer();
        drawListBuffer.put(drawOrder);
        drawListBuffer.position(0);
    }

}
