package cn.itcast.estore.web.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.itcast.estore.domain.Product;
import cn.itcast.estore.service.ProductServiceImpl;

public class CartServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String method = request.getParameter("method");

		if ("add".equals(method)) {// 添加商品到购物车
			add(request, response);
		} else if ("remove".equals(method)) { // 从购物车删除商品
			remove(request, response);
		} else if ("update".equals(method)) {// 修改购物车商品
			update(request, response);
		}
	}

	// 添加商品到购物车
	public void add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 1.得到要添加到购物车中的商品的id
		String id = request.getParameter("id");

		// 2.根据id查找商品
		try {
			Product p = new ProductServiceImpl().findById(id);

			// 3.得到购物车
			HttpSession session = request.getSession();
			Map<Product, Integer> cart = (Map<Product, Integer>) session
					.getAttribute("cart");

			if (cart == null) {// 如果cart不存在，说明是第一次购物.
				cart = new HashMap<Product, Integer>();
			}
			// 判断购物车中是否有要添加商品。

			Integer count = cart.put(p, 1);
			if (count != null) {
				// 说明有吗
				cart.put(p, count + 1);
			}

			session.setAttribute("cart", cart);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		response.sendRedirect(request.getContextPath() + "/index.jsp");
	}

	// 从购物车中删除商品
	public void remove(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");

		Product p = new Product();
		p.setId(id);

		// 得到购物车
		Map<Product, Integer> cart = (Map<Product, Integer>) request
				.getSession().getAttribute("cart");

		cart.remove(p);

		if (cart.size() == 0) {
			request.getSession().removeAttribute("cart");
		}

		response.sendRedirect(request.getContextPath() + "/showCart.jsp");
	}

	// 修改购物车中商品数量
	public void update(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {// 得到要修改的商品的数量与商品id。
		String id = request.getParameter("id");
		int count = Integer.parseInt(request.getParameter("count"));

		// 这是要修改的商品
		Product p = new Product();
		p.setId(id);

		// 得到购物车
		Map<Product, Integer> cart = (Map<Product, Integer>) request
				.getSession().getAttribute("cart");

		// 修改商品的数量
		if (count == 0) {
			cart.remove(p); // 将商品从购物车中移除
		} else {
			cart.put(p, count);
		}
		request.getSession().setAttribute("cart", cart);

		response.sendRedirect(request.getContextPath() + "/showCart.jsp");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
