package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class UpdateTranCodeAction extends Action {
<<<<<<< HEAD
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//HttpSession session = request.getSession();
		Purchase purchase = new Purchase();
		int tranNo = Integer.parseInt(request.getParameter("tranNo"));
		
		
		//boolean loginGrade = ((User)session.getAttribute("user")).getRole().equals("admin");
		//if(loginGrade){
	//		purchase.setTranCode("1");
		//}
//		purchase.setTranNo(tranNo);
		
		PurchaseService Service = new PurchaseServiceImpl();
		Service.updateTranCode(purchase);
		
		return "redirect:/listPurchase.do";
	}
=======

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		int tranNo = Integer.parseInt(request.getParameter("tranNo"));
		
		PurchaseService service = new PurchaseServiceImpl();
		Purchase purchase = service.getPurchase(tranNo);
		service.updateTranCode(purchase);
		
		return "forward:/purchase/listPurchase.jsp";
	}
	
	

>>>>>>> refs/heads/new/test
}
