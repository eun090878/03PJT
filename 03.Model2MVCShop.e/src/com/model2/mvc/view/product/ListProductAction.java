package com.model2.mvc.view.product;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;

public class ListProductAction extends Action {

	@Override
	public String execute(	HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		System.out.println(" ::. ListProductAction ���� :: ");
		
//		System.out.println("MENU�ҷ��Ͷ� :: " +request.getParameter("menu"));
		System.out.println("searchkeyword" + request.getParameter("searchKeyword"));
		Search searchVO=new Search();
		
	
		
		int currentPage=1;
		if(request.getParameter("currentPage") != null)
			currentPage=Integer.parseInt(request.getParameter("currentPage"));
		
		searchVO.setCurrentPage(currentPage);
		searchVO.setSearchCondition(request.getParameter("searchCondition"));
		searchVO.setSearchKeyword(request.getParameter("searchKeyword"));
		
		// web.xml  meta-data �� ���� ��� ���� 
		int pageSize=Integer.parseInt(getServletContext().getInitParameter("pageSize"));
		int pageUnit  =  Integer.parseInt(getServletContext().getInitParameter("pageUnit"));
		searchVO.setPageSize(pageSize);
		

		// Business logic ����
		ProductService service=new ProductServiceImpl();
		Map<String,Object> map=service.getProductList(searchVO);
		
		Page resultPage	= 
				new Page( currentPage, ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println("ListProductAction ::"+resultPage);
		
		System.out.println("currenP " + currentPage);
		System.out.println("totalC " + ((Integer)map.get("totalCount")).intValue());
		System.out.println();
		
		// Model �� View ����
		request.setAttribute("list", map.get("list"));
		request.setAttribute("resultPage", resultPage);
		request.setAttribute("searchVO", searchVO);
		
		System.out.println(" ::. ListProductAction �� :: \n");

		return "forward:/product/listProduct.jsp";
	}
}
