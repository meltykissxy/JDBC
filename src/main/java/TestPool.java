import com.alibaba.druid.pool.DruidDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class TestPool {
	public static void main(String[] args) throws SQLException {
		//1、创建数据源（数据库连接池）对象
		DruidDataSource ds =new DruidDataSource();

		//2、设置参数
		//(1)设置基本参数
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUrl("jdbc:mysql://localhost:3306/test");
		ds.setUsername("root");
		ds.setPassword("123456");

		//(2)设置连接数等参数
		ds.setInitialSize(5);//一开始提前申请好5个连接，不够了，重写申请
		ds.setMaxActive(10);//最多不超过10个，如果10都用完了，还没还回来，就会出现等待
		ds.setMaxWait(1000);//用户最多等1000毫秒，如果1000毫秒还没有人还回来，就异常了

		//3、获取连接
		for (int i = 1; i <=15; i++) {
			Connection conn = ds.getConnection();
			System.out.println("第"+i+"个：" + conn);

			//如果这里没有关闭，就相当于没有还
//			conn.close();#这里关闭，是还回池中
		}
	}
}
