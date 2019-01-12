package yankunwei.snakeGame;

import javax.swing.table.DefaultTableModel;

/**
 * Description: 不可编辑的DefaultTableModel
 *
 * @author 闫坤炜
 * Date: 2019-01-04 16:33
 */
public class NonEditableTableModel extends DefaultTableModel {
    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}
