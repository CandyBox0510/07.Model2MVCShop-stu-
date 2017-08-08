package com.model2.mvc.service.product;


import java.util.List;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Comment;
import com.model2.mvc.service.domain.Product;


public interface ProductDao {

	public Product findProduct(int prodNo) throws Exception;
	
	public int findProductNo(int tranNo) throws Exception;
	
	public List<Product> getProductList(Search search) throws Exception;
	
	public void insertProduct(Product product) throws Exception;
	
	public void updateProduct(Product product) throws Exception;
	
	public void removeProduct(int prodNo) throws Exception;
	
	public void updateProductTranCode(Map<String,Object> map) throws Exception;

	int getTotalCount(Search search) throws Exception;
	
	public void insertProductComment(Map<String,Object> map) throws Exception;
	
	public List<Comment> getProductComment(String prodNo) throws Exception;
	
	public void deleteProductComment(String commentNo) throws Exception;
}
