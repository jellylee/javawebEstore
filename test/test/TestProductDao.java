package test;

import java.sql.SQLException;

import cn.itcast.estore.dao.ProductDao;
import cn.itcast.estore.domain.Product;
import cn.itcast.estore.service.ProductServiceImpl;

public class TestProductDao {
	
	public static void main(String[] args) throws SQLException {
		
		/*Product p = new Product();
		p.setCategory("java");
		p.setDescription("this is a good book");
		p.setId("jisdfjo");
		p.setImgurl("wwww.baidu.com");
		p.setName("java by lgd");
		p.setPnum(44);
		p.setPrice(89);
		
		ProductDao dao = new ProductDao();
		dao.addProduct(p);*/
		
		ProductServiceImpl pi = new ProductServiceImpl();
		System.out.println(pi.findAll());
	}
}
