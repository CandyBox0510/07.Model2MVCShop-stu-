package com.model2.mvc.web.product;

import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.CommonUtil;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.Wish;
import com.model2.mvc.service.product.ProductService;

@Controller
public class ProductController {

	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	
	@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;
	
	public ProductController() {
		System.out.println("productController() default Constructor ");
	}

	@RequestMapping("/addProduct.do")
	public ModelAndView addProduct(@ModelAttribute("product")Product product) throws Exception{
		
		product.setManuDate(CommonUtil.toStrDateStr(product.getManuDate()));
		productService.addProduct(product);
		
		
		ModelAndView modelAndView = new ModelAndView();
		
		modelAndView.setViewName("forward:/product/addProduct.jsp");
		
		modelAndView.addObject("product",product);
		
		return modelAndView;
	}
	
	@RequestMapping("/getProduct.do")
	public ModelAndView getProduct(@RequestParam("prodNo") String prodNo,
									  @RequestParam(value="menu",defaultValue="") String menu,
									  HttpServletRequest request, 
									  HttpServletResponse response,
									  @CookieValue(value="history",defaultValue="") String history)
									  throws Exception{
		
		Cookie cookie=null;
		if(history == ""){
			cookie = new Cookie("history",prodNo);
		}else{
			cookie = new Cookie("history",history+","+prodNo);
		}
		
		cookie.setMaxAge(60*5);
		response.addCookie(cookie);
					
		//ProductService service=new ProductServiceImpl();
		Product product= productService.getProduct(Integer.parseInt(prodNo));
		
		ModelAndView modelAndView = new ModelAndView();
		
		modelAndView.addObject("product",product);
		
		
		if(menu!=""){
			if(menu.equals("manage")){
				modelAndView.setViewName("forward:/updateProductView.do");
				return modelAndView;		
			}
		}
		modelAndView.setViewName("forward:/product/getProduct.jsp");
		return modelAndView;
	}
	
	@RequestMapping("/updateProductView.do")
	public ModelAndView updateProductView(@RequestParam("prodNo")String prodNo) throws NumberFormatException, Exception{
		
		Product product = productService.getProduct(Integer.parseInt(prodNo));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("product",product);
		modelAndView.setViewName("forward:/product/updateProductView.jsp");
		
		return modelAndView;
	}
	
	@RequestMapping("/updateProduct.do")
	public ModelAndView updateProduct(@ModelAttribute("product")Product product) throws Exception{
		
		productService.updateProduct(product);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("product",product);
		modelAndView.setViewName("forward:/getProduct.do?prodNo="+product.getProdNo()+"&comePath=manage");
		
		return modelAndView;
	}
	
	@RequestMapping("/listProduct.do")
	public ModelAndView listProduct(@ModelAttribute("search")Search search) throws Exception{

		if(search.getCurrentPage()==0){
			search.setCurrentPage(1);
		}
			System.out.println("이번에 눌린 페이지"+search.getCurrentPage());
		
		if(search.getSearchSortPrice()=="" ||search.getSearchSortPrice()==null){
			search.setSearchSortPrice("0");
		}
			System.out.println("이번에 가격 정렬기준"+search.getSearchSortPrice());

		System.out.println("리프액 searchCondition"+search.getSearchCondition());
		System.out.println("리프액 searchKeyword"+search.getSearchKeyword());
			
		search.setPageUnit(pageUnit);
		
		Map<String, Object> map = productService.getProductList(search);
		
		
		Page resultPage	= 
				new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(),
						pageUnit, pageSize);
		System.out.println("ListProductAction ::"+resultPage);
	
		List<Product> list = (List<Product>)map.get("list");
		for (int i = 0; i < list.size(); i++) {
			if(list.get(i).getTranStatusCode() != null){
				list.get(i).setTranStatusCode(list.get(i).getTranStatusCode().trim());
			}
		}
		
		System.out.println("ProductControll List 확인용 : "+map.get("list").toString());
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("list",map.get("list"));
		modelAndView.addObject("resultPage",resultPage);
		modelAndView.setViewName("forward:/product/listProduct.jsp");
		
		
		return modelAndView;
	}
}
