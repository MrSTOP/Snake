package yankunwei.newRender;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL33;
import yankunwei.util.ToolHelper;

import java.nio.FloatBuffer;

/**
 * Description: 着色器
 *
 * @author 闫坤炜
 * Date: 2018-12-22 18:34
 */
public class Shader {
    protected int shaderProgram;

    protected int vertexShader;
    protected int fragmentShader;

    public Shader(String filePath) {
        this.vertexShader = GL33.glCreateShader(GL33.GL_VERTEX_SHADER);
        GL33.glShaderSource(this.vertexShader, ToolHelper.getInstant().readShaderFile(filePath + ".vert"));
        GL33.glCompileShader(this.vertexShader);
        if (GL33.glGetShaderi(this.vertexShader, GL33.GL_COMPILE_STATUS) != GL33.GL_TRUE) {
            System.err.println("Vertices ShaderLearn \"" + filePath + ".vert\" compile failed!");
            System.err.println(GL33.glGetShaderInfoLog(this.vertexShader));
            System.exit(1);
        }

        this.fragmentShader = GL33.glCreateShader(GL33.GL_FRAGMENT_SHADER);
        GL33.glShaderSource(this.fragmentShader, ToolHelper.getInstant().readShaderFile(filePath + ".frag"));
        GL33.glCompileShader(this.fragmentShader);
        if (GL33.glGetShaderi(this.fragmentShader, GL33.GL_COMPILE_STATUS) != GL33.GL_TRUE) {
            System.err.println("Fragment ShaderLearn \"" + filePath + ".frag\" compile failed!");
            System.err.println(GL33.glGetShaderInfoLog(this.fragmentShader));
            System.exit(1);
        }

        this.shaderProgram = GL33.glCreateProgram();
        GL33.glAttachShader(this.shaderProgram, this.vertexShader);
        GL33.glAttachShader(this.shaderProgram, this.fragmentShader);
        GL33.glLinkProgram(this.shaderProgram);
        if (GL33.glGetProgrami(this.shaderProgram, GL33.GL_LINK_STATUS) != GL33.GL_TRUE) {
            System.err.println("Link shader program \"" + filePath + ".vert\" \"" + filePath + ".frag failed!");
            System.err.println(GL33.glGetProgramInfoLog(this.shaderProgram));
            System.exit(1);
        }

        GL33.glDeleteShader(this.vertexShader);
        GL33.glDeleteShader(this.fragmentShader);
    }

    public void setInt(String name, int i) {
        GL33.glUniform1i(getLocation(name), i);
    }

    public void setFloat(String name, float f) {
        GL33.glUniform1f(getLocation(name), f);
    }

    public void setMatrix4F(String name, Matrix4f matrix4f) {
        FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(16);
        matrix4f.get(floatBuffer);
        GL33.glUniformMatrix4fv(getLocation(name), false, floatBuffer);
    }

    public void setVector3F(String name, Vector3f vector3f) {
        FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(3);
        vector3f.get(floatBuffer);
        GL33.glUniform3fv(getLocation(name), floatBuffer);
    }

    public void useShader() {
        GL33.glUseProgram(this.shaderProgram);
    }

    private int getLocation(String name) {
        int location = GL33.glGetUniformLocation(this.shaderProgram, name);
        if (location != -1) {
            return location;
        } else {
            throw new IllegalArgumentException("Uniform: \"" + name + "\"" + " don't exist!");
        }
    }
}
