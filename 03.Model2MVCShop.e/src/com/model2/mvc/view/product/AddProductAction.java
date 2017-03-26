package com.model2.mvc.view.product;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.impl.ProductServiceImpl;

public class AddProductAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println(" ::. AddProductAction ���� :: ");

		if (FileUpload.isMultipartContent(request)) {
			String temDir = "C:\\Users\\BitCamp\\git\\03MiniPJT\\03.Model2MVCShop.e\\WebContent\\images\\uploadFiles\\";
		//	C:\\Users\\BitCamp\\git\\03MiniPJT\\03.Model2MVCShop.e\\WebContent\\images\\uploadFiles\\

			DiskFileUpload fileUpload = new DiskFileUpload();
			fileUpload.setRepositoryPath(temDir);
			fileUpload.setSizeMax(1024 * 1024 * 10);
			fileUpload.setSizeThreshold(1024 * 100);
			
			if (request.getContentLength() < fileUpload.getSizeMax()) {
				Product productVO = new Product();

				StringTokenizer token = null;

				List fileItemList = fileUpload.parseRequest(request);
				int size = fileItemList.size();
				for (int i = 0; i < size; i++) {
					FileItem fileItem = (FileItem) fileItemList.get(i);
					if (fileItem.isFormField()) {
						if (fileItem.getFieldName().equals("manuDate")) {
							token = new StringTokenizer(fileItem.getString("euc-kr"), "-");
							String manuDate = token.nextToken() + token.nextToken() + token.nextToken();
							productVO.setManuDate(manuDate);
						} else if (fileItem.getFieldName().equals("prodName")) {
							productVO.setProdName(fileItem.getString("euc-kr"));
						} else if (fileItem.getFieldName().equals("prodDetail")) {
							productVO.setProdDetail(fileItem.getString("euc-kr"));
						} else if (fileItem.getFieldName().equals("price")) {
							productVO.setPrice(Integer.parseInt(fileItem.getString("euc-kr")));
						}
					} else { // ���� �����̸�
							
						System.out.println("fileItem ���� :: " + fileItem);
						System.out.println("fileItem.getName() ���� :: " + fileItem.getName());
						if (fileItem.getSize() > 0) {
							int idx = fileItem.getName().lastIndexOf("\\");
							if (idx == 1) {
								idx = fileItem.getName().lastIndexOf("/");
							}
							String fileName = fileItem.getName().substring(idx + 1);
							System.out.println("fileName �� �����̳� :: " + fileName);
							productVO.setFileName(fileName);
							try {
								File uploadedFile = new File(temDir, fileName);
								fileItem.write(uploadedFile);
							} catch (IOException e) {
								System.out.println(e);
							}

						} else {
							productVO.setFileName("../../images/empty.GIF");
						}

					} // else
				} // for

				ProductServiceImpl service = new ProductServiceImpl();
				service.addProduct(productVO);

				request.setAttribute("product", productVO);
			} else {
				int overSize = (request.getContentLength() / 1000000);
				System.out.println("<script> alert('������ ũ��� 1MB���� �Դϴ�. ���ε��� ������ �뷮�� " + overSize + "MB�Դϴ�.');");
				System.out.println("history.back();</script>");
			}
		} else {
			System.out.println("���ڵ� Ÿ���� multipart/form-date�� �ƴմϴ�. ");
		}
		return "forward:/product/getProduct.jsp";
	}

} // if( FileUpload.isMultipartContent(request) ) end

/////////////////////////////////////////////////////////////////
/*
 * Product productVO=new Product();
 * 
 * productVO.setProdName(request.getParameter("prodName"));
 * productVO.setProdDetail(request.getParameter("prodDetail"));
 * productVO.setManuDate(request.getParameter("manuDate"));
 * productVO.setPrice(Integer.parseInt(request.getParameter("price")));
 * productVO.setFileName(request.getParameter("fileName"));
 * 
 * System.out.println("AddProductAction : ["+productVO +"]");
 * 
 * ProductService pService = new ProductServiceImpl();
 * pService.addProduct(productVO);
 * 
 * request.setAttribute("product", productVO);
 * 
 * System.out.println(" ::. GetProductAction �� :: \n"); //����� ��ǰ���� ������ return
 * "forward:/product/getProduct.jsp";
 * 
 * } }
 */
