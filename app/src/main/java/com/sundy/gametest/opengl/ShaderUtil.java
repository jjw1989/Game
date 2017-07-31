package com.sundy.gametest.opengl;

import android.opengl.GLES20;

/**
 * 着色器工具类
 * Created by David on 2017/7/15.
 */

public class ShaderUtil {

    public static int loadShader(int shaderType, String source) {
        int shader = GLES20.glCreateShader(shaderType);
        if (shader != 0) {
            GLES20.glShaderSource(shader, source);
            GLES20.glCompileShader(shader);
            int[] compiled = new int[1];
            GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compiled, 0);
            
        }
        return shader;
    }
}
