package com.model2.mvc.service.purchase.impl;

import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.dao.PurchaseDao;

public class PurchaseServiceImpl implements PurchaseService {
	
	//Field
	private PurchaseDao purchaseDao;
	
	//Constructor
	public PurchaseServiceImpl() {
		this.purchaseDao = new PurchaseDao();
	}
	
	//구매
	public void addPurchase(Purchase purchaseVO) throws Exception{
		purchaseDao.insertPurchase(purchaseVO);
	}
	
	//구매정보 상세조회
	public Purchase getPurchase(int tranNo) throws Exception{
		return purchaseDao.findPurchase(tranNo);		
	}

	public Purchase getPurchase2(int ProdNo) throws Exception{

		return purchaseDao.findPurchase2(ProdNo);

	}

	//구매목록
	public Map<String, Object> getPurchaseList(Search searchVO, String buyerId) throws Exception{
		return purchaseDao.getPurchaseList(searchVO, buyerId);
	}
	
	//판매목록
	public Map<String, Object> getSaleList(Search searchVO) throws Exception{
		return purchaseDao.getSaleList(searchVO);
	}
	
	//구매정보 수정
	public void updatePurcahse(Purchase purchaseVO) throws Exception{
		purchaseDao.updatePurchase(purchaseVO);
	}
	
	//구매상태코드 수정
	public void updateTranCode(Purchase purchaseVO) throws Exception{
		purchaseDao.updateTranCode(purchaseVO);
	}

}
