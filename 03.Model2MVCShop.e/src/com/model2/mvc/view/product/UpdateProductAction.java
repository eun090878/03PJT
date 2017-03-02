package com.model2.mvc.view.product;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.domain.Product;

public class UpdateProductAction extends Action {
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		System.out.println(" ::. UpdateProductAction 시작 :: ");
		
		Product productVO = new Product();
				
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));	
		/*int prodNO2 = productVO.getProdNo();*/
		
		productVO.setProdNo(prodNo);
		productVO.setProdName(request.getParameter("prodName"));
		productVO.setProdDetail(request.getParameter("prodDetail"));
		productVO.setManuDate(request.getParameter("manuDate"));
		productVO.setPrice(Integer.parseInt(request.getParameter("price")));
		productVO.setFileName(request.getParameter("fileName"));
		System.out.println("여기는 UPDATEPRODUCTACTION ::"+productVO.getRegDate());
		
/*		System.out.println("prodNo ::::"+prodNo);
		System.out.println("ProductName:::"+request.getParameter("prodName"));
		System.out.println("ProductDetail:::" + request.getParameter("prodDetail"));
		System.out.println("Manufacture Date ::::" + request.getParameter("manuDate"));
		System.out.println("price:::::::" +Integer.parseInt(request.getParameter("price")));
		System.out.println("fileName :::::" + request.getParameter("fileName"));*/
				
		ProductService service = new ProductServiceImpl();
		service.updateProduct(productVO);
		productVO = service.getProduct(prodNo);
		
		request.setAttribute("product", productVO);

		System.out.println(" ::. UpdateProductAction 끝 :: \n");
		
		/*return "redirect:/getProduct.do";*/
		return "forward:/product/readProduct.jsp";
		
	}
}
