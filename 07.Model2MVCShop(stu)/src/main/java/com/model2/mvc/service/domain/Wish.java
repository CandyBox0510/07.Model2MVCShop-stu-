package com.model2.mvc.service.domain;

import java.sql.Date;

public class Wish {

	private int wishNo;
	private int prodNo;
	private String userId;
	private String tranStatusCode;
	private Date regDate;
	private Product purchaseProd;
	
	public Wish() {
		// TODO Auto-generated constructor stub
	}

	public int getWishNo() {
		return wishNo;
	}

	public void setWishNo(int wishNo) {
		this.wishNo = wishNo;
	}

	public int getProdNo() {
		return prodNo;
	}

	public void setProdNo(int prodNo) {
		this.prodNo = prodNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTranStatusCode() {
		return tranStatusCode;
	}

	public void setTranStatusCode(String tranStatusCode) {
		this.tranStatusCode = tranStatusCode;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}


	public Product getPurchaseProd() {
		return purchaseProd;
	}

	public void setPurchaseProd(Product purchaseProd) {
		this.purchaseProd = purchaseProd;
	}

	@Override
	public String toString() {
		return "Wish [wishNo=" + wishNo + ", prodNo=" + prodNo + ", userId=" + userId + ", tranStatusCode="
				+ tranStatusCode + ", regDate=" + regDate + "]";
	}

}
