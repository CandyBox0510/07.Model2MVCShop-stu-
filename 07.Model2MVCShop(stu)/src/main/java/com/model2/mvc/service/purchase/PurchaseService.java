package com.model2.mvc.service.purchase;

import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;



public interface PurchaseService {

	public void addPurchase(Purchase purchase) throws Exception;
	
	public Purchase getPurchase(int tranNo) throws Exception;
	
//	public Purchase getPurchase2(int prodNo) throws Exception;
	
	public Map<String,Object> getPurchaseList(Search search,String buyerId) throws Exception;
	
	public Map<String,Object> getSaleList(Search search) throws Exception;
	
	public Map<String,Object> getWishList(Search search, String userId) throws Exception;
	
	public void updatePurchase(Purchase purchase) throws Exception;
	
	public void updateTranCode(Map<String,Object> map) throws Exception;
	
	public void deletePurchase(String tranNo) throws Exception;
	
	public void addWishPurchase(Purchase purchase) throws Exception;
	
	public void deleteWishPurchase(String wishNo) throws Exception;
}