package cn.itcast.estore.utils;

import java.sql.Connection;
import java.sql.SQLException;
import com.mchange.v2.c3p0.ComboPooledDataSource;  


import javax.sql.DataSource;


public class DataSourceUtils {

	private static DataSource dataSource = new ComboPooledDataSource();

	private static ThreadLocal<Connection> tl = new ThreadLocal<Connection>();

	public static DataSource getDataSource() {
		return dataSource;
	}

	public static Connection getConnection() throws SQLException {

		Connection con = tl.get();

		if (con == null) {
			con = dataSource.getConnection();
			tl.set(con);
		}

		return con;
	}

	public static void startTransaction() throws SQLException {

		getConnection().setAutoCommit(false);

	}
	
	public static void rollback() throws SQLException {
		getConnection().rollback();
	}

	public static void commitAndReleased() throws SQLException {

		getConnection().commit(); 
		getConnection().close();
		tl.remove();
	}

}
