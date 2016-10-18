package com.sysongy.util;

public class RealNameException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * 自定义错误信息和异常抛出
	 * @param message
	 * @param cause
	 */
	public RealNameException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * 只有异常抛出
	 * @param cause
	 */
	public RealNameException(Throwable cause) {
		super(cause);
	}

	/**
	 * 带自定义错误信息的输出
	 * @param message
	 */
	public RealNameException(String message) {
		super(message);
	}
}
