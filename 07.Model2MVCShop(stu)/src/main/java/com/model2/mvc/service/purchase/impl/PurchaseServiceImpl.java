package com.model2.mvc.service.purchase.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.purchase.PurchaseDao;
import com.model2.mvc.service.purchase.PurchaseService;


@Service("purchaseServiceImpl")
public class PurchaseServiceImpl implements PurchaseService {

	@Autowired
	@Qualifier("purchaseDaoImpl")
	PurchaseDao purchaseDao;
	
	public PurchaseServiceImpl() {
		
	}

	@Override
	public void addPurchase(Purchase purchase) throws Exception {
		purchaseDao.insertPurchase(purchase);
	}

	@Override
	public Purchase getPurchase(int tranNo) throws Exception {
		return purchaseDao.findPurchase(tranNo);
	}

//	@Override
//	public Purchase getPurchase2(int prodNo) throws Exception {
//		Purchase purchase = new Purchase();
//		purchase.setPurchaseProd((new ProductServiceImpl().getProduct(prodNo)));
//		return purchase;
//	}

	@Override
	public Map<String, Object> getPurchaseList(Search search, String userId) throws Exception {
		List<Purchase>list = purchaseDao.getPurchaseList(search, userId);
		Map<String,Object> totalMap = new HashMap<String, Object>();
		totalMap.put("userId", userId);
		totalMap.put("where", "purchaseList");
		int totalCount = purchaseDao.getTotalCount(totalMap);
		
		Map<String,Object> map = new HashMap<String, Object>();
		
		map.put("list", list);
		map.put("totalCount", totalCount);
		
		return map;
	}

	@Override
	public Map<String, Object> getSaleList(Search search) throws Exception {
		List<Purchase>list = purchaseDao.getSaleList(search);
		Map<String,Object> totalMap = new HashMap<String, Object>();
		totalMap.put("where", "saleList");
		totalMap.put("tranCode", 3);
		int totalCount = purchaseDao.getTotalCount(totalMap);
		
		Map<String,Object> map = new HashMap<String, Object>();
		
		map.put("list", list);
		map.put("totalCount", totalCount);
		
		return map;
	}
	

	@Override
	public Map<String, Object> getWishList(Search search, String userId) throws Exception {
		List<Purchase>list = purchaseDao.getWishList(search, userId);
		
		Map<String,Object> totalMap = new HashMap<String, Object>();
		totalMap.put("userId", userId);
		totalMap.put("where", "wishList");
		int totalCount = purchaseDao.getTotalCount(totalMap);
		
		Map<String,Object> map = new HashMap<String, Object>();
		
		map.put("list", list);
		map.put("totalCount", totalCount);
		
		return map;
	}

	@Override
	public void updatePurchase(Purchase purchase) throws Exception {
		purchaseDao.updatePurchase(purchase);
	}

	@Override
	public void updateTranCode(Map<String,Object> map) throws Exception {
		purchaseDao.updateTranCode(map);
	}

	@Override
	public void deletePurchase(String tranNo) throws Exception {
		purchaseDao.deletePurchase(tranNo);
	}

	@Override
	public void addWishPurchase(Purchase purchase) throws Exception {
		purchaseDao.insertWishPurchase(purchase);
	}

	@Override
	public void deleteWishPurchase(String wishNo) throws Exception{
		purchaseDao.deleteWishPurchase(wishNo);
	}
}
