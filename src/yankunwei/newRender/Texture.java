package yankunwei.newRender;

import org.lwjgl.BufferUtils;
import yankunwei.util.ToolHelper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Description: 材质
 *
 * @author 闫坤炜
 * Date: 2018-12-22 19:12
 */
public abstract class Texture {

    protected int texture[];
    protected int width[];
    protected int height[];





//    protected int width1;
//    protected int height1;
//    protected int texture1;

    public Texture() {
        this.texture = new int[16];
        this.width = new int[16];
        this.height = new int[16];
    }

//    protected ByteBuffer getRGBAImageByteBuffer(String filePath) {
//        ByteBuffer pixelByteBuffer = null;
//        try {
//            BufferedImage bufferedImage = ImageIO.read(ToolHelper.getInstant().getURL(filePath));
//            this.width1 = bufferedImage.getWidth();
//            this.height1 = bufferedImage.getHeight();
//            int[] rawPixels = new int[width1 * height1 * 4];
//            bufferedImage.getRGB(0, 0, width1, height1, rawPixels, 0, width1);
//            pixelByteBuffer = BufferUtils.createByteBuffer(width1 * height1 * 4);
//            for (int i = 0; i < width1; i++) {
//                for (int j = 0; j < height1; j++) {
//                    int pixel = rawPixels[i * width1 + j];
//                    pixelByteBuffer.put((byte) ((pixel >> 16) & 0xFF)); //RED
//                    pixelByteBuffer.put((byte) ((pixel >> 8) & 0xFF));  //GREEN
//                    pixelByteBuffer.put((byte) ((pixel) & 0xFF));       //BLUE
//                    pixelByteBuffer.put((byte) ((pixel >> 24) & 0xFF)); //ALPHA
//                }
//            }
//            pixelByteBuffer.flip();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return pixelByteBuffer;
//    }
//
//    protected ByteBuffer getRGBImageByteBuffer(String filePath) {
//        ByteBuffer pixelByteBuffer = null;
//        try {
//            BufferedImage bufferedImage = ImageIO.read(ToolHelper.getInstant().getURL(filePath));
//            this.width1 = bufferedImage.getWidth();
//            this.height1 = bufferedImage.getHeight();
//            int[] rawPixels = new int[width1 * height1 * 3];
//            bufferedImage.getRGB(0, 0, width1, height1, rawPixels, 0, width1);
//            pixelByteBuffer = BufferUtils.createByteBuffer(width1 * height1 * 3);
//            for (int i = 0; i < width1; i++) {
//                for (int j = 0; j < height1; j++) {
//                    int pixel = rawPixels[i * width1 + j];
//                    pixelByteBuffer.put((byte) ((pixel >> 16) & 0xFF)); //RED
//                    pixelByteBuffer.put((byte) ((pixel >> 8) & 0xFF));  //GREEN
//                    pixelByteBuffer.put((byte) ((pixel) & 0xFF));       //BLUE
//                }
//            }
//            pixelByteBuffer.flip();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return pixelByteBuffer;
//    }

    protected ByteBuffer getRGBAImageByteBuffer(String filePath, int index) {
        ByteBuffer pixelByteBuffer = null;
        try {
            BufferedImage bufferedImage = ImageIO.read(ToolHelper.getInstant().getURL(filePath));
            this.width[index] = bufferedImage.getWidth();
            this.height[index] = bufferedImage.getHeight();
            int[] rawPixels = new int[width[index] * height[index] * 4];
            bufferedImage.getRGB(0, 0, width[index], height[index], rawPixels, 0, width[index]);
            pixelByteBuffer = BufferUtils.createByteBuffer(width[index] * height[index] * 4);
            for (int i = width[index]; i > 0 ; i--) {
                for (int j = 0; j < height[index]; j++) {
                    int pixel = rawPixels[i * width[index] + j];
                    pixelByteBuffer.put((byte) ((pixel >> 16) & 0xFF)); //RED
                    pixelByteBuffer.put((byte) ((pixel >> 8) & 0xFF));  //GREEN
                    pixelByteBuffer.put((byte) ((pixel) & 0xFF));       //BLUE
                    pixelByteBuffer.put((byte) ((pixel >> 24) & 0xFF)); //ALPHA
                }
            }
            pixelByteBuffer.flip();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pixelByteBuffer;
    }

    protected ByteBuffer getRGBImageByteBuffer(String filePath, int index) {
        ByteBuffer pixelByteBuffer = null;
        try {
            BufferedImage bufferedImage = ImageIO.read(ToolHelper.getInstant().getURL(filePath));
            this.width[index] = bufferedImage.getWidth();
            this.height[index] = bufferedImage.getHeight();
            int[] rawPixels = new int[width[index] * height[index] * 3];
            bufferedImage.getRGB(0, 0, width[index], height[index], rawPixels, 0, width[index]);
            pixelByteBuffer = BufferUtils.createByteBuffer(width[index] * height[index] * 3);
            for (int i = 0; i < width[index]; i++) {
                for (int j = 0; j < height[index]; j++) {
                    int pixel = rawPixels[i * width[index] + j];
                    pixelByteBuffer.put((byte) ((pixel >> 16) & 0xFF)); //RED
                    pixelByteBuffer.put((byte) ((pixel >> 8) & 0xFF));  //GREEN
                    pixelByteBuffer.put((byte) ((pixel) & 0xFF));       //BLUE
                }
            }
            pixelByteBuffer.flip();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pixelByteBuffer;
    }

    protected abstract void initTexture(String[] fileNames);

    public abstract void bind(int sampler);
}
