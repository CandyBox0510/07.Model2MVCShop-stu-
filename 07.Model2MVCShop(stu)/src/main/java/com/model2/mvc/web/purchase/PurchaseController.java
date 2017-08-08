package com.model2.mvc.web.purchase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.CommonUtil;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.domain.Wish;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.purchase.PurchaseService;

@Controller
@RequestMapping("/purchase/*")
public class PurchaseController {
	
	@Autowired
	@Qualifier("purchaseServiceImpl")
	PurchaseService purchaseService;
	
	@Autowired
	@Qualifier("productServiceImpl")
	ProductService productService;
	
	@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;

	public PurchaseController() {
		System.out.println("PurchaseController() default Constructor");
	}
	
	@RequestMapping(value="addPurchase", method=RequestMethod.POST)
	public String addPurchase(@ModelAttribute("purchase")Purchase purchase,
								HttpSession session,
								Model model) throws Exception{

		purchase.setBuyer((User)session.getAttribute("user"));
		purchase.setDlvyDate(CommonUtil.toStrDateStr(purchase.getDlvyDate()));
		System.out.println("addPurchase 전 purchase"+purchase);
		purchaseService.addPurchase(purchase);
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("prodNo", purchase.getPurchaseProd().getProdNo());
		map.put("tranCode", "1");
		productService.updateProductTranCode(map);
		model.addAttribute("purchase", purchase);
		
		return "forward:/purchase/addPurchase.jsp";
	}
	
	@RequestMapping(value="addPurchase", method=RequestMethod.GET)
	public String addPurchaseView(@RequestParam("prodNo")String prodNo,
									@ModelAttribute("purchase")Purchase purchase,
									HttpSession session,
									Model model) throws Exception{
		
		purchase.setPurchaseProd(productService.getProduct(Integer.parseInt(prodNo)));
		System.out.println(purchase.getPurchaseProd());
		purchase.setBuyer((User)session.getAttribute("user"));
		System.out.println(purchase.getBuyer());
		
		model.addAttribute("purchase", purchase);
				
		return "forward:/purchase/addPurchaseView.jsp";
	}
	
	@RequestMapping(value="getPurchase")
	public String getPurchase(@RequestParam("tranNo")String tranNo,
								 Model model) throws NumberFormatException, Exception{
		Purchase purchase = purchaseService.getPurchase(Integer.parseInt(tranNo));
		purchase.setPaymentOption(purchase.getPaymentOption().trim());
		System.out.println("겟펄쳐스액션"+purchase);
		model.addAttribute("purchase", purchase);
		
		return "forward:/purchase/getPurchase.jsp";
	}
	
	@RequestMapping(value="updatePurchaseView", method=RequestMethod.GET)
	public String updatePurchaseView(@ModelAttribute("purchase")Purchase purchase,
										@RequestParam("tranNo")String tranNo,
										Model model) throws Exception{
		purchase = purchaseService.getPurchase(Integer.parseInt(tranNo));
		
		model.addAttribute("purchase", purchase);
		
		return "forward:/purchase/updatePurchaseView.jsp";
	}
	
	@RequestMapping(value="updatePurchase", method=RequestMethod.POST)
	public String updatePurchase(@ModelAttribute("purchase")Purchase purchase) throws Exception{
		
		purchase.setDlvyDate(CommonUtil.toDateStr(purchase.getDlvyDate()));
		
		purchaseService.updatePurchase(purchase);
		return "forward:/purchase/getPurchase";
		
	}
	
	@RequestMapping(value="listPurchase")
	public String listPurchase(@ModelAttribute("search")Search search,
								  HttpSession session,
								  Model model) throws Exception{
		String userId = null;
		
		if(search.getCurrentPage()==0){
			search.setCurrentPage(1);
		}
		
		System.out.println("현재 커런트 페이지"+search.getCurrentPage());

		search.setPageUnit(pageUnit);

		try{
		userId = ((User)(session.getAttribute("user"))).getUserId();
		}catch(NullPointerException e){
			throw new Exception("로그인 풀린 상태.");
		}
		
		Map<String, Object> map = purchaseService.getPurchaseList(search, userId);
		
		Page resultPage	= 
				new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(),
						pageUnit, pageSize);
		System.out.println("ListPurchaseAction ::"+resultPage);
		
