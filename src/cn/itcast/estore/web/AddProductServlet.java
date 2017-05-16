package cn.itcast.estore.web;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import cn.itcast.estore.domain.Product;
import cn.itcast.estore.domain.User;
import cn.itcast.estore.exception.PrivilegeException;
import cn.itcast.estore.factory.ProductServiceFactory;
import cn.itcast.estore.service.ProductService;
import cn.itcast.estore.service.ProductServiceImpl;
import cn.itcast.estore.utils.PicUtils;
import cn.itcast.estore.utils.UploadUtils;

public class AddProductServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 1.封装数据到javaBean--先将数据封装到map集合中.
		Map<String, String[]> map = new HashMap<String, String[]>();

		// 2.创建DiskFileItemFactory
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(1024 * 1024 * 5); // 设置缓存区大小为5m
		factory.setRepository(new File(this.getServletContext().getRealPath(
				"/temp")));// 设置临时文件存储位置

		// 3.创建ServletFileUpload
		ServletFileUpload upload = new ServletFileUpload(factory);

		// 判断是否是上传操作
		if (upload.isMultipartContent(request)) {
			upload.setHeaderEncoding("utf-8");// 解决上传文件中文乱问题

			// 4.得到所有的FileItem
			try {
				List<FileItem> items = upload.parseRequest(request);

				// 5.遍历items
				for (FileItem item : items) {
					// 判断是否是上传项
					if (item.isFormField()) {
						String fieldName = item.getFieldName();
						String value = item.getString("utf-8");

						map.put(fieldName, new String[] { value }); // 封装非上传项组件信息到map
					} else {
						// 是上传组件
						String fileName = item.getName(); // 得到上传文件名称 注意：可以包含路径.
						// c:/a/a.txt a.txt
						// 得到真实名称
						fileName = UploadUtils.subFileName(fileName); // a.txt

						// 得到随机名称
						String uuidFileName = UploadUtils
								.generateRandonFileName(fileName);

						// 得到随机目录
						String randomDir = UploadUtils
								.generateRandomDir(uuidFileName);

						String path = this.getServletContext().getRealPath(
								"/upload" + randomDir);

						File pathFile = new File(path);

						if (!pathFile.exists()) { // 目录不存在，创建
							pathFile.mkdirs();
						}

						// 得到一个imgurl
						String imgurl = "/upload" + randomDir + "/"
								+ uuidFileName;

						map.put("imgurl", new String[] { imgurl }); // 封装上传图片的路径.

						try {
							item.write(new File(pathFile, uuidFileName)); // 文件上传操作
						} catch (Exception e) {
							e.printStackTrace();
						}

					}
				}

			} catch (FileUploadException e) {
				e.printStackTrace();
			}

			// 封装商品的id
			map.put("id", new String[] { UUID.randomUUID().toString() });

			Product product = new Product();

			try {
				BeanUtils.populate(product, map); // 封装数据到Product对象.
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}

			// 做一个功能：得到商品缩略图
			PicUtils putils = new PicUtils(this.getServletContext()
					.getRealPath(product.getImgurl()));
			putils.resize(200, 200);

			// 调用ProductService中的添加商品方法.

			ProductService service = ProductServiceFactory.getInstance();

			try {
				User user = (User) request.getSession().getAttribute("user");
				service.add(user, product);
				response.sendRedirect(request.getContextPath() + "/index.jsp");
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (PrivilegeException e) {
				e.printStackTrace();
				response.sendRedirect(request.getContextPath()
						+ "/error/error.jsp");
				return;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
