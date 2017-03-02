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
	
		System.out.println(" ::. AddPurchaseViewAction ���� :: ");
	
		int productNo = Integer.parseInt(request.getParameter("prodNo"));
		System.out.println("productNo�޾� �Ͷ�:::::"+productNo);
		
		ProductService service = new ProductServiceImpl();
		Product productVO = service.getProduct(productNo);
		System.out.println("product ��ǰ���� :: " + productVO);
		
		request.setAttribute("product", productVO);		
		
		System.out.println(" ::. AddPurchaseViewAction �� :: ");
		
		return "forward:/purchase/addPurchaseView.jsp";
	}

}
