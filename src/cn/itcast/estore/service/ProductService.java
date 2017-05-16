package cn.itcast.estore.service;

import java.sql.SQLException;
import java.util.List;

import cn.itcast.estore.annotation.PrivilegeInfo;
import cn.itcast.estore.domain.Product;
import cn.itcast.estore.domain.User;
import cn.itcast.estore.exception.PrivilegeException;

public interface ProductService {

	@PrivilegeInfo("添加商品")
	public void add(User user ,Product product) throws PrivilegeException, SQLException;
	
	//查找所有商品
	public List<Product> findAll() throws SQLException;
	
	//根据id查找商品
	public Product findById(String id) throws SQLException;
	
	//查询销售榜单
	public List<Product> findSell(User user) throws PrivilegeException, SQLException;
	
}
