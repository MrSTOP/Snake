package yankunwei.newRender;

import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL33;

import java.nio.ByteBuffer;

/**
 * Description:
 *
 * @author 闫坤炜
 * Date: 2019-01-02 18:03
 */
public class imgTexture extends Texture {

    public imgTexture(String[] fileNames) {
        initTexture(fileNames);
    }

    @Override
    protected void initTexture(String[] fileNames) {
        for (int i = 0; i < fileNames.length; i++) {
            this.texture[i] = GL33.glGenTextures();
            GL33.glActiveTexture(GL33.GL_TEXTURE0 + i);
            GL33.glBindTexture(GL33.GL_TEXTURE_2D, texture[i]);

            GL33.glTexParameteri(GL33.GL_TEXTURE_2D, GL33.GL_TEXTURE_WRAP_S, GL33.GL_REPEAT);
            GL33.glTexParameteri(GL33.GL_TEXTURE_2D, GL33.GL_TEXTURE_WRAP_T, GL33.GL_REPEAT);
            GL33.glTexParameteri(GL33.GL_TEXTURE_2D, GL33.GL_TEXTURE_MIN_FILTER, GL33.GL_LINEAR);
            GL33.glTexParameteri(GL33.GL_TEXTURE_2D, GL33.GL_TEXTURE_MAG_FILTER, GL33.GL_LINEAR);

            ByteBuffer img = getRGBAImageByteBuffer(fileNames[i], i);
            GL33.glTexImage2D(GL33.GL_TEXTURE_2D, 0, GL33.GL_RGBA, width[i], height[i], 0, GL33.GL_RGBA, GL33.GL_UNSIGNED_BYTE, img);
        }
    }

    @Override
    public void bind(int sampler) {
        GL33.glActiveTexture(GL13.GL_TEXTURE0);
        GL33.glBindTexture(GL33.GL_TEXTURE_2D, texture[0]);
    }
}
