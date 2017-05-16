package cn.itcast.estore.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.estore.domain.Product;
import cn.itcast.estore.domain.User;
import cn.itcast.estore.exception.PrivilegeException;
import cn.itcast.estore.factory.ProductServiceFactory;
import cn.itcast.estore.service.ProductService;
import cn.itcast.estore.service.ProductServiceImpl;
import cn.itcast.estore.utils.DownloadUtils;

public class DownloadServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 1.得到下载文件名称
		String filename = new Date().toLocaleString() + "销售榜单.csv";
		// 2.设置下载文件的mimeType类型
		response.setContentType(this.getServletContext().getMimeType(filename));

		// 3.设置Content-Dispostion
		String agent = request.getHeader("User-agent");
		response.setHeader("Content-disposition", "attachement;filename="
				+ DownloadUtils.getDownloadFileName(filename, agent));

		// 4.得到销售榜单信息

		List<Product> ps = null;
		ProductService service = ProductServiceFactory.getInstance();
		try {
			User user = (User) request.getSession().getAttribute("user");
			ps = service.findSell(user);

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (PrivilegeException e) {
			response.sendRedirect(request.getContextPath() + "/error/error.jsp");
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}

		response.setCharacterEncoding("gbk");
		response.getWriter().println("商品名称,销售数量");

		for (Product p : ps) {
			response.getWriter().println(
					p.getName() + "," + p.getTotalSaleNum());
			response.getWriter().flush();
		}

		response.getWriter().close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
