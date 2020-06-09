package com.ljt.o2o.exception;

//为何继承RuntimeException 因为只有继承它的异常才能回退 
public class LocalAuthOperationException extends RuntimeException {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2355592554608742721L;

	public LocalAuthOperationException(String msg) {
		super(msg);
	}
}
