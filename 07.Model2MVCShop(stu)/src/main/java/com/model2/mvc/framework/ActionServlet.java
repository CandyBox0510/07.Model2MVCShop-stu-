package com.model2.mvc.framework;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.model2.mvc.common.util.HttpUtil;



public class ActionServlet extends HttpServlet {
	
	private RequestMapping mapper;

	@Override
	public void init() throws ServletException {
		super.init();
		String resources=getServletConfig().getInitParameter("resources");
															//프로퍼타이즈의 위치
		mapper=RequestMapping.getInstance(resources);
										//propertise의 위치를 기준으로 한
										//mapper가 RequestMapping 인스턴스 참조
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) 
												throws ServletException, IOException {
		
		String url = request.getRequestURI();
		System.out.println("《URI》:"+url);
		String contextPath = request.getContextPath();
		System.out.println("《contextPath》:"+contextPath);
		String path = url.substring(contextPath.length());
		System.out.println(path);
		
		try{
			Action action = mapper.getAction(path);
			action.setServletContext(getServletContext());
			
			String resultPage=action.execute(request, response);
			String result=resultPage.substring(resultPage.indexOf(":")+1);
			
			if(resultPage.startsWith("forward:")){
				HttpUtil.forward(request, response, result);
				System.out.println("ActionServlet of forward");
			}else{
				HttpUtil.redirect(response, result);
				System.out.println("ActionServlet of redirect");
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}