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
	
	//����
	public void addPurchase(Purchase purchaseVO) throws Exception{
		purchaseDao.insertPurchase(purchaseVO);
	}
	
	//�������� ����ȸ
	public Purchase getPurchase(int tranNo) throws Exception{
		return purchaseDao.findPurchase(tranNo);		
	}

	public Purchase getPurchase2(int ProdNo) throws Exception{

		return purchaseDao.findPurchase2(ProdNo);

	}

	//���Ÿ��
	public Map<String, Object> getPurchaseList(Search searchVO, String buyerId) throws Exception{
		return purchaseDao.getPurchaseList(searchVO, buyerId);
	}
	
	//�ǸŸ��
	public Map<String, Object> getSaleList(Search searchVO) throws Exception{
		return purchaseDao.getSaleList(searchVO);
	}
	
	//�������� ����
	public void updatePurcahse(Purchase purchaseVO) throws Exception{
		purchaseDao.updatePurchase(purchaseVO);
	}
	
	//���Ż����ڵ� ����
	public void updateTranCode(Purchase purchaseVO) throws Exception{
		purchaseDao.updateTranCode(purchaseVO);
	}

}
