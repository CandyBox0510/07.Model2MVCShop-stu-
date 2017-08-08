package com.model2.mvc.service.product.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Comment;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductDao;
import com.model2.mvc.service.product.ProductService;


@Service("productServiceImpl")
public class ProductServiceImpl implements ProductService {

	@Autowired
	@Qualifier("productDaoImpl")
	ProductDao productDao;

	public ProductServiceImpl() {
	
	}
	

/*	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}*/


	@Override
	public void addProduct(Product product) throws Exception {
		productDao.insertProduct(product);
	}

	@Override
	public Product getProduct(int prodNo) throws Exception {
		return productDao.findProduct(prodNo);
	}
	

	@Override
	public int getProductNo(int tranNo) throws Exception {
		return productDao.findProductNo(tranNo);
	}

	@Override
	public Map<String,Object> getProductList(Search search) throws Exception {
		List<Product> productList = productDao.getProductList(search);
		int totalCount = productDao.getTotalCount(search);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("list", productList);
		map.put("totalCount", totalCount);
		 
		 return map;
	}

	@Override
	public void updateProduct(Product product) throws Exception {
		productDao.updateProduct(product);
	}

	
	@Override
	public void removeProduct(int prodNo) throws Exception {
		productDao.removeProduct(prodNo);
	}

	@Override
	public void updateProductTranCode(Map<String,Object> map) throws Exception{
		productDao.updateProductTranCode(map);
	}
	
	@Override
	public void addProductComment(Map<String,Object> map) throws Exception {
		productDao.insertProductComment(map);
	}

	@Override
	public List<Comment> getProductComment(String prodNo) throws Exception {
		return productDao.getProductComment(prodNo);
	}
	
	@Override
	public void deleteProductComment(String commentNo) throws Exception{
		productDao.deleteProductComment(commentNo);
	}
	
}
