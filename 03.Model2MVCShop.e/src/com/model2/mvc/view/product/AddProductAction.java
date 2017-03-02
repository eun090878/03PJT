package com.model2.mvc.view.product;


import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.domain.Product;

public class AddProductAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println(" ::. AddProductAction 시작 :: ");
		Product productVO=new Product();
		
		productVO.setProdName(request.getParameter("prodName"));
		productVO.setProdDetail(request.getParameter("prodDetail"));	
		productVO.setManuDate(request.getParameter("manuDate"));
		productVO.setPrice(Integer.parseInt(request.getParameter("price")));
		productVO.setFileName(request.getParameter("fileName"));
	 
		System.out.println("AddProductAction : ["+productVO +"]");
		
		ProductService pService = new ProductServiceImpl();
		pService.addProduct(productVO);
		
		request.setAttribute("product", productVO);

		System.out.println(" ::. GetProductAction 끝 :: \n");
		//등록한 상품정보 보여줌
		return "forward:/product/getProduct.jsp";
		
	}
}
