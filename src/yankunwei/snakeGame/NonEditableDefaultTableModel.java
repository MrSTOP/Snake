package yankunwei.snakeGame;

import javax.swing.table.DefaultTableModel;
import java.util.Vector;

/**
 * Description: 不可编辑的DefaultTableModel
 *
 * @author 闫坤炜
 * Date: 2019-01-04 16:33
 */
public class NonEditableDefaultTableModel extends DefaultTableModel {
    public NonEditableDefaultTableModel(Vector data, Vector columnNames) {
        super(data, columnNames);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}
