package test;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import cn.itcast.estore.utils.MailUtils;

public class TestMailUtil {

	public static void main(String[] args) throws AddressException, MessagingException {
			
		MailUtils.sendMail("1605026055@qq.com", "test");
	}

}
