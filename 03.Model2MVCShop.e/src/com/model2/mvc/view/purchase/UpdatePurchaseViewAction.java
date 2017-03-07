package com.model2.mvc.view.purchase;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class UpdatePurchaseViewAction extends Action {
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		request.getAttribute("vo");		//???????????
		System.out.println(" ::. UpdatePurchaseViewAction 시작 :: ");
		
		Purchase purchase = new Purchase();
		
		int tranNo=Integer.parseInt(request.getParameter("tranNo"));
		System.out.println("tranNo 받아와라" + tranNo);
	
		Product prodNo = (Product)request.getAttribute("prodNo");
		User userId = (User)request.getAttribute("userId");
		System.out.println("UpdatePurchaseViewAction :: prodNo : " + prodNo);
		System.out.println("UpdatePurchaseViewAction :: userId : " + userId);
		
		purchase.setTranNo(tranNo);
		purchase.setPurchaseProd(prodNo);		
		purchase.setBuyer(userId);
		purchase.setPaymentOption(request.getParameter("paymentOption"));
		purchase.setReceiverName(request.getParameter("receiverName"));
		purchase.setReceiverPhone(request.getParameter("receiverPhone"));
		purchase.setDlvyAddr(request.getParameter("dlvyAddr"));
		purchase.setDlvyRequest(request.getParameter("dlvyRequest"));
		purchase.setDlvyDate(request.getParameter("dlbyDate"));
		purchase.setOrderDate(Date.valueOf(request.getParameter("orderDate")));
				
		PurchaseService service = new PurchaseServiceImpl();
		service.updatePurcahse(purchase);
		
		request.setAttribute("purchase", purchase);		
		
		System.out.println(" ::. UpdatePurchaseViewAction 끝 :: ");
		
		return "forward:/purchase/updatePurchaseView.jsp";
	}
	

}
