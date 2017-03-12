package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class UpdateTranCodeByProdAction extends  Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println("UpdateTranCodeByProdAction Ω√¿€ !");
		Purchase purchase = new Purchase();
		int tranNo = Integer.parseInt(request.getParameter("tranNo"));		
				
		PurchaseService service = new PurchaseServiceImpl();
		purchase = service.getPurchase(tranNo);
		service.updateTranCode(purchase);
		
		
		
		System.out.println("UpdateTranCodeByProdAction ≥° !");
		
		return null;
	}
	

}
