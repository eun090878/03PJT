package com.model2.mvc.service.purchase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;

public class PurchaseDao {
	//Constructor	
	public PurchaseDao() {
	}
	
	//�������� �Է�
	public void insertPurchase(Purchase purchaseVO) throws Exception {
		System.out.println("PurchaseDao :: insertPurchase ���� ::");
		
		Connection con = DBUtil.getConnection();
		
		String sql ="INSERT INTO transaction VALUES ( seq_transaction_tran_no.nextVal, ?, ?, ?, ?, ?, ?, ?, ?, sysdate, ?)";

// INSERT INTO transaction VALUES ( seq_transaction_tran_no.nextVal, 
		//10000, 'user01', 'pay', 'user01', '010-1111', '�����', '��ȭ', 'del', sysdate, to_date('2012/01/14 10:48:43', 'YYYY/MM/DD HH24:MI:SS'))

		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, purchaseVO.getPurchaseProd().getProdNo());
		stmt.setString(2, purchaseVO.getBuyer().getUserId());
		stmt.setString(3, purchaseVO.getPaymentOption());
		stmt.setString(4, purchaseVO.getReceiverName());
		stmt.setString(5, purchaseVO.getReceiverPhone());
		stmt.setString(6, purchaseVO.getDlvyAddr());
		stmt.setString(7, purchaseVO.getDlvyRequest());
		stmt.setString(8, purchaseVO.getTranCode());
		stmt.setString(9, purchaseVO.getDlvyDate());
		
		stmt.executeUpdate();
		con.close();
		
		System.out.println("PurchaseDao :: insertPurchase �� ::");
	}
	
	//�������� ����ȸ
	public Purchase findPurchase(int transNo) throws Exception {
		
		System.out.println("PurchaseDao :: findPurchase() ���� ");
		
		Connection con = DBUtil.getConnection();

		String sql = "";
		
		
		Purchase purchaseVO	 = new Purchase();
		
		return purchaseVO;
	}
	
	//���Ÿ��
	public Map<String, Object> getPurchaseList(Search searchVO, String buyerId)throws Exception {
		
		System.out.println("PurchaseDao :: getPurchaseList() ���� ");
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		Connection con = DBUtil.getConnection();
		
		String sql = "SELECT t.tran_no, t.buyer_id, t.receiver_name, t.receiver_phone, t.tran_status_code"
				+ "	from users u, transaction t"
				+ " where t.buyer_id='"+buyerId+ "'"
				+ " and u.user_id = t.buyer_id ";
		
		int totalCount = this.getTotalCount(sql);
		System.out.println("PurchaseDao :: totalCount  :: " + totalCount);
		
		UserService service = new UserServiceImpl();
		User user = service.getUser(buyerId);
		
		PreparedStatement stmt = con.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();		
	
		List<Purchase> list = new ArrayList<Purchase>();
		
		while(rs.next()){
			Purchase vo = new Purchase();
			vo.setTranNo(rs.getInt("tran_no"));
			vo.setBuyer(user);
			vo.setReceiverName(rs.getString("receiver_name"));
			vo.setReceiverPhone(rs.getString("receiver_phone"));
			vo.setTranCode(rs.getString("tran_status_code"));
			//�������� �߰�

			list.add(vo);

		}
		//==> totalCount ���� ����
		map.put("totalCount", new Integer(totalCount));
		
		//==> currentPage �� �Խù� ���� ���� List ����
		map.put("list", list);

		rs.close();
		stmt.close();
		con.close();
		
		
		System.out.println("PurchaseDao :: getPurchaseList() �� ");
		return map;
	}
	
	//�ǸŸ��
	public Map<String, Object> getSaleList(Search searchVO) throws Exception {
Map<String, Object> map = new HashMap<String, Object>();
		
		return map;
	}
	
	//�������� ����
	public void updatePurchase(Purchase purchaseVO) throws Exception {
		
	}
	
	//���Ż����ڵ� ����
	public void updateTranCode(Purchase purchaseVO) throws Exception {
		
	}
	
	private int getTotalCount(String sql) throws Exception {
		
		sql = "SELECT COUNT(*) "+
		          "FROM ( " +sql+ ") countTable";
		
		Connection con = DBUtil.getConnection();
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
		
		int totalCount = 0;
		if( rs.next() ){
			totalCount = rs.getInt(1);
		}
		
		pStmt.close();
		con.close();
		rs.close();
		
		return totalCount;
	}
	

}
