package com.model2.mvc.framework;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.common.util.HttpUtil;


public class ActionServlet extends HttpServlet {
	
	private RequestMapping mapper;

	@Override
	public void init() throws ServletException {
		super.init();
		String resources=getServletConfig().getInitParameter("resources");
		mapper=RequestMapping.getInstance(resources);
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) 
																									throws ServletException, IOException {
		System.out.println("[ ActionServlet 시작 ]");
		
		String url = request.getRequestURI();
		String contextPath = request.getContextPath();
		String requestPath = url.substring(contextPath.length());
		System.out.println("\nActionServlet.service() RequestURI :: " + requestPath);
		
		try{
			Action action = mapper.getAction(requestPath);
			action.setServletContext(getServletContext());
			
			String resultPage=action.execute(request, response);
			String result=resultPage.substring(resultPage.indexOf(":")+1);
//			System.out.println("forward하기전 result ::"+result);
			
			if(resultPage.startsWith("forward:")) {
				System.out.println("forward");
				HttpUtil.forward(request, response, result);
				
			} else {
				System.out.println("redirect");
				HttpUtil.redirect(response, result); 
				
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		System.out.println("[ ActionServlet 끝 ]\n\n");
	}
}