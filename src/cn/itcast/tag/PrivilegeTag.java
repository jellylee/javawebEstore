package cn.itcast.tag;

import cn.itcast.estore.domain.User;
import java.io.IOException;
import java.io.PrintStream;
import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.SkipPageException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class PrivilegeTag extends SimpleTagSupport {
	private User user;

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void doTag() throws JspException, IOException {
		System.out.println(this.user);

		if ((this.user != null) && (!("user".equals(this.user.getRole()))))
			return;
		getJspContext().getOut().write("<h1>权限不足</h1>");

		throw new SkipPageException();
	}
}
