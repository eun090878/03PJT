package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;

public class AddPurchaseAction extends Action {
	
		@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		System.out.println(" ::. AddPurchaseAction Ω√¿€ :: ");
		int productNo = Integer.parseInt(request.getParameter("prodNo"));
		String buyerId = request.getParameter("buyerId");
		
		System.out.println("AddPurchaseAction:: prodNo"+productNo);
		System.out.println("AddPurchaseAction:: buyerId"+buyerId);
		
		User user = new User();
		Product product = new Product();
		Purchase purchase = new Purchase();
				
		ProductService pService = new ProductServiceImpl();
		product = pService.getProduct(productNo);
		
		UserService uService = new UserServiceImpl();
		user = uService.getUser(buyerId);
		
		String paymentOption = request.getParameter("paymentOption");
		String receiverName = request.getParameter("receiverName");
		String receiverPhone = request.getParameter("receiverPhone");
		String dlvyAddr = request.getParameter("receiverAddr");
		String dlvyRequest = request.getParameter("receiverRequest");
		String dlvyDate = request.getParameter("receiverDate");

		
		purchase.setPurchaseProd(product);
		purchase.setBuyer(user);
		purchase.setPaymentOption(paymentOption);
		purchase.setReceiverName(receiverName);
		purchase.setReceiverPhone(receiverPhone);
		purchase.setDlvyAddr(dlvyAddr);
		purchase.setDlvyRequest(dlvyRequest);
		purchase.setDlvyDate(dlvyDate);
//		purchase.setTranCode(tranCode);
				
		PurchaseService service = new PurchaseServiceImpl();
		service.addPurchase(purchase);
		
		System.out.println("getproduct ::::::::" + pService.getProduct(productNo));
		System.out.println("userId:::::" +uService.getUser(buyerId) );

	/*	PurchaseService purService = new PurchaseServiceImpl();
		purService.addPurchase(purchase);*/
		
		request.setAttribute("purchase", purchase);
		
		System.out.println(" ::. AddPurchaseAction ≥° :: ");
		
		return "forward:/purchase/addPurchase.jsp";	
		
	}
}
