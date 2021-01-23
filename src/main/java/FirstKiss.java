import org.junit.jupiter.api.Test;

import java.sql.*;

public class FirstKiss {
    public static void main(String[] args) throws SQLException {
        String db = "Exercise";
        String server = "192.168.81.8:3306";
        String url = "jdbc:mysql://" + server + "/" + db + "?useUnicode=true&characterEncoding=utf8";
        String user = "root";
        String password = "lovely0620";
        Connection connection = DriverManager.getConnection(url, user, password);
        Statement statement = connection.createStatement();

        // KISS 增
        String sql01 = "select * from Person;";
        int i = statement.executeUpdate(sql01);
        // KISS 删
        // KISS 改
        // KISS 查
        String sql00 = "select * from Person;";
        ResultSet resultSet00 = statement.executeQuery(sql00);

        statement.close();
        connection.close();
    }

    @Test
    public void run01() throws SQLException {
        String db = "Exercise";
        String server = "192.168.81.8:3306";
        String url = "jdbc:mysql://" + server + "/" + db + "?useUnicode=true&characterEncoding=utf8";
        String user = "root";
        String password = "lovely0620";
        Connection connection = DriverManager.getConnection(url, user, password);
        Statement statement = connection.createStatement();

        String sql = "insert into Person values(3,'yjy')";

        int len = statement.executeUpdate(sql);

        System.out.println(len>0?"成功":"失败");

        statement.close();
        connection.close();
    }

    @Test
    public void run02() throws SQLException {
        String db = "Exercise";
        String server = "192.168.81.8:3306";
        String url = "jdbc:mysql://" + server + "/" + db + "?useUnicode=true&characterEncoding=utf8";
        String user = "root";
        String password = "lovely0620";
        Connection connection = DriverManager.getConnection(url, user, password);
        Statement statement = connection.createStatement();

        String sql = "SELECT * FROM Person";

        ResultSet rs = statement.executeQuery(sql);//ResultSet看成InputStream
        while(rs.next()){//next()表示是否还有下一行
            Object did = rs.getObject(1);//获取第n列的值
            Object dname = rs.getObject(2);
			/*
			int did = rs.getInt("did");//也可以根据列名称，并且可以按照数据类型获取
			String dname = rs.getString("dname");
			String desc = rs.getString("description");
			 */

            System.out.println(did +"\t" + dname);
        }

        // 4、关闭
        rs.close();
        statement.close();
        connection.close();
    }
}
