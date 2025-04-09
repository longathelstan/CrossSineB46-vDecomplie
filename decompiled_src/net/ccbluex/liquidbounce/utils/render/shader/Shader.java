/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.utils.render.shader;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import org.apache.commons.io.IOUtils;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public abstract class Shader
extends MinecraftInstance {
    private int program;
    private Map<String, Integer> uniformsMap;

    public Shader(String fragmentShader) {
        int fragmentShaderID;
        int vertexShaderID;
        try {
            InputStream vertexStream = this.getClass().getResourceAsStream("/assets/minecraft/crosssine/shader/vertex.vert");
            vertexShaderID = this.createShader(IOUtils.toString((InputStream)vertexStream), 35633);
            IOUtils.closeQuietly((InputStream)vertexStream);
            InputStream fragmentStream = this.getClass().getResourceAsStream("/assets/minecraft/crosssine/shader/fragment/" + fragmentShader);
            fragmentShaderID = this.createShader(IOUtils.toString((InputStream)fragmentStream), 35632);
            IOUtils.closeQuietly((InputStream)fragmentStream);
        }
        catch (Exception e) {
            e.printStackTrace();
            return;
        }
        if (vertexShaderID == 0 || fragmentShaderID == 0) {
            return;
        }
        this.program = ARBShaderObjects.glCreateProgramObjectARB();
        if (this.program == 0) {
            return;
        }
        ARBShaderObjects.glAttachObjectARB((int)this.program, (int)vertexShaderID);
        ARBShaderObjects.glAttachObjectARB((int)this.program, (int)fragmentShaderID);
        ARBShaderObjects.glLinkProgramARB((int)this.program);
        ARBShaderObjects.glValidateProgramARB((int)this.program);
        ClientUtils.INSTANCE.logInfo("[Shader] Successfully loaded: " + fragmentShader);
    }

    public void startShader() {
        GL11.glPushMatrix();
        GL20.glUseProgram((int)this.program);
        if (this.uniformsMap == null) {
            this.uniformsMap = new HashMap<String, Integer>();
            this.setupUniforms();
        }
        this.updateUniforms();
    }

    public void stopShader() {
        GL20.glUseProgram((int)0);
        GL11.glPopMatrix();
    }

    public abstract void setupUniforms();

    public abstract void updateUniforms();

    private int createShader(String shaderSource, int shaderType) {
        int shader = 0;
        try {
            shader = ARBShaderObjects.glCreateShaderObjectARB((int)shaderType);
            if (shader == 0) {
                return 0;
            }
            ARBShaderObjects.glShaderSourceARB((int)shader, (CharSequence)shaderSource);
            ARBShaderObjects.glCompileShaderARB((int)shader);
            if (ARBShaderObjects.glGetObjectParameteriARB((int)shader, (int)35713) == 0) {
                throw new RuntimeException("Error creating shader: " + this.getLogInfo(shader));
            }
            return shader;
        }
        catch (Exception e) {
            ARBShaderObjects.glDeleteObjectARB((int)shader);
            throw e;
        }
    }

    private String getLogInfo(int i) {
        return ARBShaderObjects.glGetInfoLogARB((int)i, (int)ARBShaderObjects.glGetObjectParameteriARB((int)i, (int)35716));
    }

    public void setUniform(String uniformName, int location) {
        this.uniformsMap.put(uniformName, location);
    }

    public void setupUniform(String uniformName) {
        this.setUniform(uniformName, GL20.glGetUniformLocation((int)this.program, (CharSequence)uniformName));
    }

    public int getUniform(String uniformName) {
        return this.uniformsMap.get(uniformName);
    }

    public void setUniformf(String name, float ... args2) {
        int loc = GL20.glGetUniformLocation((int)this.program, (CharSequence)name);
        switch (args2.length) {
            case 1: {
                GL20.glUniform1f((int)loc, (float)args2[0]);
                break;
            }
            case 2: {
                GL20.glUniform2f((int)loc, (float)args2[0], (float)args2[1]);
                break;
            }
            case 3: {
                GL20.glUniform3f((int)loc, (float)args2[0], (float)args2[1], (float)args2[2]);
                break;
            }
            case 4: {
                GL20.glUniform4f((int)loc, (float)args2[0], (float)args2[1], (float)args2[2], (float)args2[3]);
            }
        }
    }

    public static void drawQuads(float x, float y, float width, float height) {
        GL11.glBegin((int)7);
        GL11.glTexCoord2f((float)0.0f, (float)0.0f);
        GL11.glVertex2f((float)x, (float)y);
        GL11.glEnd();
    }

    public static void drawQuad(float x, float y, float width, float height) {
        GL11.glBegin((int)7);
        GL11.glTexCoord2f((float)0.0f, (float)0.0f);
        GL11.glVertex2d((double)x, (double)(y + height));
        GL11.glTexCoord2f((float)1.0f, (float)0.0f);
        GL11.glVertex2d((double)(x + width), (double)(y + height));
        GL11.glTexCoord2f((float)1.0f, (float)1.0f);
        GL11.glVertex2d((double)(x + width), (double)y);
        GL11.glTexCoord2f((float)0.0f, (float)1.0f);
        GL11.glVertex2d((double)x, (double)y);
        GL11.glEnd();
    }
}

