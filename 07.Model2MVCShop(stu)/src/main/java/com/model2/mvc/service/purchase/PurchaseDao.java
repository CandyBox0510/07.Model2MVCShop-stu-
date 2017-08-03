package com.model2.mvc.service.purchase;

import java.util.List;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;

public interface PurchaseDao {


	public Purchase findPurchase(int tranNo) throws Exception;

	public List<Purchase> getPurchaseList(Search search,String buyerId) throws Exception;

	public List<Purchase> getWishList(Search search,String userId) throws Exception;

	public List<Purchase> getSaleList(Search search);

	public void insertPurchase(Purchase purchase) throws Exception;

	public void updatePurchase(Purchase purchase) throws Exception;

	public void updateTranCode(Map<String,Object> map) throws Exception;

	public void deletePurchase(String tranNo) throws Exception;

	public void deleteWishPurchase(String wishNo) throws Exception;

	public void insertWishPurchase(Purchase purchase) throws Exception;
	
	int getTotalCount(Map<String,Object> map) throws Exception;

}
