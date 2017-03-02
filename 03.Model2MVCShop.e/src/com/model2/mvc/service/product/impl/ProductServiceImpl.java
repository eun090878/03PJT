package com.model2.mvc.service.product.impl;

import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.dao.ProductDao;

public class ProductServiceImpl implements ProductService {
	
	//Field
	private ProductDao productDAO;

	//Constructor
	public ProductServiceImpl() {
		this.productDAO = new ProductDao();
	}
	
	public void addProduct(Product productVO) throws Exception{
		productDAO.insertProduct(productVO);
	}

	public Product getProduct(int prodNo) throws Exception{
		return productDAO.findProduct(prodNo);
	}

	public Map<String, Object> getProductList(Search searchVO) throws Exception{
		return productDAO.getProductList(searchVO);
	}

	public void updateProduct(Product productVO) throws Exception{
		productDAO.updateProduct(productVO);
	}
	
}
