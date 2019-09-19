package com.example.lc.materialuitest.view;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.opengl.GLES11Ext;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class CameraGLSurfaceRenderer extends GLAbstractRender {

    private Context context;
    private int texture;
    private SurfaceTexture surfaceTexture;
    private FloatBuffer vertexBuffer;
    private FloatBuffer backTextureBuffer;
    private FloatBuffer frontTextureBuffer;
    private CameraGLSurfaceRendererCallback rendererCallback;
    private boolean isBackCamera;

    private float vertexData[] = {
            -1f, -1f, 0.0f,     //左下角
            1f, -1f, 0.0f,      //右下角
            -1f, 1f, 0.0f,      //左上角
            1f, 1f, 0.0f        //右上角
    };

    /**
     * 纹理坐标对应顶点坐标与后置摄像头映射
     */
    private float backTextureData[] = {
            1f, 1f,     //右上角
            1f, 0f,     //右下角
            0f, 1f,     //左上角
            0f, 0f      //左下角
    };

    /**
     * 纹理坐标对应顶点坐标与前置摄像头映射
     */
    private float frontTextureData[] = {
            0f, 1f,     //左上角
            0f, 0f,     //左下角
            1f, 1f,     //右上角
            1f, 0f      //右下角
    };

    //每次取得的数量
    private final int coordsPerVertexCount = 3;
    //顶点坐标数量
    private final int vertexCount = vertexData.length / coordsPerVertexCount;
    //一次取出的大小
    private final int vertexStride = coordsPerVertexCount * 4;

    //每次取点的数量
    private final int coordsPerTextureCount = 2;
    //一次取出的大小
    private final int textureStride = coordsPerTextureCount * 4;

    private int av_Position;
    private int af_Position;
    private int s_Texture;

    public CameraGLSurfaceRenderer(Context context, CameraGLSurfaceRendererCallback rendererCallback) {
        super(context);
        this.isBackCamera = true;
        this.context = context;
        this.rendererCallback = rendererCallback;

        this.vertexBuffer = ByteBuffer.allocateDirect(vertexData.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(vertexData);
        this.vertexBuffer.position(0);

        this.backTextureBuffer = ByteBuffer.allocateDirect(backTextureData.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(backTextureData);
        this.backTextureBuffer.position(0);

        this.frontTextureBuffer = ByteBuffer.allocateDirect(frontTextureData.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(backTextureData);
        this.frontTextureBuffer.position(0);
    }

    @Override
    protected void onCreate() {
        texture = loadExternelTexture();

        av_Position = GLES20.glGetAttribLocation(program, "av_Position");
        af_Position = GLES20.glGetAttribLocation(program, "af_Position");
        s_Texture = GLES20.glGetAttribLocation(program, "s_Texture");
        surfaceTexture = new SurfaceTexture(texture);
        surfaceTexture.setOnFrameAvailableListener(new SurfaceTexture.OnFrameAvailableListener() {
            @Override
            public void onFrameAvailable(SurfaceTexture surfaceTexture) {
                if(rendererCallback != null){
                    rendererCallback.onRequestRenderer();
                }
            }
        });
        if(rendererCallback != null){
            rendererCallback.onCreate(surfaceTexture);
        }
    }

    @Override
    protected void onChanged() {
        if(rendererCallback != null){
            rendererCallback.onChange(width, height);
        }
    }

    @Override
    protected void onDraw() {
        if(surfaceTexture != null){
            surfaceTexture.updateTexImage();
        }

        GLES20.glEnableVertexAttribArray(av_Position);
        GLES20.glEnableVertexAttribArray(af_Position);

        //设置顶点位置值
        GLES20.glVertexAttribPointer(av_Position, coordsPerVertexCount, GLES20.GL_FLOAT, false, vertexStride, vertexBuffer);
        //设置纹理位置值
        if(isBackCamera){
            GLES20.glVertexAttribPointer(af_Position, coordsPerTextureCount, GLES20.GL_FLOAT, false, textureStride, backTextureBuffer);
        }else {
            GLES20.glVertexAttribPointer(af_Position, coordsPerTextureCount, GLES20.GL_FLOAT, false, textureStride, frontTextureBuffer);
        }

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, texture);
        GLES20.glUniform1i(s_Texture, 0);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, vertexCount);
        GLES20.glDisableVertexAttribArray(av_Position);
        GLES20.glDisableVertexAttribArray(af_Position);

        if(rendererCallback != null){
            rendererCallback.onDraw();
        }
    }

    @Override
    protected String getVertexSource() {
        final String source = "attribute vec4 av_Position;" +
                "attribute vec2 af_Position;" +
                "varying vec2 v_texPo;" +
                "void main(){" +
                "   v_texPo = af_Position;" +
                "   gl_Position = av_Position;" +
                "}";
        return source;
    }

    @Override
    protected String getFragmentSource() {
        final String source = "#extension GL_OES_EGL_image_external : require \n" +
                "precision mediump float; " +
                "varying vec2 v_texPo; " +
                "uniform samplerExternalOES s_Texture; " +
                "void main() { " +
                "   gl_FragColor = texture2D(s_Texture, v_texPo); " +
                "} ";
        return source;
    }

    public SurfaceTexture getSurfaceTexture() {
        return surfaceTexture;
    }

    public void setBackCamera(boolean backCamera) {
        this.isBackCamera = backCamera;
    }

    public interface CameraGLSurfaceRendererCallback{
        void onRequestRenderer();
        void onCreate(SurfaceTexture surfaceTexture);
        void onChange(int width, int height);
        void onDraw();
    }


}
