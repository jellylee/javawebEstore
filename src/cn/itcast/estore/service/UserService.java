package cn.itcast.estore.service;

import cn.itcast.estore.domain.User;

public interface UserService{

	//注册操作
	public void regist(User user) throws Exception;
	
	//登录操作
	public User login(String username ,String password) throws Exception;
	
	//激活操作
	public void activeUser(String activecode) throws Exception;
}