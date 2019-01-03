package yankunwei.newRender;

import org.lwjgl.opengl.GL33;
import yankunwei.util.ToolHelper;

/**
 * Description: 模型
 *
 * @author 闫坤炜
 * Date: 2018-12-22 18:41
 */
public class Model {
    protected int VBO;
    protected int VAO;
    protected int EBO;
    private int texture;

    public Model() {
    }

    public Model(float[] vertices, int[] indices) {
        initModel(vertices, indices);
    }

    protected void initModel(float[] vertices, int[] indices) {
        this.VAO = GL33.glGenVertexArrays();
        this.VBO = GL33.glGenBuffers();
        this.EBO = GL33.glGenBuffers();

        GL33.glBindVertexArray(this.VAO);
        GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, this.VBO);
        GL33.glBufferData(GL33.GL_ARRAY_BUFFER, ToolHelper.getInstant().generateFloatBuffer(vertices), GL33.GL_STATIC_DRAW);

        GL33.glBindBuffer(GL33.GL_ELEMENT_ARRAY_BUFFER, this.EBO);
        GL33.glBufferData(GL33.GL_ELEMENT_ARRAY_BUFFER, ToolHelper.getInstant().generateIntBuffer(indices), GL33.GL_STATIC_DRAW);

        GL33.glVertexAttribPointer(0, 3, GL33.GL_FLOAT, false, 20, 0);
        GL33.glEnableVertexAttribArray(0);

        GL33.glVertexAttribPointer(1, 2, GL33.GL_FLOAT, false, 20, 12);
        GL33.glEnableVertexAttribArray(1);


        GL33.glBindVertexArray(0);
        GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, 0);
        GL33.glBindBuffer(GL33.GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    public void render() {
        GL33.glBindVertexArray(this.VAO);
//        GL33.glPolygonMode(GL33.GL_FRONT_AND_BACK, GL33.GL_LINE);
        GL33.glDrawElements(GL33.GL_TRIANGLES, 6, GL33.GL_UNSIGNED_INT, 0);
//        GL33.glPolygonMode(GL33.GL_FRONT_AND_BACK, GL33.GL_FILL);
        GL33.glBindVertexArray(0);
    }
}
