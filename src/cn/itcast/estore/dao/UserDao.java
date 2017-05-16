package cn.itcast.estore.dao;

import cn.itcast.estore.domain.User;
import cn.itcast.estore.utils.DataSourceUtils;
import cn.itcast.estore.utils.Md5Utils;
import java.sql.SQLException;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

public class UserDao {
	public void addUser(User user) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());

		String sql = "insert into users values(null,?,?,?,?,'user','0',?,null)";

		runner.update(sql, new Object[] { user.getUsername(), Md5Utils.md5(user.getPassword()), user.getNickname(),
				user.getEmail(), user.getActivecode() });
	}

	public User findUserByActiveCode(String activecode) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());

		String sql = "select * from users where activecode=?";

		return ((User) runner.query(sql, new BeanHandler(User.class), new Object[] { activecode }));
	}
	

	public void activeUserByActivecode(String activecode) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());

		String sql = "update users set state=1 where activecode=?";

		runner.update(sql, activecode);
	}

	public User findUserByUserNameAndPassword(String username, String password) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());

		String sql = "select * from users where username=? and password=?";

		return ((User) runner.query(sql, new BeanHandler(User.class),
				new Object[] { username, Md5Utils.md5(password) }));
	}
}