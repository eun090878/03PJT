package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.domain.Product;

public class GetProductAction extends  Action {

	@Override
	public String execute(	HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		
		System.out.println(" ::. GetProductAction ���� :: ");
		
		System.out.println("currentPage�� �Ѿ���� :: " +request.getParameter("currentPage"));
		int productNo = Integer.parseInt(request.getParameter("prodNo"));
		
		ProductService service = new ProductServiceImpl();
		Product product = service.getProduct(productNo);
//		System.out.println("����� GETPRODUCTACTION :: " + vo.getRegDate());
		request.setAttribute("product", product);
		
		System.out.println(" ::. GetProductAction �� :: \n");
		
		System.out.println("menu Parameter : "+request.getParameter("menu"));
		if(request.getParameter("menu").equals("manage")){			
			return "forward:/product/updateProductView.jsp";
		} else {		//request.getParameter("menu").equals("search"))
			return "forward:/product/readProduct.jsp";
		}		
	}
}
