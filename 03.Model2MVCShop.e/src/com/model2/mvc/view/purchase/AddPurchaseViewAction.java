package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;

public class AddPurchaseViewAction extends Action{
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		System.out.println(" ::. AddPurchaseViewAction 시작 :: ");
	
		int productNo = Integer.parseInt(request.getParameter("prodNo"));
		System.out.println("productNo받아 와랏:::::"+productNo);
		
		ProductService service = new ProductServiceImpl();
		Product productVO = service.getProduct(productNo);
		System.out.println("product 상품정보 :: " + productVO);
		
		request.setAttribute("product", productVO);		
		
		System.out.println(" ::. AddPurchaseViewAction 끝 :: ");
		
		return "forward:/purchase/addPurchaseView.jsp";
	}

}
