package cn.itcast.estore.exception;

import java.lang.reflect.InvocationTargetException;

public class PrivilegeException extends InvocationTargetException {

	public PrivilegeException() {
		super();
	}

	public PrivilegeException(Throwable target, String s) {
		super(target, s);
	}

	public PrivilegeException(Throwable target) {
		super(target);
	}

}
