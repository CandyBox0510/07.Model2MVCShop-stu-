package com.model2.mvc.service.purchase.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseDao;

@Repository("purchaseDaoImpl")
public class PurchaseDaoImpl implements PurchaseDao{

	@Autowired
	@Qualifier("sqlSessionTemplate")
	SqlSession sqlSession;
	
	public PurchaseDaoImpl() {

	}

	public Purchase findPurchase(int tranNo) throws Exception{
		return sqlSession.selectOne("PurchaseMapper.getPurchase",tranNo);
	}
	
	public List<Purchase> getPurchaseList(Search search,String buyerId) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("search", search);
		map.put("buyerId", buyerId);
		return sqlSession.selectList("PurchaseMapper.getPurchaseList",map);
	}
	
	public List<Purchase> getWishList(Search search,String userId) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("search", search);
		map.put("userId", userId);
		return sqlSession.selectList("PurchaseMapper.getWishList",map);
	}
	
	public List<Purchase> getSaleList(Search search){
		return sqlSession.selectList("PurchaseMapper.getSaleList", search);
	}
	
	public void insertPurchase(Purchase purchase) throws Exception{
		sqlSession.insert("PurchaseMapper.addPurchase",purchase);
	}
	
	public void updatePurchase(Purchase purchase) throws Exception{
		sqlSession.update("PurchaseMapper.updatePurchase",purchase);
	}
	
	public void updateTranCode(Map<String,Object> map) throws Exception{
		sqlSession.update("PurchaseMapper.updateTranCode",map);
	}
	
	public void deletePurchase(String tranNo) throws Exception{
		sqlSession.delete("PurchaseMapper.removePurchase",tranNo);
	}
	
	public void deleteWishPurchase(String wishNo) throws Exception{
		sqlSession.delete("PurchaseMapper.removeWishPurchase",wishNo);
	}
	
	public void insertWishPurchase(Purchase purchase) throws Exception{
		sqlSession.insert("PurchaseMapper.addWish",purchase);
	}
	
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	public int getTotalCount(Map<String,Object> map) throws Exception {
		return sqlSession.selectOne("PurchaseMapper.getTotalCount",map);
	}
}
