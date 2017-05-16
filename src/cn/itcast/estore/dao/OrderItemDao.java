package cn.itcast.estore.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.itcast.estore.domain.Order;
import cn.itcast.estore.domain.OrderItem;
import cn.itcast.estore.utils.DataSourceUtils;

public class OrderItemDao {

	// 添加订单项
	public void addOrderItem(Order order) throws SQLException {

		List<OrderItem> items = order.getOrderItems();

		Object[][] params = new Object[items.size()][3];

		for (int i = 0; i < items.size(); i++) {

			OrderItem item = items.get(i);

			params[i][0] = item.getOrder_id();
			params[i][1] = item.getProduct_id();
			params[i][2] = item.getBuynum();
		}

		String sql = "insert into orderitem values(?,?,?)";

		QueryRunner runner = new QueryRunner();

		runner.batch(DataSourceUtils.getConnection(), sql, params);

	}

	// 根据订单查询订单项
	public List<OrderItem> findOrderItemByOrderId(Order order)
			throws SQLException {

		String sql = "select * from orderitem,products where orderitem.product_id=products.id and  order_id=?";
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());

		return runner.query(sql,
				new BeanListHandler<OrderItem>(OrderItem.class), order.getId());
	}

	// 根据订单id查询所有订单项
	public List<OrderItem> findOrderItemByOrderId(String id)
			throws SQLException {
		String sql = "select * from orderitem where order_id=?";
		QueryRunner runner = new QueryRunner();
		return runner.query(DataSourceUtils.getConnection(),sql,
				new BeanListHandler<OrderItem>(OrderItem.class), id);
	}

	//删除订单项
	public void delOrderItem(String id) throws SQLException {
		
		String sql="delete from orderItem where order_id=?";
		
		QueryRunner runner = new QueryRunner();
		
		runner.update(DataSourceUtils.getConnection(),sql,id);
	}
}
