package com.wang.core.exception;

/**
 * 参数错误，方法调用的入参不符合要求
 *
 * @version 1.0
 * @author HeJiawang
 *
 */
public class ArgumentException extends BusinessException {

	private static final long serialVersionUID = 6832695224568830049L;

	public ArgumentException(String message) {
		super(message);
	}
}