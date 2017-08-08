package com.model2.mvc.service.product.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Comment;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductDao;



@Repository("productDaoImpl")
public class ProductDaoImpl implements ProductDao {

	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSession;
	
//	public void setSqlSession(SqlSession sqlSession) {
//	this.sqlSession = sqlSession;
//}

	@Override
	public Product findProduct(int prodNo) throws Exception {		
		return sqlSession.selectOne("ProductMapper.getProduct",prodNo); 
	}

	@Override
	public int findProductNo(int tranNo) throws Exception {
		return sqlSession.selectOne("ProductMapper.getProductNo", tranNo);
	}

	@Override
	public List<Product> getProductList(Search search) throws Exception {
		return sqlSession.selectList("ProductMapper.getProductList",search);
	}

	@Override
	public void insertProduct(Product product) throws Exception {
		sqlSession.insert("ProductMapper.addProduct",product);
	}

	@Override
	public void updateProduct(Product product) throws Exception {
		sqlSession.update("ProductMapper.updateProduct",product);
	}
	
	@Override
	public void removeProduct(int prodNo) throws Exception {
		sqlSession.delete("ProductMapper.removeProduct",prodNo);
	}
	
	public void updateProductTranCode(Map<String,Object> map) throws Exception{
		sqlSession.update("ProductMapper.updateProductTranCode",map);
	}

	@Override
	public int getTotalCount(Search search) throws Exception {
		return sqlSession.selectOne("ProductMapper.getTotalCount",search);
	}

	@Override
	public void insertProductComment(Map<String,Object> map) throws Exception {
		sqlSession.insert("ProductMapper.addProductComment",map);
	}

	@Override
	public List<Comment> getProductComment(String prodNo) throws Exception {
		return sqlSession.selectList("ProductMapper.getProductComment",prodNo);
	}
	
	@Override
	public void deleteProductComment(String commentNo) throws Exception{
		sqlSession.delete("ProductMapper.deleteProductComment",commentNo);
	}
}
