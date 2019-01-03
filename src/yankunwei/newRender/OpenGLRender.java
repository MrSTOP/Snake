package yankunwei.newRender;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWFramebufferSizeCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL33;
import org.lwjgl.system.MemoryUtil;
import yankunwei.snakeGame.*;
import yankunwei.snakeGame.food.FoodRenderInfo;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.nio.ByteBuffer;

/**
 * Description: OpenGL
 *
 * @author 闫坤炜
 * Date: 2018-12-29 19:24
 */
public class OpenGLRender {
    private float[] vertices = new float[]{
            //     ---- 位置 -- - 纹理坐标 -
             0.5f,  0.5f, 0.0f, 1.0f, 1.0f,   // 右上
             0.5f, -0.5f, 0.0f, 1.0f, 0.0f,   // 右下
            -0.5f, -0.5f, 0.0f, 0.0f, 0.0f,   // 左下
            -0.5f,  0.5f, 0.0f, 0.0f, 1.0f    // 左上
    };

    private float[] snakebody = new float[]{
             0.015F,  0.015F, 0.0F, 1F, 0F,
             0.015F, -0.015F, 0.0F, 1F, 1F,
            -0.015F, -0.015F, 0.0F, 0F, 1F,
            -0.015F,  0.015F, 0.0F, 0F, 0F,
    };

    private int[] indices = new int[]{
            0, 1, 3,
            1, 2, 3
    };

    private Model triangleModel;
    private Model snakeBodyModel;
    private Shader triangleShader;
    private imgTexture snakeBodyTexture;
    private imgTexture snakeHeadTexture;
    private imgTexture heartTexture;


    private GameComponent gameComponent;
    private Thread OpenGLRenderThread;

    protected static final int DEFAULT_WINDOW_HEIGHT = SnakePanel.DEFAULT_HEIGHT;
    protected static final int DEFAULT_WINDOW_WIDTH = SnakePanel.DEFAULT_WIDTH;
    protected long window;

    protected GLFWErrorCallback glfwErrorCallback = GLFWErrorCallback.createPrint(System.err);

    protected GLFWFramebufferSizeCallback glfwFramebufferSizeCallback = new GLFWFramebufferSizeCallback() {
        @Override
        public void invoke(long window, int width, int height) {
            GL33.glViewport(0, 0, width, height);
        }
    };
    private FoodManager foodManager;
    private Snake snake;
    private WallManager wallManager;
    private boolean render;

    private static OpenGLRender openGLRender = new OpenGLRender();

    public OpenGLRender() {
        this.render = true;
        OpenGLRenderThread = new Thread(() -> {
            initGLFW();
            initOpenGL();
            initShape();
//            GLFW.glfwShowWindow(window);
            while (!GLFW.glfwWindowShouldClose(window) && !Thread.currentThread().isInterrupted()) {
                while (render) {

                    GL33.glClear(GL33.GL_COLOR_BUFFER_BIT);

                    GLFW.glfwPollEvents();
                    triangleShader.useShader();
                    for (FoodRenderInfo info: foodManager.getFoodRenderInfo()) {
                        Matrix4f transform = new Matrix4f();
                        transform.translate(info.x, info.y, 0.0F);
                        if (info.type == FoodRenderInfo.FoodType.HEART) {
                            transform.translate(0.01F, 0.0F, 0.0F);
                            transform.scale(1.4F);
                            heartTexture.bind(0);
                        }
                        if (info.type == FoodRenderInfo.FoodType.PLUS){
                            snakeBodyTexture.bind(0);
                        }
                        triangleShader.setMatrix4F("transform", transform);
                        snakeBodyModel.render();
                    }

                    for (SnakeRenderInfo info: snake.getsnakeRenderInfo()) {
//                    System.out.println(point);
                        Matrix4f transform = new Matrix4f();
                        transform.translate(info.x, info.y, 0.1F);
                        if (info.type == SnakeRenderInfo.SnakeType.BODY) {
                            snakeBodyTexture.bind(0);
                        }
                        if (info.type == SnakeRenderInfo.SnakeType.HEAD) {
//                            transform.scale(2);
                            transform.rotate((float) (Math.PI * info.dir / 2), new Vector3f(0.0F, 0.0F, 1.0F));
                            snakeHeadTexture.bind(0);
                        }
                        triangleShader.setMatrix4F("transform", transform);
                        snakeBodyModel.render();
                    }

                    for (Point2D.Float info: wallManager.getWallRenderInfo()) {
                        Matrix4f transform = new Matrix4f();
                        transform.translate(info.x, info.y, 0.1F);
                        snakeBodyTexture.bind(0);
                        triangleShader.setMatrix4F("transform", transform);
                        snakeBodyModel.render();
                        System.out.println(info);
                    }
//                Matrix4f transform = new Matrix4f();
//                triangleShader.setMatrix4F("transform", transform);
//                    heartTexture.bind(0);
//                triangleModel.render();

//                snakeBodyModel.render();
                    updateFrame();
                    gameComponent.repaint();
                    GLFW.glfwSwapBuffers(window);
                }
            }
            clean();
            System.out.println("OpenGL Render Thread Stop");
        }, "OpenGL Render Thread");
        OpenGLRenderThread.start();
    }

