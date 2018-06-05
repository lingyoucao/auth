package com.demo.token.model;

import java.io.Serializable;
import java.util.Date;


public class User implements Serializable{

	/**
	 * 用户ID
	 */
	private Long userId;
	/**
	 * 明文密码存放字段
	 */
	private String password;
	/**
	 * 密文密码，即授权码
	 */
	private String authCode;
	/**
	 * 创建时间
	 */
	private Date createDate;
	/**
	 * 修改时间
	 */
	private Date modifyDate;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	@Override
	public String toString() {
		return "User{" +
				"userId=" + userId +
				", password='" + password + '\'' +
				", authCode='" + authCode + '\'' +
				", createDate=" + createDate +
				", modifyDate=" + modifyDate +
				'}';
	}
}
