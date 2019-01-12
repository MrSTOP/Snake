package yankunwei.snakeGame;

import javax.swing.*;
import java.sql.*;
import java.util.UUID;
import java.util.Vector;

/**
 * Description: JDBC数据库
 *
 * @author 闫坤炜
 * Date: 2018-12-23 11:46
 */
public class SQLiteDatabase {
    /**
     * 连接数据库并检查表是否存在不存在则创建
     */
    public static void connectToDatabase() {
        boolean isTableExist = false;
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:Score.db"); Statement statement = connection.createStatement()) {
            System.out.println("Open Database succeeded");
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            System.out.println(databaseMetaData.getDriverName());
            String sql = "CREATE TABLE SCORE " +
                    "(ID TEXT PRIMARY KEY     NOT NULL," +
                    " PlayerName           TEXT    NOT NULL, " +
                    " Score           INT     NOT NULL)";
            String checkTable = "SELECT name FROM sqlite_master";
            ResultSet table = statement.executeQuery(checkTable);
            System.out.println("CHECK");
            while (table.next() && !isTableExist) {
                if (table.getString("name").equals("SCORE")) {
                    isTableExist = true;
                    System.out.println("Table already exist");
                }
            }
            if (!isTableExist) {
                statement.executeUpdate(sql);
                System.out.println("Table create succeeded");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(), "数据库错误", JOptionPane.WARNING_MESSAGE);
            System.exit(-1);
        }
    }

    /**
     * @param name 玩家昵称
     * @param score 玩家分数
     */
    public static void insertScore(String name, int score) {
        connectToDatabase();
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:Score.db"); Statement statement = connection.createStatement()) {
            UUID uuid = UUID.randomUUID();
//            System.out.println("Database connect succeeded");
            String sql = String.format("INSERT INTO SCORE VALUES ('%s', '%s', %d)", uuid.toString(), name, score);
            statement.executeUpdate(sql);

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return 存储着玩家分数数据Vector的Vector
     */
    public static Vector<Vector<Object>> getScore() {
        connectToDatabase();
        Vector<Vector<Object>> scores = new Vector<>();
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:Score.db"); Statement statement = connection.createStatement()) {
            String sql = "SELECT * FROM SCORE";
            ResultSet resultSet = statement.executeQuery(sql);
            int columnCount = resultSet.getMetaData().getColumnCount();
            while (resultSet.next()) {
                    Vector<Object> score = new Vector<>();
                    for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                        score.add(resultSet.getObject(columnIndex));
                    }
                    scores.add(score);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return scores;
    }
}