    public static OpenGLRender getInstant() {
        return openGLRender;
    }

    public void updateRenderInfo(GameComponent gameComponent, Snake snake, FoodManager foodManager, WallManager wallManager) {
        this.gameComponent = gameComponent;
        this.snake = snake;
        this.foodManager = foodManager;
        this.wallManager = wallManager;
    }

    public void stopRender() {
        this.render = false;
    }

    public void startRender() {
        this.render = true;
    }

    protected void updateFrame() {
        ByteBuffer nativeBuffer = BufferUtils.createByteBuffer(DEFAULT_WINDOW_WIDTH * DEFAULT_WINDOW_HEIGHT * 3);
        BufferedImage image = new BufferedImage(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT, BufferedImage.TYPE_3BYTE_BGR);
        GL33.glReadPixels(0, 0, DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT, GL33.GL_BGR, GL11.GL_UNSIGNED_BYTE, nativeBuffer);
        byte[] imgData = ((DataBufferByte)image.getRaster().getDataBuffer()).getData();
        nativeBuffer.get(imgData);
        gameComponent.setImage(image);
    }

    private void initGLFW() {
        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);

        window = GLFW.glfwCreateWindow(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT, "Hello Triangle!", 0, 0);
        if (window == MemoryUtil.NULL) {
            GLFW.glfwTerminate();
            throw new RuntimeException("Failed to create the GLFW window");
        }

        initGLFWCallback();

        GLFWVidMode glfwVidMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
        int posX = (glfwVidMode.width() - DEFAULT_WINDOW_WIDTH) / 2;
        int posY = (glfwVidMode.height() - DEFAULT_WINDOW_HEIGHT) / 2;
        GLFW.glfwSetWindowPos(window, posX, posY);

        GLFW.glfwMakeContextCurrent(window);
    }

    protected void initGLFWCallback() {
        GLFW.glfwSetErrorCallback(glfwErrorCallback);
        GLFW.glfwSetFramebufferSizeCallback(window, glfwFramebufferSizeCallback);
    }

    private void initOpenGL() {
        GL.createCapabilities();
        GL33.glViewport(0, 0, DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);
        GL33.glClearColor(0.2F, 0.3F, 0.3F, 1.0F);
//        GL33.glEnable(GL33.GL_DEPTH_TEST);
//        GL33.glEnable(GL33.GL_CULL_FACE);
    }

    protected void initShape() {
        this.triangleModel = new Model(vertices, indices);
        this.snakeBodyModel = new Model(snakebody, indices);
        this.triangleShader = new Shader("resources/shaders/shader");
        this.snakeBodyTexture = new imgTexture(new String[]{"/resources/images/snake.png"});
        this.snakeHeadTexture = new imgTexture(new String[]{"/resources/images/snake_head.png"});
        this.heartTexture = new imgTexture(new String[]{"/resources/images/heart.png"});
    }

    protected void clean() {
        GLFW.glfwTerminate();
        glfwErrorCallback.free();
        glfwFramebufferSizeCallback.free();
    }
}