		List<Purchase> list = (List<Purchase>)map.get("list");
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setTranCode(list.get(i).getTranCode().trim());
			list.get(i).setPurchaseProd(productService.getProduct(list.get(i).getPurchaseProd().getProdNo()));
		}
		
		
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("list", map.get("list"));
		model.addAttribute("search",search);
		
		return "forward:/purchase/listPurchase.jsp";
	}
	
	@RequestMapping(value="listSale")
	public String listSale(@ModelAttribute("search")Search search,
							 Model model) throws Exception{
		
		if(search.getCurrentPage()==0){
			search.setCurrentPage(1);
		}
		
		search.setPageUnit(pageUnit);
		
		Map<String, Object> map = purchaseService.getSaleList(search);
		
		Page resultPage	= 
				new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(),
						pageUnit, pageSize);
		
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("list", map.get("list"));
		model.addAttribute("search",search);
		
		
		return "forward:/purchase/listSale.jsp";
	}
	
	@RequestMapping(value="updateTranCode", method=RequestMethod.GET)
	public String updateTranCode(@RequestParam("tranNo")String tranNo,
									@RequestParam("tranCode")String tranCode) throws NumberFormatException, Exception{
		Map<String, Object>map = new HashMap<String, Object>();
		map.put("tranNo", Integer.parseInt(tranNo));
		map.put("tranCode", Integer.parseInt(tranCode));
		purchaseService.updateTranCode(map);
		map.put("prodNo", productService.getProductNo(Integer.parseInt(tranNo)));
		productService.updateProductTranCode(map);
		
		return "forward:/purchase/listPurchase.do";
	}
	
	@RequestMapping(value="updateTranCodeByProd",method=RequestMethod.GET)
	public String updateTranCodeByProd(@RequestParam("prodNo")String prodNo,
											@RequestParam("tranCode")String tranCode) throws Exception{
		Map<String, Object>map = new HashMap<String, Object>();
		map.put("prodNo", Integer.parseInt(prodNo));
		map.put("tranCode", Integer.parseInt(tranCode));
		purchaseService.updateTranCode(map);
		productService.updateProductTranCode(map);
				
		return "forward:/product/listProduct.do?menu=manage";
	}
	
	@RequestMapping(value="cancelPurchase", method=RequestMethod.GET)
	public String cancelPurchase(@RequestParam("tranNo")String tranNo) throws Exception{
		Map<String, Object>map = new HashMap<String, Object>();
		map.put("prodNo", productService.getProductNo(Integer.parseInt(tranNo)));
		map.put("tranCode", null);
		productService.updateProductTranCode(map);
		purchaseService.deletePurchase(tranNo);
		
		return "forward:/purchase/listPurchase.do";
	}
	
	@RequestMapping(value="addWishPurchase", method=RequestMethod.GET)
	public String addWishPurchase(@ModelAttribute("purchase")Purchase purchase,
									  @RequestParam("prodNo")String prodNo,
									  HttpSession session,
									  Model model) throws Exception{
		
		Product product = new Product();
		product.setProdNo(Integer.parseInt(prodNo));
		
		purchase.setPurchaseProd(product);
		purchase.setBuyer((User)(session.getAttribute("user"))); 
		
		purchaseService.addWishPurchase(purchase);
		
		model.addAttribute("wishSuccess", "success");
		
		return "forward:/product/listProduct.do?menu=search";
	}
	
	@RequestMapping(value="listWishPurchase")
	public String listWishPurchase(@ModelAttribute("search")Search search,
										HttpSession session,
										Model model) throws Exception{
		
		model.addAttribute("wishSuccess", "");
		
		
		if(search.getCurrentPage()==0){
			search.setCurrentPage(1);
		}
		
		search.setPageUnit(pageUnit);
		
		User user = (User)session.getAttribute("user");
		
		Map<String, Object> map = purchaseService.getWishList(search, user.getUserId());
		
		Page resultPage	= 
				new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(),
						pageUnit, pageSize);
		
		List<Wish> list = (List<Wish>)map.get("list");
		for (int i = 0; i < list.size(); i++) {
			if(list.get(i).getTranStatusCode() != null){
				list.get(i).setTranStatusCode(list.get(i).getTranStatusCode().trim());
			}
			list.get(i).setPurchaseProd(productService.getProduct(list.get(i).getPurchaseProd().getProdNo()));
		}
		
		
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("list", map.get("list"));
		model.addAttribute("search",search);
		
		return "forward:/purchase/listWishPurchase.jsp";
	}
	
	@RequestMapping(value="cancelWishPurchase", method=RequestMethod.GET)
	public String cancelWishPurchase(@RequestParam("wishNo")String wishNo) throws Exception{
		purchaseService.deleteWishPurchase(wishNo);
		
		return "forward:/purchase/listWishPurchase.do";
	}
	
}
