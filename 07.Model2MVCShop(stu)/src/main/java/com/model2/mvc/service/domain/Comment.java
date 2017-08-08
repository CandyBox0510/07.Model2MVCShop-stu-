package com.model2.mvc.service.domain;

public class Comment {

	private String productCommentNo;
	private String userId;
	private String comments;
	private String prodNo;
	private String regDate;
	
	public Comment() {
		// TODO Auto-generated constructor stub
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	

	public String getProductCommentNo() {
		return productCommentNo;
	}

	public void setProductCommentNo(String productCommentNo) {
		this.productCommentNo = productCommentNo;
	}
	

	public String getProdNo() {
		return prodNo;
	}

	public void setProdNo(String prodNo) {
		this.prodNo = prodNo;
	}

	@Override
	public String toString() {
		return "Comment [productCommentNo=" + productCommentNo + ", userId=" + userId + ", comments=" + comments
				+ ", prodNo=" + prodNo + ", regDate=" + regDate + "]";
	}
	
}
