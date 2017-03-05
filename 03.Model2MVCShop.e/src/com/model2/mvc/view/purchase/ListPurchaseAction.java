package com.model2.mvc.view.purchase;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class ListPurchaseAction extends Action{

	@Override
	public String execute(	HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		System.out.println(" ::. ListPurchaseAction ���� :: ");
		
		HttpSession session = request.getSession();
		String buyerId = ((User)session.getAttribute("user")).getUserId();		
		System.out.println("ListPurchaseAction : "+buyerId);
		
		Search searchVO=new Search();
				
		int currentPage=1;
		if(request.getParameter("currentPage") != null)
			currentPage=Integer.parseInt(request.getParameter("currentPage"));
		
		searchVO.setCurrentPage(currentPage);
		
		int pageSize=Integer.parseInt(getServletContext().getInitParameter("pageSize"));
		int pageUnit  =  Integer.parseInt(getServletContext().getInitParameter("pageUnit"));
		searchVO.setPageSize(pageSize);

		PurchaseService service=new PurchaseServiceImpl();
		Map<String, Object> map = service.getPurchaseList(searchVO, buyerId);
		
		Page resultPage	= 
				new Page( currentPage, ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		
		request.setAttribute("list", map.get("list"));
		request.setAttribute("resultPage", resultPage);
		request.setAttribute("searchVO", searchVO);
		
		System.out.println(" ::. ListPurchaseAction �� :: ");
		
		return "forward:/purchase/listPurchase.jsp";
	}
}
