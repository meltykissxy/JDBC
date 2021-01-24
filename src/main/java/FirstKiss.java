import org.junit.jupiter.api.Test;
import util.JDBCUtils;
import util.PropertiesUtil;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class FirstKiss {
    private static Properties property;

    static {
        try {
            property = PropertiesUtil.load("config.properties");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String url = "jdbc:mysql://" + property.getProperty("mysql.server") + "/" + property.getProperty("mysql.db") + "?useUnicode=true&characterEncoding=utf8";

    public static void main(String[] args) throws SQLException {
        String url = "jdbc:mysql://" + property.getProperty("mysql.server") + "/" + property.getProperty("mysql.db") + "?useUnicode=true&characterEncoding=utf8";
        Connection connection = DriverManager.getConnection(url, property.getProperty("mysql.user"), property.getProperty("mysql.password"));
        Statement statement = connection.createStatement();

        statement.close();
        connection.close();
    }

    // KISS 以后直接用这种工具类的方式增删改
    @Test
    public void run07() throws SQLException {
        String sql = "insert into Person values(?, ?, ?)";
        JDBCUtils.update(sql, 7, "chj", 22);
    }

    @Test
    public void run06() throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "insert into Person values(?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setObject(1, 6);
            ps.setObject(2, "sym");
            ps.setObject(3, 21);
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, ps);
        }
    }

    // KISS 推荐使用：增删改
    @Test
    public void run04() throws SQLException {
        try (Connection conn = DriverManager.getConnection(url, property.getProperty("mysql.user"), property.getProperty("mysql.password"))) {
            try (PreparedStatement ps = conn.prepareStatement("insert into Person values(?, ?, ?)")) {
                ps.setInt(1, 5);
                ps.setString(2, "ly");
                ps.setInt(3, 16);
                System.out.println(ps.executeUpdate());
            }
        }
    }

    // KISS 推荐使用：条件查询
    @Test
    public void run03() throws SQLException {
        try (Connection conn = DriverManager.getConnection(url, property.getProperty("mysql.user"), property.getProperty("mysql.password"))) {
            try (PreparedStatement ps = conn.prepareStatement("SELECT id, name, age FROM Person WHERE name = ?")) {
                ps.setString(1, "lss");
                try (ResultSet rs = ps.executeQuery()) {
                    System.out.println("ID" + "\t" + "姓名" + "\t" + "年龄");
                    while (rs.next()) {
                        int id = rs.getInt(1);
                        String name = rs.getString(2);
                        int age = rs.getInt(3);
                        System.out.println(id + "\t" + name + "\t" + age);
//                        Object object1 = rs.getObject(1);
//                        Object object2 = rs.getObject(2);
//                        Object object3 = rs.getObject(3);
//                        System.out.println(object1 + "\t" + object2 + "\t" + object3);
                    }
                }
            }
        }
    }
    // KISS 推荐使用：全查询
    @Test
    public void run05() throws SQLException {
        try (Connection conn = DriverManager.getConnection(url, property.getProperty("mysql.user"), property.getProperty("mysql.password"))) {
            try (PreparedStatement ps = conn.prepareStatement("SELECT id, name, age FROM Person")) {
                try (ResultSet rs = ps.executeQuery()) {
                    System.out.println("ID" + "\t" + "姓名" + "\t" + "年龄");
                    while (rs.next()) {
                        int id = rs.getInt(1);
                        String name = rs.getString(2);
                        int age = rs.getInt(3);
                        System.out.println(id + "\t" + name + "\t" + age);
                    }
                }
            }
        }
    }

    // KISS insert into Person values(4,'dxy', 20)
    @Test
    public void run01() throws SQLException {
        Connection connection = DriverManager.getConnection(url, property.getProperty("mysql.user"), property.getProperty("mysql.password"));
        Statement statement = connection.createStatement();

        String sql = "insert into Person values(4,'dxy', 20)";

        int len = statement.executeUpdate(sql);

        System.out.println(len>0 ? "成功":"失败");

        statement.close();
        connection.close();
    }

    // KISS SELECT * FROM PERSON;
    // KISS 不推荐Statement，用prepareStatement
    @Test
    public void run02() throws SQLException {
        Connection connection = DriverManager.getConnection(url, property.getProperty("mysql.user"), property.getProperty("mysql.password"));
        Statement statement = connection.createStatement();

        String sql = "SELECT * FROM Person";

        System.out.println("ID" + "\t" + "姓名" + "\t" + "年龄");

        ResultSet rs = statement.executeQuery(sql);//ResultSet看成InputStream
        while(rs.next()){//next()表示是否还有下一行
            Object did = rs.getObject(1);//获取第n列的值
            Object dname = rs.getObject(2);
            Object dage = rs.getObject(3);
			/*
			int did = rs.getInt("did");//也可以根据列名称，并且可以按照数据类型获取
			String dname = rs.getString("dname");
			String desc = rs.getString("description");
			 */

            System.out.println(did + "\t" + dname + "\t" + dage);
        }

        // 4、关闭
        rs.close();
        statement.close();
        connection.close();
    }
}
