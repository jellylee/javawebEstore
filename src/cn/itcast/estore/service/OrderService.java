package cn.itcast.estore.service;

import java.sql.SQLException;
import java.util.List;

import cn.itcast.estore.annotation.PrivilegeInfo;
import cn.itcast.estore.domain.Order;
import cn.itcast.estore.domain.User;
import cn.itcast.estore.exception.PrivilegeException;

public interface OrderService {

	// 添加订单
	@PrivilegeInfo("生成订单")
	public void add(User user, Order order) throws PrivilegeException,
			Exception;

	// 根据用户查找订单
	@PrivilegeInfo("查看订单")
	public List<Order> find(User user) throws PrivilegeException, Exception;

	// 根据id删除订单
	public void delete(String id) throws Exception;

	public void updateState(String id) throws Exception;
}
