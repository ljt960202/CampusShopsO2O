package com.ljt.o2o.exception;

//为何继承RuntimeException 因为只有继承它的异常才能回退 
public class ShopOperationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5245860319241499901L;
	
	public ShopOperationException(String msg) {
		super(msg);
	}
}
