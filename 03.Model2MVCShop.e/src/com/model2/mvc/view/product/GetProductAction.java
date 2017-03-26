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
		
		System.out.println(" ::. GetProductAction 시작 :: ");
		
		System.out.println("currentPage가 넘어오냐 :: " +request.getParameter("currentPage"));
		int productNo = Integer.parseInt(request.getParameter("prodNo"));
		
		ProductService service = new ProductServiceImpl();
		Product product = service.getProduct(productNo);
//		System.out.println("여기는 GETPRODUCTACTION :: " + vo.getRegDate());
		request.setAttribute("product", product);
		
		System.out.println(" ::. GetProductAction 끝 :: \n");
		
		System.out.println("menu Parameter : "+request.getParameter("menu"));
		if(request.getParameter("menu").equals("manage")){			
			return "forward:/product/updateProductView.jsp";
		} else {		//request.getParameter("menu").equals("search"))
			return "forward:/product/readProduct.jsp";
		}		
	}
}
