package com.wang.core.util.qiniu;

/**
 * 七牛操作返回结果类
 * @author HeJiawang
 * @date   2016.09.20
 */
public class QiNiuOptionResult {

	//200 操作成功，其他均失败
	private String status;
	
	//当发生错误时，错误的描述信息
	private String errorDesc;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getErrorDesc() {
		return errorDesc;
	}

	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}
	
}
