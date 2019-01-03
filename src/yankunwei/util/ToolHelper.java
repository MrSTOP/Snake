package yankunwei.util;

import org.lwjgl.BufferUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * Description: 辅助工具类
 *
 * @author 闫坤炜
 * Date: 2018-12-20 22:30
 */
public class ToolHelper {
    private static final ToolHelper toolHelper = new ToolHelper();

    public static ToolHelper getInstant() {
        return toolHelper;
    }

    public URL getURL(String filePath) throws IllegalArgumentException {
        URL url = this.getClass().getResource(filePath);
        if (url != null) {
            return url;
        } else {
            throw new IllegalArgumentException("\"" + filePath + "\" is invalid file path, can't be transform to URL");
        }
    }

    public URI getURI(String filePath) throws URISyntaxException, IllegalArgumentException {
        URL url = this.getURL(filePath);
        if (url != null) {
            return url.toURI();
        } else {
            throw new IllegalArgumentException("\"" + filePath + "\" is invalid file path, can't be transform to URI");
        }
    }

    public InputStream getInputStream(String filePath) throws IllegalArgumentException {
        InputStream inputStream = ClassLoader.getSystemResourceAsStream(filePath);
        if (inputStream != null) {
            return inputStream;
        } else {
            throw new IllegalArgumentException("\"" + filePath + "\" is invalid file path, can't be transform to InputStream");
        }
    }

    public FloatBuffer generateFloatBuffer(float[] floats) {
        FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(floats.length);
        floatBuffer.put(floats);
        floatBuffer.flip();
        return floatBuffer;
    }

    public IntBuffer generateIntBuffer(int[] ints) {
        IntBuffer intBuffer = BufferUtils.createIntBuffer(ints.length);
        intBuffer.put(ints);
        intBuffer.flip();
        return intBuffer;
    }

    public String readShaderFile(String filePath) {
        StringBuilder stringBuilder = new StringBuilder();
        InputStreamReader inputStreamReader;
        BufferedReader bufferedReader;
        try {
            inputStreamReader = new InputStreamReader(this.getInputStream(filePath));
            bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append("\n");
            }
            bufferedReader.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

}
