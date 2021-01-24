package util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCUtils {
//    public static Connection getConnection() throws IOException, SQLException {
//        Properties property = PropertiesUtil.load("config.properties");
//        String url = "jdbc:mysql://" + property.getProperty("mysql.server") + "/" + property.getProperty("mysql.db") + "?useUnicode=true&characterEncoding=utf8";
//        Connection connection = DriverManager.getConnection(url, property.getProperty("mysql.user"), property.getProperty("mysql.password"));
//        return connection;
//    }

    public static Connection getConnection() throws Exception {
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("config.properties");
        Properties pros = new Properties();
        pros.load(is);

        String url = "jdbc:mysql://" + pros.getProperty("mysql.server") + "/" + pros.getProperty("mysql.db") + "?useUnicode=true&characterEncoding=utf8";
        Connection conn = DriverManager.getConnection(url, pros.getProperty("mysql.user"), pros.getProperty("mysql.password"));
        return conn;
    }

    public static void closeResource(Connection conn, PreparedStatement ps) throws SQLException {
        if (ps != null)
            ps.close();

        if (conn != null)
            conn.close();
    }

    public static void update(String sql, Object... args) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResource(conn, ps);
        }
    }
}
