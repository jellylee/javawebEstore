package cn.itcast.estore.service;

import java.sql.SQLException;

import cn.itcast.estore.dao.UserDao;
import cn.itcast.estore.domain.User;
import cn.itcast.estore.exception.ActiveUserException;
import cn.itcast.estore.exception.LoginException;
import cn.itcast.estore.exception.RegistException;
import cn.itcast.estore.utils.MailUtils;

public class UserServiceImpl implements UserService {
	// 注册功能实现
	@Override
	public void regist(User user) throws RegistException {

		// 调用dao中添加用户操作的方法

		try {
			new UserDao().addUser(user);

			// 发送邮件操作
			String emailMsg = "注册成功，请在12小时内<a href='http://www.bookEstore.com/user?method=activeuser&activecode="
					+ user.getActivecode() + "'>激活</a>，激活码是" + user.getActivecode();
			MailUtils.sendMail(user.getEmail(), emailMsg);

		} catch (SQLException e) {
			throw new RegistException("注册失败");
		} catch (Exception e) {
			throw new RegistException("邮件发送失败");
		}
	}

	// 激活功能实现
		@Override
		public void activeUser(String activecode) throws RegistException, ActiveUserException {

			// 调用一个dao中的方法，根据activecode查找用户
			UserDao dao = new UserDao();
			User user = null;
			try {
				user = dao.findUserByActiveCode(activecode);
			} catch (SQLException e) {
				throw new RegistException("根据激活码查找用户失败");
			}
			long time = System.currentTimeMillis() - user.getUpdatetime().getTime();

			// 判断它是否超时 开发是12小时，测试 1分钟
			if (time > 12 * 60 * 60 * 1000) {
				throw new ActiveUserException("激活码过期");
			}

			// 进行激活操作
			try {
				dao.activeUserByActivecode(activecode);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new ActiveUserException("激活失败");
			}

		}

	
	// 登录功能实现
	@Override
	public User login(String username, String password) throws LoginException {
		UserDao dao = new UserDao();
		User user = null;
		try {
			user = dao.findUserByUserNameAndPassword(username, password);

			if (user == null) {
				throw new LoginException("用户名或密码不正确");
			}
			// 判断用户的状态。
			if (user.getState() == 0) {
				// 用户状态未激活，不能进行登录操作
				throw new LoginException("用户未激活");
			}

		} catch (SQLException e) {
			throw new LoginException("登录失败");
		}

		return user;
	}
	
}
