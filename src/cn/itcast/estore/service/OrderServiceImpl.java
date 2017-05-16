package cn.itcast.estore.service;

import java.sql.SQLException;
import java.util.List;

import cn.itcast.estore.dao.OrderDao;
import cn.itcast.estore.dao.OrderItemDao;
import cn.itcast.estore.dao.ProductDao;
import cn.itcast.estore.domain.Order;
import cn.itcast.estore.domain.OrderItem;
import cn.itcast.estore.domain.User;
import cn.itcast.estore.exception.OrderException;
import cn.itcast.estore.exception.PrivilegeException;
import cn.itcast.estore.utils.DataSourceUtils;

public class OrderServiceImpl implements OrderService {
	// 添加订单
	public void add(User user, Order order) throws PrivilegeException {
		OrderDao dao = new OrderDao();
		OrderItemDao idao = new OrderItemDao();
		ProductDao pdao = new ProductDao();

		try {

			// 开启事务
			DataSourceUtils.startTransaction();
			// 1.向orders表中添加数据
			dao.createOrder(order);
			// 2.向orderitem表中添加数据
			idao.addOrderItem(order);
			// 3.修改products表中数据
			pdao.updateProductCount(order);
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				DataSourceUtils.rollback(); // 事务回滚
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				DataSourceUtils.commitAndReleased(); // 事务提交与释放
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	// 根据用户查找订单
	public List<Order> find(User user) throws PrivilegeException, SQLException {

		List<Order> orders = new OrderDao().findOrder(user); // 查询订单信息,不包含订单中的商品信息

		OrderItemDao idao = new OrderItemDao();

		// 根据得到的订单，查询订单中商品信息.
		for (Order order : orders) {

			List<OrderItem> items = idao.findOrderItemByOrderId(order);

			order.setOrderItems(items);

		}

		return orders;
	}

	// 根据id删除订单
	public void delete(String id) throws OrderException {

		OrderDao dao = new OrderDao();
		OrderItemDao idao = new OrderItemDao();
		ProductDao pdao = new ProductDao();
		// 1.修改商品表中商品数量

		try {
			DataSourceUtils.startTransaction(); //开启事务
			// 1.1 得到商品的数量
			List<OrderItem> items = idao.findOrderItemByOrderId(id);
			// 1.2修改商品的数量
			pdao.updateProductCount(items);
			// 2.删除订单项
			idao.delOrderItem(id);
			// 3.删除订单
			dao.delOrder(id);

		} catch (SQLException e) {
			e.printStackTrace();

			try {
				DataSourceUtils.rollback(); //事务回滚
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			throw new OrderException("删除订单失败");

		} finally {
			try {
				DataSourceUtils.commitAndReleased(); //事务提交
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	// 根据订单号修改订单状态
	public void updateState(String id) throws SQLException {
		OrderDao dao = new OrderDao();

		dao.updateState(id);
	}

}
