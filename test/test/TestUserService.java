package test;

import cn.itcast.estore.dao.UserDao;
import cn.itcast.estore.domain.User;
import cn.itcast.estore.exception.RegistException;
import cn.itcast.estore.service.UserService;
import cn.itcast.estore.service.UserServiceImpl;

public class TestUserService {

	public static void main(String[] args) throws RegistException {

		User user = new User();
		user.setActivecode("testService");
		user.setEmail("jellylees@163.com");
		user.setNickname("jellylee");
		user.setPassword("hlell");
		user.setUsername("nihao");
		
		UserServiceImpl us = new UserServiceImpl();
		us.regist(user);
	}

}
