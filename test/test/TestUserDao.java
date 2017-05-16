package test;

import java.sql.SQLException;

import cn.itcast.estore.dao.UserDao;
import cn.itcast.estore.domain.User;

public class TestUserDao {
	public static void main(String[] args) throws SQLException {
		
		User user = new User();
		UserDao ud = new UserDao();
		user.setActivecode("fsdajfhlsdfjk");
		user.setEmail("fjsdafkajsd");
		user.setNickname("hsadhf");
		user.setPassword("jdsfj");
		user.setUsername("hahahahhahahahah");
		
		ud.addUser(user);
	}
}
