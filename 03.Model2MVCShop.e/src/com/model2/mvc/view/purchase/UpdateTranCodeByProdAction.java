package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.common.Search;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class UpdateTranCodeByProdAction extends  Action{

	  @Override
	   public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

<<<<<<< HEAD
	      System.out.println("UpdateTranCodeByProdAction 시작 !");
	      Purchase purchase = new Purchase();
	      int prodNo = Integer.parseInt(request.getParameter("prodNo"));      
	            
	      PurchaseService service = new PurchaseServiceImpl();
	      purchase = service.getPurchase2(prodNo);
	      service.updateTranCode(purchase);
	      
	      System.out.println("UpdateTranCodeByProdAction 끝 !");
	      
	      return "forward:/listProduct.do?menu=manager";
	   }
=======
		System.out.println("UpdateTranCodeByProdAction 시작 !");
		System.out.println("시작시작 :: " + request.getParameter("tranCode"));
		Search search = new Search();
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));		
				
		PurchaseService service = new PurchaseServiceImpl();
		Purchase purchase = service.getPurchase2(prodNo);
		service.updateTranCode(purchase);
		
		System.out.println("UpdateTranCodeByProdAction 끝 !");
		
		return  "forward:/listProduct.do?menu=manage";
	}
>>>>>>> refs/heads/new/test
	

}
