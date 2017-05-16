package cn.itcast.estore.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.itcast.estore.domain.Order;
import cn.itcast.estore.domain.User;
import cn.itcast.estore.utils.DataSourceUtils;

public class OrderDao {

	// 创建订单
	public void createOrder(Order order) throws SQLException {
		String sql = "insert into orders values(?,?,?,0,null,?)";

		QueryRunner runner = new QueryRunner();//不需要使用带参数构造方法

		runner.update(DataSourceUtils.getConnection(), sql, order.getId(),
				order.getMoney(), order.getReceiverinfo(), order.getUser_id());
	}

	// 根据用户查询订单
	public List<Order> findOrder(User user) throws SQLException {

		String sql = null;
		List<Order> orders = null;
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		if ("admin".equals(user.getRole())) {
			sql = "select orders.*,username,nickname from orders,users where orders.user_id=users.id";

			orders = runner.query(sql, new BeanListHandler<Order>(Order.class));
		} else if("user".equals(user.getRole())) {
			sql = "select orders.*,username,nickname from orders,users where orders.user_id=users.id and user_id=?";
			orders = runner.query(sql, new BeanListHandler<Order>(Order.class),
					user.getId());
		}

		return orders;
	}
	//删除订单
	public void delOrder(String id) throws SQLException {

		String sql = "delete from orders where id=?";

		QueryRunner runner = new QueryRunner();

		runner.update(DataSourceUtils.getConnection(), sql, id);
	}
	//修改订单状态
	public void updateState(String id) throws SQLException {
	
		String sql="update orders set paystate=1 where id=?";
		
		QueryRunner runner = new QueryRunner();
		
		runner.update(sql,id);
		
	}

}
