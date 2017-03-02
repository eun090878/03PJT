package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.domain.Product;

public class UpdateProductViewAction extends Action{
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		System.out.println(" ::. UpdateProductViewAction Ω√¿€ :: ");
		request.getAttribute("vo");		//???????????
		int productNo = Integer.parseInt(request.getParameter("prodNo"));
		
		ProductService service = new ProductServiceImpl();
		Product productVO = service.getProduct(productNo);
	
		request.setAttribute("product", productVO);
	
		System.out.println(" ::. UpdateProductViewAction ≥° ::\n ");
		
		return "forward:/product/updateProductView.jsp";
	
	}
}
